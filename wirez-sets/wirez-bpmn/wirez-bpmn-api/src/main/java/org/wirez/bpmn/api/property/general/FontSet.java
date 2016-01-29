package org.wirez.bpmn.api.property.general;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.wirez.bpmn.api.BPMNPropertySet;
import org.wirez.core.api.annotation.propertyset.Property;
import org.wirez.core.api.definition.property.PropertySet;

import javax.inject.Inject;

@Portable
@Bindable
@org.wirez.core.api.annotation.propertyset.PropertySet
public class FontSet implements BPMNPropertySet {

    public static final String ID = "font";
    public static final String NAME = "Font";

    @Property
    private FontFamily fontFamily;
    
    @Property
    private FontColor fontColor;

    @Property
    private FontSize fontSize;
    
    @Property
    private FontBorderSize fontBorderSize;

    public FontSet() {
    }

    public FontSet(@MapsTo("fontFamily") FontFamily fontFamily,
                   @MapsTo("fontColor") FontColor fontColor,
                   @MapsTo("fontSize") FontSize fontSize,
                   @MapsTo("fontBorderSize") FontBorderSize fontBorderSize) {
        this.fontFamily = fontFamily;
        this.fontColor = fontColor;
        this.fontSize = fontSize;
        this.fontBorderSize = fontBorderSize;
    }

    @Override
    public String getPropertySetId() {
        return ID;
    }

    @Override
    public String getPropertySetName() {
        return NAME;
    }

    public FontFamily getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(FontFamily fontFamily) {
        this.fontFamily = fontFamily;
    }

    public FontColor getFontColor() {
        return fontColor;
    }

    public FontSize getFontSize() {
        return fontSize;
    }

    public FontBorderSize getFontBorderSize() {
        return fontBorderSize;
    }

    public void setFontColor(FontColor fontColor) {
        this.fontColor = fontColor;
    }

    public void setFontSize(FontSize fontSize) {
        this.fontSize = fontSize;
    }

    public void setFontBorderSize(FontBorderSize fontBorderSize) {
        this.fontBorderSize = fontBorderSize;
    }
}
