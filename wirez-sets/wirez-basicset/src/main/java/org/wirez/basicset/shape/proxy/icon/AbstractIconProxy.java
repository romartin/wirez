package org.wirez.basicset.shape.proxy.icon;

import org.wirez.basicset.definition.icon.Icon;
import org.wirez.shapes.proxy.icon.IconProxy;

public abstract class AbstractIconProxy<I extends Icon> implements IconProxy<I> {
    
    @Override
    public double getWidth( final I element ) {
        return element.getWidth().getValue();
    }

    @Override
    public double getHeight( final I element ) {
        return element.getHeight().getValue();
    }

    @Override
    public String getBackgroundColor( final I element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public String getBorderColor( final I element ) {
        return element.getBackgroundSet().getBorderColor().getValue();
    }

    @Override
    public double getBorderSize( final I element ) {
        return element.getBackgroundSet().getBorderSize().getValue();
    }


    @Override
    public String getNamePropertyValue( final I element ) {
        return null;
    }

}
