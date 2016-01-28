package org.wirez.client.widgets.wizard.screen;

import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.user.client.ui.Widget;
import org.uberfire.client.mvp.UberView;
import org.uberfire.mvp.Command;
import org.wirez.client.widgets.event.CreateEmptyDiagramEvent;
import org.wirez.client.widgets.wizard.BaseWizardScreen;
import org.wirez.client.widgets.wizard.CanvasWizard;
import org.wirez.client.widgets.wizard.CanvasWizardScreen;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.ShapeSet;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.Collection;

@Dependent
public class NewDiagramWizardScreen extends BaseWizardScreen implements CanvasWizardScreen {

    public interface View extends UberView<NewDiagramWizardScreen> {

        View setHeading(String title);

        View addItem(String name, String description, SafeUri thumbnailUri, boolean isSelected, Command clickHandler);

        View showEmptyView();

        View setActionButtonText(String caption);

        View setActionButtonEnabled(boolean isEnabled);

        View clear();
        
    }

    ShapeManager wirezClientManager;
    Event<CreateEmptyDiagramEvent> createEmptyDiagramEventEvent;
    View view;
    private String selectedShapeSetId;

    @Inject
    public NewDiagramWizardScreen(final ShapeManager wirezClientManager, 
                                  final Event<CreateEmptyDiagramEvent> createEmptyDiagramEventEvent, 
                                  final View view) {
        this.wirezClientManager = wirezClientManager;
        this.createEmptyDiagramEventEvent = createEmptyDiagramEventEvent;
        this.view = view;
    }

    @PostConstruct
    public void init() {
        view.init(this);
        view.setHeading("Choose a diagram:");
        view.setActionButtonText("Create");
        view.setActionButtonEnabled(false);
    }

    @Override
    public String getNextButtonText() {
        return "Create";
    }

    private final Callback callback = new Callback() {
        @Override
        public void onNextButtonClick(final CanvasWizard canvasWizard) {
            canvasWizard.clear();
        }

        @Override
        public void onBackButtonClick(final CanvasWizard canvasWizard) {
            canvasWizard.clear();
        }
    };
    
    @Override
    public Callback getCallback() {
        return callback;
    }

    public CanvasWizardScreen show() {
        view.clear();
        final Collection<ShapeSet> shapeSets = wirezClientManager.getShapeSets();
        if (shapeSets == null || shapeSets.isEmpty()) {
            view.showEmptyView();
        } else {
            for (final ShapeSet shapeSet : shapeSets) {
                final String uuid = shapeSet.getId();
                final String name = shapeSet.getName();
                final String description = shapeSet.getDescription();
                final SafeUri thumbnailUri = shapeSet.getThumbnailUri();
                final boolean isSelected = selectedShapeSetId == null || selectedShapeSetId.equals(uuid);
                view.addItem(name, description, thumbnailUri, isSelected,
                        new Command() {
                            @Override
                            public void execute() {
                                NewDiagramWizardScreen.this.onShapeSetSelected(uuid);
                            }
                        });
                view.setActionButtonEnabled(selectedShapeSetId != null);

            }
        }

        return this;
    }

    public void clear() {
        selectedShapeSetId = null;
        view.setActionButtonEnabled(false);
        view.clear();
    }

    private void onShapeSetSelected(final String shapeSetId) {
        selectedShapeSetId = shapeSetId;
        show();
    }

    void onActionButtonClick() {
        assert selectedShapeSetId != null;
        createEmptyDiagramEventEvent.fire(new CreateEmptyDiagramEvent(selectedShapeSetId));
        clear();
        show();
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }
    
}
