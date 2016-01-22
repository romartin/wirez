package org.wirez.bpmn.api.property.general;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.annotation.propertyset.Property;
import org.wirez.core.api.definition.property.PropertySet;

import javax.inject.Inject;

@Portable
public class BackgroundSet implements PropertySet {

    public static final String ID = "background";
    public static final String NAME = "Background";

    @Inject
    @Property
    private BgColor bgColor;

    @Inject
    @Property
    private BorderColor borderColor;
    
    @Inject
    @Property
    private BorderSize borderSize;

    @Override
    public String getPropertySetId() {
        return ID;
    }

    @Override
    public String getPropertySetName() {
        return NAME;
    }

    public BgColor getBgColor() {
        return bgColor;
    }

    public BorderColor getBorderColor() {
        return borderColor;
    }

    public BorderSize getBorderSize() {
        return borderSize;
    }

    public void setBgColor(BgColor bgColor) {
        this.bgColor = bgColor;
    }

    public void setBorderColor(BorderColor borderColor) {
        this.borderColor = borderColor;
    }

    public void setBorderSize(BorderSize borderSize) {
        this.borderSize = borderSize;
    }
}
