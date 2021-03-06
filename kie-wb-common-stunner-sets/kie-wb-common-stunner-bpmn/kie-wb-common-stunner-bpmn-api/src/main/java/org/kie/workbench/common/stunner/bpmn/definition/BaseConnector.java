package org.kie.workbench.common.stunner.bpmn.definition;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.kie.workbench.common.forms.metaModel.FieldDef;
import org.kie.workbench.common.stunner.bpmn.definition.property.background.BackgroundSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.font.FontSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.general.BPMNGeneral;
import org.kie.workbench.common.stunner.bpmn.shape.def.SequenceFlowConnectorDef;
import org.kie.workbench.common.stunner.core.definition.annotation.Description;
import org.kie.workbench.common.stunner.core.definition.annotation.PropertySet;
import org.kie.workbench.common.stunner.core.definition.annotation.Shape;
import org.kie.workbench.common.stunner.core.definition.annotation.definition.Category;
import org.kie.workbench.common.stunner.core.definition.annotation.definition.Labels;
import org.kie.workbench.common.stunner.core.definition.builder.Builder;
import org.kie.workbench.common.stunner.shapes.factory.BasicShapesFactory;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Shape( factory = BasicShapesFactory.class, def = SequenceFlowConnectorDef.class )
public abstract class BaseConnector implements BPMNDefinition {
    @Category
    public static final transient String category = Categories.CONNECTING_OBJECTS;

    @Description
    public static final transient String description = "A Connecting Object";

    @PropertySet
    @FieldDef( label = "General Settings", position = 0)
    @Valid
    protected BPMNGeneral general;

    @PropertySet
    @FieldDef( label = "Background Settings", position = 2)
    @Valid
    protected BackgroundSet backgroundSet;

    @PropertySet
    @FieldDef( label = "Font Settings", position = 3)
    protected FontSet fontSet;

    @NonPortable
    static abstract class BaseConnectorBuilder<T extends BaseConnector> implements Builder<BaseConnector> {

        public static final transient String COLOR = "#000000";
        public static final transient String BORDER_COLOR = "#000000";
        public static final Double BORDER_SIZE = 3d;

    }

    @Labels
    protected final Set<String> labels = new HashSet<String>() {{
        add( "all" );
        add( "ConnectingObjectsMorph" );
    }};

    protected BaseConnector() {
    }

    public BaseConnector(@MapsTo("general") BPMNGeneral general,
                         @MapsTo("backgroundSet") BackgroundSet backgroundSet,
                         @MapsTo("fontSet") FontSet fontSet
                    ) {
        this.general = general;
        this.backgroundSet = backgroundSet;
        this.fontSet = fontSet;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Set<String> getLabels() {
        return labels;
    }

    public BPMNGeneral getGeneral() {
        return general;
    }

    public void setGeneral(BPMNGeneral general) {
        this.general = general;
    }

    public BackgroundSet getBackgroundSet() {
        return backgroundSet;
    }

    public void setBackgroundSet(BackgroundSet backgroundSet) {
        this.backgroundSet = backgroundSet;
    }

    public FontSet getFontSet() {
        return fontSet;
    }

    public void setFontSet(FontSet fontSet) {
        this.fontSet = fontSet;
    }
}
