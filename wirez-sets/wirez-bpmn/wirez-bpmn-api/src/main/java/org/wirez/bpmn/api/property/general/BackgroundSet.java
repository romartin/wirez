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
public class BackgroundSet implements BPMNPropertySet {

    public static final String ID = "background";
    public static final String NAME = "Background";

    @Property
    private BgColor bgColor;

    @Property
    private BorderColor borderColor;
    
    @Property
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
