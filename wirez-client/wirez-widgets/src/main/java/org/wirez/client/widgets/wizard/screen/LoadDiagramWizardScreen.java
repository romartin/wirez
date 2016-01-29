package org.wirez.client.widgets.wizard.screen;

import com.google.gwt.user.client.ui.Widget;
import org.uberfire.client.mvp.UberView;
import org.uberfire.mvp.PlaceRequest;
import org.uberfire.mvp.impl.DefaultPlaceRequest;
import org.wirez.client.widgets.event.LoadDiagramEvent;
import org.wirez.client.widgets.wizard.BaseWizardScreen;
import org.wirez.client.widgets.wizard.CanvasWizard;
import org.wirez.client.widgets.wizard.CanvasWizardScreen;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@Dependent
public class LoadDiagramWizardScreen extends BaseWizardScreen implements CanvasWizardScreen {

    public interface View extends UberView<LoadDiagramWizardScreen> {
        
    }
    
    Event<LoadDiagramEvent> loadDiagramEventEvent;
    View view;

    @Inject
    public LoadDiagramWizardScreen(final Event<LoadDiagramEvent> loadDiagramEventEvent, final View view) {
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
    public Callback getCallback() {
        return new Callback() {
            @Override
            public void onNextButtonClick() {
                final String path = "org/wirez/bpmn/backend/examples/wirez-bpmn-test.bpmn2";
                loadDiagramEventEvent.fire(new LoadDiagramEvent(path));
            }

            @Override
            public void onBackButtonClick() {
                wizard.clear();
            }
        };
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }
    
}
