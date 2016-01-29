package org.wirez.bpmn.api.property.general;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.wirez.bpmn.api.BPMNPropertySet;
import org.wirez.core.api.annotation.propertyset.Property;

import javax.inject.Inject;

@Portable
@Bindable
@org.wirez.core.api.annotation.propertyset.PropertySet
public class BPMNGeneral implements BPMNPropertySet {

    public static final String ID = "bpmnGeneral";
    public static final String NAME = "BPMN General";

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

    @Override
    public String getPropertySetId() {
        return ID;
    }

    @Override
    public String getPropertySetName() {
        return NAME;
    }

    public Name getName() {
        return name;
    }

    public Documentation getDocumentation() {
        return documentation;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setDocumentation(Documentation documentation) {
        this.documentation = documentation;
    }
}
