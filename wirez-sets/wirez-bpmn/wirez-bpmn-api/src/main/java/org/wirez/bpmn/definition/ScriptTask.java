package org.wirez.bpmn.definition;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.kie.workbench.common.forms.metaModel.FieldDef;
import org.wirez.bpmn.definition.property.dimensions.RectangleDimensionsSet;
import org.wirez.bpmn.definition.property.background.BackgroundSet;
import org.wirez.bpmn.definition.property.dataio.DataIOSet;
import org.wirez.bpmn.definition.property.font.FontSet;
import org.wirez.bpmn.definition.property.general.BPMNGeneral;
import org.wirez.bpmn.definition.property.simulation.*;
import org.wirez.bpmn.definition.property.task.EmptyTaskExecutionSet;
import org.wirez.bpmn.definition.property.task.Script;
import org.wirez.bpmn.definition.property.task.TaskType;
import org.wirez.bpmn.definition.property.task.TaskTypes;
import org.wirez.core.definition.annotation.definition.Definition;
import org.wirez.core.definition.annotation.definition.Property;
import org.wirez.core.definition.annotation.definition.PropertySet;
import org.wirez.core.definition.annotation.definition.Title;
import org.wirez.core.definition.annotation.morph.Morph;
import org.wirez.core.factory.graph.NodeFactory;
import org.wirez.core.rule.annotation.CanDock;

import javax.validation.Valid;

@Portable
@Bindable
@Definition( graphFactory = NodeFactory.class, builder = ScriptTask.ScriptTaskBuilder.class )
@CanDock( roles = { "IntermediateEventOnActivityBoundary" } )
@Morph( base = BaseTask.class )
public class ScriptTask extends BaseTask {

    @Title
    public static final transient String title = "Script Task";

    @PropertySet
    @FieldDef( label = "Implementation/Execution", position = 1)
    @Valid
    protected EmptyTaskExecutionSet executionSet;

    @Property
    protected Script script;

    @NonPortable
    public static class ScriptTaskBuilder extends BaseTaskBuilder<ScriptTask> {

        @Override
        public ScriptTask build() {
            return new ScriptTask(new BPMNGeneral("Task"),
                    new EmptyTaskExecutionSet(),
                    new DataIOSet(),
                    new BackgroundSet(COLOR, BORDER_COLOR, BORDER_SIZE),
                    new FontSet(),
                    new RectangleDimensionsSet(WIDTH, HEIGHT),
                    new SimulationSet(),
                    new TaskType( TaskTypes.SCRIPT ),
                    new Script());
        }

    }

    public ScriptTask() {
        super( TaskTypes.SCRIPT );
    }

    public ScriptTask(@MapsTo("general") BPMNGeneral general,
                      @MapsTo("executionSet") EmptyTaskExecutionSet executionSet,
                      @MapsTo("dataIOSet") DataIOSet dataIOSet,
                      @MapsTo("backgroundSet") BackgroundSet backgroundSet,
                      @MapsTo("fontSet") FontSet fontSet,
                      @MapsTo("dimensionsSet") RectangleDimensionsSet dimensionsSet,
                      @MapsTo("simulationSet") SimulationSet simulationSet,
                      @MapsTo("taskType") TaskType taskType,
                      @MapsTo("script") Script script) {

        super(general, dataIOSet, backgroundSet, fontSet, dimensionsSet, simulationSet, taskType);
        this.script = script;
        this.executionSet = executionSet;

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

    public EmptyTaskExecutionSet getExecutionSet() {
        return executionSet;
    }

    public void setExecutionSet(EmptyTaskExecutionSet executionSet) {
        this.executionSet = executionSet;
    }
}
