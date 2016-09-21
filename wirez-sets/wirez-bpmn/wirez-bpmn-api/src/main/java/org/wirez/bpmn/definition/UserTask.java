package org.wirez.bpmn.definition;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.kie.workbench.common.forms.metaModel.FieldDef;
import org.wirez.bpmn.definition.property.dimensions.RectangleDimensionsSet;
import org.wirez.bpmn.definition.property.assignee.AssigneeSet;
import org.wirez.bpmn.definition.property.background.BackgroundSet;
import org.wirez.bpmn.definition.property.dataio.DataIOSet;
import org.wirez.bpmn.definition.property.font.FontSet;
import org.wirez.bpmn.definition.property.general.BPMNGeneral;
import org.wirez.bpmn.definition.property.simulation.*;
import org.wirez.bpmn.definition.property.task.EmptyTaskExecutionSet;
import org.wirez.bpmn.definition.property.task.TaskType;
import org.wirez.bpmn.definition.property.task.TaskTypes;
import org.wirez.core.definition.annotation.definition.Definition;
import org.wirez.core.definition.annotation.definition.PropertySet;
import org.wirez.core.definition.annotation.definition.Title;
import org.wirez.core.definition.annotation.morph.Morph;
import org.wirez.core.factory.graph.NodeFactory;
import org.wirez.core.rule.annotation.CanDock;

import javax.validation.Valid;

@Portable
@Bindable
@Definition( graphFactory = NodeFactory.class, builder = UserTask.UserTaskBuilder.class )
@CanDock( roles = { "IntermediateEventOnActivityBoundary" } )
@Morph( base = BaseTask.class )
public class UserTask extends BaseTask {

    @Title
    public static final transient String title = "User Task";

    @PropertySet
    @FieldDef( label = "Implementation/Execution", position = 1)
    @Valid
    protected EmptyTaskExecutionSet executionSet;

    @FieldDef( label = "Assigned to", position = 2)
    protected AssigneeSet assigneeSet;

    @NonPortable
    public static class UserTaskBuilder extends BaseTaskBuilder<UserTask> {

        @Override
        public UserTask build() {
            return new UserTask(new BPMNGeneral("Task"),
                    new EmptyTaskExecutionSet(),
                    new AssigneeSet(),
                    new DataIOSet(),
                    new BackgroundSet(COLOR, BORDER_COLOR, BORDER_SIZE),
                    new FontSet(),
                    new RectangleDimensionsSet(WIDTH, HEIGHT),
                    new SimulationSet(),
                    new TaskType( TaskTypes.USER ));
        }

    }

    public UserTask() {
        super( TaskTypes.USER );
    }

    public UserTask(@MapsTo("general") BPMNGeneral general,
                    @MapsTo("executionSet") EmptyTaskExecutionSet executionSet,
                    @MapsTo("assigneeSet") AssigneeSet assigneeSet,
                    @MapsTo("dataIOSet") DataIOSet dataIOSet,
                    @MapsTo("backgroundSet") BackgroundSet backgroundSet,
                    @MapsTo("fontSet") FontSet fontSet,
                    @MapsTo("dimensionsSet") RectangleDimensionsSet dimensionsSet,
                    @MapsTo("simulationSet") SimulationSet simulationSet,
                    @MapsTo("taskType") TaskType taskType) {

        super(general, dataIOSet, backgroundSet, fontSet, dimensionsSet, simulationSet, taskType);
        this.executionSet = executionSet;
        this.assigneeSet = assigneeSet;
    }

    public String getTitle() {
        return title;
    }

    public AssigneeSet getAssigneeSet() {
        return assigneeSet;
    }

    public void setAssigneeSet(AssigneeSet assigneeSet) {
        this.assigneeSet = assigneeSet;
    }

    public EmptyTaskExecutionSet getExecutionSet() {
        return executionSet;
    }

    public void setExecutionSet(EmptyTaskExecutionSet executionSet) {
        this.executionSet = executionSet;
    }
}
