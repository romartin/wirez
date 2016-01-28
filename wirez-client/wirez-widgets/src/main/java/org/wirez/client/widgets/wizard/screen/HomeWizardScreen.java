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
public class HomeWizardScreen extends BaseWizardScreen implements CanvasWizardScreen {

    public interface View extends UberView<HomeWizardScreen> {
        View clear();
    }
    
    LoadDiagramWizardScreen loadDiagramWizardScreen;
    NewDiagramWizardScreen newDiagramWizardScreen;
    View view;
    
    private boolean isLoad = false;

    @Inject
    public HomeWizardScreen(final LoadDiagramWizardScreen loadDiagramWizardScreen, 
                            final NewDiagramWizardScreen newDiagramWizardScreen, 
                            final View view) {
        this.loadDiagramWizardScreen = loadDiagramWizardScreen;
        this.newDiagramWizardScreen = newDiagramWizardScreen;
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
                
                if (isLoad) {
                    canvasWizard.navigate(loadDiagramWizardScreen);
                } else {
                    canvasWizard.navigate(newDiagramWizardScreen);
                }
            }

            @Override
            public void onBackButtonClick(final CanvasWizard canvasWizard) {
                canvasWizard.clear();
            }
        };
    }

    @Override
    public CanvasWizardScreen show() {
        view.clear();
        isLoad = false;
        return this;
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }

    void onLoadButtonClick() {
        this.isLoad = true;
    }

    void onNewButtonClick() {
        this.isLoad = false;
    }
    
}
