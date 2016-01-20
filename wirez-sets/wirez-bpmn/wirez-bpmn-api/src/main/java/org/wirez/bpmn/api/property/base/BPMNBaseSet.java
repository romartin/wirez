package org.wirez.bpmn.api.property.base;

import org.wirez.core.api.annotation.propertyset.Property;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.definition.property.defaultset.Name;

import javax.inject.Inject;

public class BPMNBaseSet implements PropertySet {

    public static final String ID = "bpmnBase";
    public static final String NAME = "BPMN Base";

    @Inject
    private Name name;

    @Inject
    private Documentation documentation;

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Property
    public Name getNameProperty() {
        return name;
    }

    @Property
    public Documentation getDocumentation() {
        return documentation;
    }
}
