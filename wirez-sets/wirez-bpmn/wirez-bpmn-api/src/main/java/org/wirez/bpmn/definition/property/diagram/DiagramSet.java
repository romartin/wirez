package org.wirez.bpmn.definition.property.diagram;

import javax.validation.Valid;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.kie.workbench.common.forms.metaModel.FieldDef;
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
    @FieldDef(label = "ID", property = "value")
    @Valid
    private Id id;

    @Property
    @FieldDef(label = "Package", property = "value")
    @Valid
    private Package packageProperty;

    @Property
    @FieldDef(label = "Version", property = "value")
    @Valid
    private Version version;

    @Property
    private Executable executable;

    public DiagramSet() {
        this( new Id(), new Package(),
                new Version(), new Executable() );
    }

    public DiagramSet(@MapsTo("id") Id id,
                    @MapsTo("packageProperty") Package packageProperty,
                    @MapsTo("version") Version version,
                    @MapsTo("executable") Executable executable) {
        this.id = id;
        this.packageProperty = packageProperty;
        this.version = version;
        this.executable = executable;
    }

    public DiagramSet(String id,
                      String packageProperty,
                      String version,
                      Boolean executable) {
        this.id = new Id( id );
        this.packageProperty = new Package( packageProperty );
        this.version = new Version( version );
        this.executable = new Executable( executable );
    }

    public String getPropertySetName() {
        return propertySetName;
    }

    public Id getId() {
        return id;
    }

    public Package getPackageProperty() {
        return packageProperty;
    }

    public Version getVersion() {
        return version;
    }

    public Executable getExecutable() {
        return executable;
    }

    public void setId( Id id ) {
        this.id = id;
    }

    public void setPackageProperty( Package packageProperty ) {
        this.packageProperty = packageProperty;
    }

    public void setVersion( Version version ) {
        this.version = version;
    }

    public void setExecutable( Executable executable ) {
        this.executable = executable;
    }
}
