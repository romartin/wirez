package org.wirez.bpmn.api.property.general;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.annotation.propertyset.Property;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.definition.property.defaultset.Name;

import javax.inject.Inject;

@Portable
public class BackgroundSet implements PropertySet {

    public static final String ID = "background";
    public static final String NAME = "Background";

    @Inject
    private BgColor bgColor;

    @Inject
    private BorderColor borderColor;
    
    @Inject
    private BorderSize borderSize;

    @Override
    public String getPropertySetId() {
        return ID;
    }

    @Override
    public String getPropertySetName() {
        return NAME;
    }

    @Property
    public BgColor getBgColor() {
        return bgColor;
    }

    @Property
    public BorderColor getBorderColor() {
        return borderColor;
    }

    @Property
    public BorderSize getBorderSize() {
        return borderSize;
    }
    
}
