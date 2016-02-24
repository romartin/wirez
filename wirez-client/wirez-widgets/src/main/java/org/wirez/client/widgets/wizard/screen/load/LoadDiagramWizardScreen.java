package org.wirez.client.widgets.wizard.screen.load;

import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.user.client.ui.Widget;
import org.uberfire.client.mvp.UberView;
import org.wirez.client.widgets.event.LoadDiagramEvent;
import org.wirez.client.widgets.wizard.BaseWizardScreen;
import org.wirez.client.widgets.wizard.CanvasWizardScreen;
import org.wirez.core.api.service.diagram.DiagramRepresentation;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.ShapeSet;
import org.wirez.core.client.service.ClientDiagramServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.service.ServiceCallback;
import org.wirez.core.client.util.WirezClientLogger;
import org.wirez.core.client.util.ShapeUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

@Dependent
public class LoadDiagramWizardScreen extends BaseWizardScreen implements CanvasWizardScreen {

    private static Logger LOGGER = Logger.getLogger("org.wirez.client.widgets.wizard.screen.load.LoadDiagramWizardScreen");

    public interface View extends UberView<LoadDiagramWizardScreen> {
        
        View showEmpty();
        
        View add(String title, String path, SafeUri thumbUri);
        
        View clear();
        
    }

    ShapeManager shapeManager;
    ClientDiagramServices clientDiagramServices;
    Event<LoadDiagramEvent> loadDiagramEventEvent;
    View view;
    
    private String selectedPath = null;

    @Inject
    public LoadDiagramWizardScreen(final ShapeManager shapeManager,
                                   final ClientDiagramServices clientDiagramServices,
                                   final Event<LoadDiagramEvent> loadDiagramEventEvent, 
                                   final View view) {
        this.shapeManager = shapeManager;
        this.clientDiagramServices = clientDiagramServices;
        this.loadDiagramEventEvent = loadDiagramEventEvent;
        this.view = view;
    }

    @PostConstruct
    public void init() {
        view.init(this);
    }
    
    @Override
    public String getNextButtonText() {
        return "Load";
    }

    @Override
    protected void doShow() {
        super.doShow();

        clear();

        wizard.setNextButtonEnabled(false);
        
        clientDiagramServices.search("", new ServiceCallback<Collection<DiagramRepresentation>>() {
            @Override
            public void onSuccess(final Collection<DiagramRepresentation> items) {
                
                if ( null == items || items.isEmpty() ) {
                    
                    view.showEmpty();
                    
                } else {
                    
                    for (final DiagramRepresentation diagram : items) {
                        
                        addEntry( diagram );
                    }
                    
                }
                
            }

            @Override
            public void onError(final ClientRuntimeError error) {
                log( Level.SEVERE, WirezClientLogger.getErrorMessage(error) );
            }
        });
    }
    
    private void addEntry(final DiagramRepresentation diagram) {
        assert diagram != null;
        final Collection<ShapeSet> shapeSets = shapeManager.getShapeSets();
        final SafeUri thumbUri = ShapeUtils.getShapeSet(shapeSets, diagram.getShapeSetId()).getThumbnailUri();
        view.add( diagram.getName(), diagram.getPath(), thumbUri );
    }

    @Override
    public Callback getCallback() {
        return new Callback() {
            
            @Override
            public void onNextButtonClick() {

                // Clear to home screen.
                wizard.clear();

                // Fire the load diagram event for others.
                loadDiagramEventEvent.fire(new LoadDiagramEvent( selectedPath ));
                
            }

            @Override
            public void onBackButtonClick() {
                wizard.clear();
            }
            
        };
    }
    
    public void clear() {
        selectedPath = null;
        view.clear();
    }
    
    void onItemClick(final String path) {
        this.selectedPath = path;
        
        if ( selectedPath != null ) {
            
            wizard.setNextButtonEnabled(true);
            
        } else {

            wizard.setNextButtonEnabled(false);
            
        }
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }

    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }
    
}
