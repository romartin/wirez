package org.wirez.bpmn.api.property.diagram;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.wirez.bpmn.api.BPMNPropertySet;
import org.wirez.core.api.definition.annotation.Name;
import org.wirez.core.api.definition.annotation.propertyset.Property;
import org.wirez.core.api.definition.annotation.propertyset.PropertySet;

@Portable
@Bindable
@PropertySet
public class DiagramSet implements BPMNPropertySet {

    @Name
    public static final transient String propertySetName = "BPMN Diagram";

    @Property
    private Package packageProperty;

    @Property
    private Executable executable;

    public DiagramSet() {
        
    }
    
    public DiagramSet(@MapsTo("packageProperty") Package packageProperty,
                      @MapsTo("executable") Executable executable) {
        this.packageProperty = packageProperty;
        this.executable = executable;
    }

    public String getPropertySetName() {
        return propertySetName;
    }

    public Package getPackageProperty() {
        return packageProperty;
    }

    public Executable getExecutable() {
        return executable;
    }

}
