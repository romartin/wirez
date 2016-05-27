package org.wirez.bpmn.definition.property.diagram;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.wirez.bpmn.definition.BPMNPropertySet;
import org.wirez.core.definition.annotation.Name;
import org.wirez.core.definition.annotation.propertyset.Property;
import org.wirez.core.definition.annotation.propertyset.PropertySet;

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
        this( new Package(),
                new Executable() );
    }

    public DiagramSet(@MapsTo("packageProperty") Package packageProperty,
                      @MapsTo("executable") Executable executable) {
        this.packageProperty = packageProperty;
        this.executable = executable;
    }

    public DiagramSet(String packageProperty,
                      Boolean executable) {
        this.packageProperty = new Package( packageProperty );
        this.executable = new Executable( executable );
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

    public void setPackageProperty( Package packageProperty ) {
        this.packageProperty = packageProperty;
    }

    public void setExecutable( Executable executable ) {
        this.executable = executable;
    }
}
