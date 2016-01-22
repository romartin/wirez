package org.wirez.bpmn.api.property.diagram;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.wirez.core.api.annotation.propertyset.Property;
import org.wirez.core.api.definition.property.PropertySet;

import javax.inject.Inject;

@Portable
@Bindable
@org.wirez.core.api.annotation.propertyset.PropertySet
public class DiagramSet implements PropertySet {
    
    public static final String ID = "diagramSet";
    public static final String NAME = "BPMN Diagram";

    @Inject
    @Property
    private Package thePackage;

    @Inject
    @Property
    private Executable executable;
    
    @Override
    public String getPropertySetId() {
        return ID;
    }

    @Override
    public String getPropertySetName() {
        return NAME;
    }

    public Package getPackage() {
        return thePackage;
    }

    public Executable getExecutable() {
        return executable;
    }

    public void setThePackage(Package thePackage) {
        this.thePackage = thePackage;
    }

    public void setExecutable(Executable executable) {
        this.executable = executable;
    }
}
