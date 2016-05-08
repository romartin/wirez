package org.wirez.basicset.api.property.background;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.wirez.core.api.definition.annotation.propertyset.Property;
import org.wirez.core.api.definition.annotation.propertyset.PropertySet;

@Portable
@Bindable
@PropertySet
public class BackgroundAndBorderSet {

    @org.wirez.core.api.definition.annotation.Name
    public static final transient String propertySetName = "Background And Borders";

    @Property
    private BgColor bgColor;

    @Property
    private BorderColor borderColor;

    @Property
    private BorderSize borderSize;

    public BackgroundAndBorderSet() {

    }

    public BackgroundAndBorderSet(@MapsTo("bgColor") BgColor bgColor,
                                  @MapsTo("borderColor") BorderColor borderColor,
                                  @MapsTo("borderSize") BorderSize borderSize) {
        this.bgColor = bgColor;
        this.borderColor = borderColor;
        this.borderSize = borderSize;
    }

    public String getPropertySetName() {
        return propertySetName;
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

    public void setBgColor( BgColor bgColor ) {
        this.bgColor = bgColor;
    }

    public void setBorderColor( BorderColor borderColor ) {
        this.borderColor = borderColor;
    }

    public void setBorderSize( BorderSize borderSize ) {
        this.borderSize = borderSize;
    }
}
