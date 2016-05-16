package org.wirez.basicset.api.icon;

import org.wirez.basicset.api.property.Height;
import org.wirez.basicset.api.property.Width;
import org.wirez.basicset.api.property.background.BackgroundAndBorderSet;

public interface Icon {

    String COLOR = "#000000";
    Double WIDTH = 50d;
    Double HEIGHT = 50d;
    Double BORDER_SIZE = 5d;
    
    BackgroundAndBorderSet getBackgroundSet();

    Width getWidth();

    Height getHeight();
    
}
