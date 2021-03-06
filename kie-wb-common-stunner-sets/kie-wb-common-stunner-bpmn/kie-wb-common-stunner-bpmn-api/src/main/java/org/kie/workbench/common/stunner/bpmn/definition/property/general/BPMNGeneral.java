package org.kie.workbench.common.stunner.bpmn.definition.property.general;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.kie.workbench.common.forms.metaModel.FieldDef;
import org.kie.workbench.common.forms.metaModel.TextArea;
import org.kie.workbench.common.stunner.bpmn.definition.BPMNPropertySet;
import org.kie.workbench.common.stunner.core.definition.annotation.Property;
import org.kie.workbench.common.stunner.core.definition.annotation.PropertySet;

import javax.validation.Valid;

@Portable
@Bindable
@PropertySet
public class BPMNGeneral implements BPMNPropertySet {

    @org.kie.workbench.common.stunner.core.definition.annotation.Name
    public static final transient String propertySetName = "BPMN General";

    @Property
    @FieldDef(label = "Name", property = "value", position = 0)
    @Valid
    private Name name;

    @Property
    @FieldDef( label = "Documentation", property = "value", position = 1)
    @TextArea( rows = 3)
    @Valid
    private Documentation documentation;

    public BPMNGeneral() {
        this( new Name(), new Documentation() );
    }

    public BPMNGeneral(@MapsTo("name") Name name,
                       @MapsTo("documentation") Documentation documentation) {
        this.name = name;
        this.documentation = documentation;
    }

    public BPMNGeneral(String name,
                       String documentation) {
        this.name = new Name( name );
        this.documentation = new Documentation( documentation );
    }

    public BPMNGeneral(String name) {
        this.name = new Name( name );
        this.documentation = new Documentation();
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
