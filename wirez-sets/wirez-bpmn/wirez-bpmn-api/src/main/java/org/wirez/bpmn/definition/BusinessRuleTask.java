package org.wirez.bpmn.definition;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.wirez.bpmn.definition.property.dimensions.RectangleDimensionsSet;
import org.wirez.bpmn.definition.property.background.BackgroundSet;
import org.wirez.bpmn.definition.property.dataio.DataIOSet;
import org.wirez.bpmn.definition.property.font.FontSet;
import org.wirez.bpmn.definition.property.general.BPMNGeneral;
import org.wirez.bpmn.definition.property.simulation.*;
import org.wirez.bpmn.definition.property.task.Script;
import org.wirez.bpmn.definition.property.task.TaskType;
import org.wirez.bpmn.definition.property.task.TaskTypes;
import org.wirez.core.definition.annotation.definition.Definition;
import org.wirez.core.definition.annotation.definition.Property;
import org.wirez.core.definition.annotation.definition.Title;
import org.wirez.core.definition.annotation.morph.Morph;
import org.wirez.core.factory.graph.NodeFactory;
import org.wirez.core.rule.annotation.CanDock;

@Portable
@Bindable
@Definition( graphFactory = NodeFactory.class, builder = BusinessRuleTask.BusinessRuleTaskBuilder.class )
@CanDock( roles = { "IntermediateEventOnActivityBoundary" } )
@Morph( base = BaseTask.class )
public class BusinessRuleTask extends BaseTask {

    @Title
    public static final transient String title = "Business Rule Task";

    @Property
    protected Script script;

    @NonPortable
    public static class BusinessRuleTaskBuilder extends BaseTaskBuilder<BusinessRuleTask> {

        @Override
        public BusinessRuleTask build() {
            return new BusinessRuleTask(new BPMNGeneral("Task"),
                    new DataIOSet(),
                    new BackgroundSet(COLOR, BORDER_COLOR, BORDER_SIZE),
                    new FontSet(),
                    new RectangleDimensionsSet(WIDTH, HEIGHT),
                    new SimulationSet(),
                    new TaskType( TaskTypes.BUSINESS_RULE ),
                    new Script());
        }

    }

    public BusinessRuleTask() {
        super( TaskTypes.BUSINESS_RULE );
    }

    public BusinessRuleTask(@MapsTo("general") BPMNGeneral general,
                            @MapsTo("dataIOSet") DataIOSet dataIOSet,
                            @MapsTo("backgroundSet") BackgroundSet backgroundSet,
                            @MapsTo("fontSet") FontSet fontSet,
                            @MapsTo("dimensionsSet") RectangleDimensionsSet dimensionsSet,
                            @MapsTo("simulationSet") SimulationSet simulationSet,
                            @MapsTo("taskType") TaskType taskType,
                            @MapsTo("script") Script script) {

        super(general, dataIOSet, backgroundSet, fontSet, dimensionsSet, simulationSet, taskType);
        this.script = script;

    }

    public String getTitle() {
        return title;
    }

    public Script getScript() {
        return script;
    }

    public void setScript(Script script) {
        this.script = script;
    }
}
