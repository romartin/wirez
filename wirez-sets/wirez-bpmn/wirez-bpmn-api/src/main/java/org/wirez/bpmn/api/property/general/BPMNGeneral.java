package org.wirez.bpmn.api.property.general;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.annotation.propertyset.Property;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.definition.property.defaultset.Name;

import javax.inject.Inject;

@Portable
public class BPMNGeneral implements PropertySet {

    public static final String ID = "bpmnGeneral";
    public static final String NAME = "BPMN General";

    @Inject
    private Name name;

    @Inject
    private Documentation documentation;

    @Override
    public String getPropertySetId() {
        return ID;
    }

    @Override
    public String getPropertySetName() {
        return NAME;
    }

    @Property
    public Name getName() {
        return name;
    }

    @Property
    public Documentation getDocumentation() {
        return documentation;
    }
}
