package org.wirez.bpmn.api.property.general;

import javax.validation.Valid;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.livespark.formmodeler.metaModel.FieldDef;
import org.livespark.formmodeler.metaModel.Slider;
import org.wirez.bpmn.api.BPMNPropertySet;
import org.wirez.core.api.definition.annotation.propertyset.Property;
import org.wirez.core.api.definition.annotation.propertyset.PropertySet;
import org.wirez.forms.meta.definition.ColorPicker;

@Portable
@Bindable
@PropertySet
public class BackgroundSet implements BPMNPropertySet {

    @org.wirez.core.api.definition.annotation.Name
    public static final transient String propertySetName = "Background Set";

    @Property
    @FieldDef(label = "Background Color", property = "value")
    @ColorPicker
    @Valid
    private BgColor bgColor;

    @Property
    @FieldDef(label = "Border Color", property = "value")
    @ColorPicker
    @Valid
    private BorderColor borderColor;

    @Property
    @FieldDef(label = "Border Size", property = "value")
    @Slider( min = 0.0, max = 5.0, step = 0.5)
    @Valid
    private BorderSize borderSize;

    public BackgroundSet() {

    }

    public BackgroundSet(@MapsTo("bgColor") BgColor bgColor,
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
