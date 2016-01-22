package org.wirez.bpmn.api.property.general;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.wirez.core.api.annotation.propertyset.Property;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.definition.property.defaultset.Name;

import javax.inject.Inject;

@Portable
@Bindable
public class BPMNGeneral implements PropertySet {

    public static final String ID = "bpmnGeneral";
    public static final String NAME = "BPMN General";

    @Inject
    @Property
    private Name name;

    @Inject
    @Property
    private Documentation documentation;

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
