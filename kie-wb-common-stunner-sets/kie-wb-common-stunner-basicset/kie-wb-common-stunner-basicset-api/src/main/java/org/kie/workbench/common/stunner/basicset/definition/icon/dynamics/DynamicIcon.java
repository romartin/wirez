package org.kie.workbench.common.stunner.basicset.definition.icon.dynamics;

import org.kie.workbench.common.stunner.basicset.definition.property.Height;
import org.kie.workbench.common.stunner.basicset.definition.property.Width;
import org.kie.workbench.common.stunner.basicset.definition.property.background.BackgroundAndBorderSet;

public interface DynamicIcon {

    String COLOR = "#000000";
    Double WIDTH = 50d;
    Double HEIGHT = 50d;
    Double BORDER_SIZE = 5d;
    
    BackgroundAndBorderSet getBackgroundSet();

    Width getWidth();

    Height getHeight();
    
}
