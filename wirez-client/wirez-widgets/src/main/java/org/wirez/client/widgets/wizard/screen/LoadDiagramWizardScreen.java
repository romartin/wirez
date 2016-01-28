package org.wirez.client.widgets.wizard.screen;

import com.google.gwt.user.client.ui.Widget;
import org.uberfire.client.mvp.UberView;
import org.wirez.client.widgets.wizard.BaseWizardScreen;
import org.wirez.client.widgets.wizard.CanvasWizard;
import org.wirez.client.widgets.wizard.CanvasWizardScreen;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class LoadDiagramWizardScreen extends BaseWizardScreen implements CanvasWizardScreen {

    public interface View extends UberView<LoadDiagramWizardScreen> {
        
    }
    
    View view;

    @Inject
    public LoadDiagramWizardScreen(final View view) {
        this.view = view;
    }

    @PostConstruct
    public void init() {
        view.init(this);
    }
    
    @Override
    public Callback getCallback() {
        return new Callback() {
            @Override
            public void onNextButtonClick(final CanvasWizard canvasWizard) {
                
            }

            @Override
            public void onBackButtonClick(final CanvasWizard canvasWizard) {
                canvasWizard.clear();
            }
        };
    }

    @Override
    public CanvasWizardScreen show() {
        return this;
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }
    
}
