package org.wirez.bpmn.api.property.diagram;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.annotation.propertyset.Property;
import org.wirez.core.api.definition.property.PropertySet;

import javax.inject.Inject;

@Portable
public class DiagramSet implements PropertySet {
    
    public static final String ID = "diagramSet";
    public static final String NAME = "BPMN Diagram";

    @Inject
    private Package thePackage;

    @Inject
    private Executable executable;
    
    @Override
    public String getPropertySetId() {
        return ID;
    }

    @Override
    public String getPropertySetName() {
        return NAME;
    }

    @Property
    public Package getPackage() {
        return thePackage;
    }

    @Property
    public Executable getExecutable() {
        return executable;
    }
    
}
