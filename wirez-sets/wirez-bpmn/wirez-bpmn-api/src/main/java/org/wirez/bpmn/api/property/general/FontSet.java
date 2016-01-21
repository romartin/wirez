package org.wirez.bpmn.api.property.general;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.annotation.propertyset.Property;
import org.wirez.core.api.definition.property.PropertySet;

import javax.inject.Inject;

@Portable
public class FontSet implements PropertySet {

    public static final String ID = "font";
    public static final String NAME = "Font";

    @Inject
    private FontColor fontColor;

    @Inject
    private FontSize fontSize;
    
    @Inject
    private FontBorderSize fontBorderSize;

    @Override
    public String getPropertySetId() {
        return ID;
    }

    @Override
    public String getPropertySetName() {
        return NAME;
    }

    @Property
    public FontColor getFontColor() {
        return fontColor;
    }

    @Property
    public FontSize getFontSize() {
        return fontSize;
    }

    @Property
    public FontBorderSize getFontBorderSize() {
        return fontBorderSize;
    }
}
