package org.wirez.client.widgets.wizard;

public abstract class BaseWizardScreen implements CanvasWizardScreen {
    
    @Override
    public String getNextButtonText() {
        return "Next";
    }

    @Override
    public String getBackButtonText() {
        return "Back";
    }

}
