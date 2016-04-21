package org.wirez.bpmn.api.property.general;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.wirez.bpmn.api.BPMNPropertySet;
import org.wirez.core.api.definition.annotation.propertyset.Property;
import org.wirez.core.api.definition.annotation.propertyset.PropertySet;

@Portable
@Bindable
@PropertySet
public class BPMNGeneral implements BPMNPropertySet {

    @org.wirez.core.api.definition.annotation.Name
    public static final transient String propertySetName = "BPMN General";

    @Property
    private Name name;

    @Property
    private Documentation documentation;

    public BPMNGeneral() {

    }

    public BPMNGeneral(@MapsTo("name") Name name,
                       @MapsTo("documentation") Documentation documentation) {
        this.name = name;
        this.documentation = documentation;
    }

    public String getPropertySetName() {
        return propertySetName;
    }

    public Name getName() {
        return name;
    }

    public Documentation getDocumentation() {
        return documentation;
    }

    public void setName( Name name ) {
        this.name = name;
    }

    public void setDocumentation( Documentation documentation ) {
        this.documentation = documentation;
    }
}
