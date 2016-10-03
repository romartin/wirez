package org.kie.workbench.common.stunner.bpmn.definition.property.font;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.kie.workbench.common.stunner.bpmn.definition.BPMNPropertySet;
import org.kie.workbench.common.stunner.core.definition.annotation.Name;
import org.kie.workbench.common.stunner.core.definition.annotation.Property;
import org.kie.workbench.common.stunner.core.definition.annotation.PropertySet;

@Portable
@Bindable
@PropertySet
public class FontSet implements BPMNPropertySet {

    @Name
    public static final transient String propertySetName = "Font";

    @Property
    private FontFamily fontFamily;

    @Property
    private FontColor fontColor;

    @Property
    private FontSize fontSize;

    @Property
    private FontBorderSize fontBorderSize;

    public FontSet() {
        this( new FontFamily(), 
                new FontColor(),
                new FontSize(), 
                new FontBorderSize() );
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

    public FontSet(String fontFamily,
                   String fontColor,
                   Double fontSize,
                   Double fontBorderSize) {
        this.fontFamily = new FontFamily( fontFamily );
        this.fontColor = new FontColor( fontColor );
        this.fontSize = new FontSize( fontSize );
        this.fontBorderSize = new FontBorderSize( fontBorderSize );
    }

    public String getPropertySetName() {
        return propertySetName;
    }

    public FontFamily getFontFamily() {
        return fontFamily;
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

    public void setFontFamily( FontFamily fontFamily ) {
        this.fontFamily = fontFamily;
    }

    public void setFontColor( FontColor fontColor ) {
        this.fontColor = fontColor;
    }

    public void setFontSize( FontSize fontSize ) {
        this.fontSize = fontSize;
    }

    public void setFontBorderSize( FontBorderSize fontBorderSize ) {
        this.fontBorderSize = fontBorderSize;
    }
}
