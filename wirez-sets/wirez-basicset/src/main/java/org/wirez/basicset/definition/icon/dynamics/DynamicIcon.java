package org.wirez.basicset.definition.icon.dynamics;

import org.wirez.basicset.definition.property.Height;
import org.wirez.basicset.definition.property.Width;
import org.wirez.basicset.definition.property.background.BackgroundAndBorderSet;

public interface DynamicIcon {

    String COLOR = "#000000";
    Double WIDTH = 50d;
    Double HEIGHT = 50d;
    Double BORDER_SIZE = 5d;
    
    BackgroundAndBorderSet getBackgroundSet();

    Width getWidth();

    Height getHeight();
    
}
