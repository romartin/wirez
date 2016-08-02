package org.wirez.bpmn.definition;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.wirez.bpmn.definition.property.Height;
import org.wirez.bpmn.definition.property.Width;
import org.wirez.bpmn.definition.property.background.BackgroundSet;
import org.wirez.bpmn.definition.property.dataio.DataIOSet;
import org.wirez.bpmn.definition.property.font.FontSet;
import org.wirez.bpmn.definition.property.general.BPMNGeneral;
import org.wirez.bpmn.definition.property.simulation.*;
import org.wirez.bpmn.definition.property.task.Actor;
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
@Definition( graphFactory = NodeFactory.class, builder = UserTask.UserTaskBuilder.class )
@CanDock( roles = { "IntermediateEventOnActivityBoundary" } )
@Morph( base = BaseTask.class )
public class UserTask extends BaseTask {

    @Title
    public static final transient String title = "User Task";

    @Property
    protected Actor actor;

    @NonPortable
    public static class UserTaskBuilder extends BaseTaskBuilder<UserTask> {

        @Override
        public UserTask build() {
            return new UserTask(new BPMNGeneral("Task"),
                    new DataIOSet(),
                    new BackgroundSet(COLOR, BORDER_COLOR, BORDER_SIZE),
                    new FontSet(),
                    new Width(WIDTH),
                    new Height(HEIGHT),
                    new Min(),
                    new Max(),
                    new Mean(),
                    new TimeUnit(),
                    new StandardDeviation(),
                    new DistributionType(),
                    new Quantity(),
                    new WorkingHours(),
                    new UnitCost(),
                    new Currency(),
                    new TaskType( TaskTypes.USER ),
                    new Actor());
        }

    }

    public UserTask() {
        super( TaskTypes.USER );
    }

    public UserTask(@MapsTo("general") BPMNGeneral general,
                    @MapsTo("dataIOSet") DataIOSet dataIOSet,
                    @MapsTo("backgroundSet") BackgroundSet backgroundSet,
                    @MapsTo("fontSet") FontSet fontSet,
                    @MapsTo("width") Width width,
                    @MapsTo("height") Height height,
                    @MapsTo("min") Min min,
                    @MapsTo("max") Max max,
                    @MapsTo("mean") Mean mean,
                    @MapsTo("timeUnit") TimeUnit timeUnit,
                    @MapsTo("standardDeviation") StandardDeviation standardDeviation,
                    @MapsTo("distributionType") DistributionType distributionType,
                    @MapsTo("quantity") Quantity quantity,
                    @MapsTo("workingHours") WorkingHours workingHours,
                    @MapsTo("unitCost") UnitCost unitCost,
                    @MapsTo("currency") Currency currency,
                    @MapsTo("taskType") TaskType taskType,
                    @MapsTo("actor") Actor actor) {

        super(general, dataIOSet, backgroundSet, fontSet, width, height, min, max, mean, timeUnit, standardDeviation,
                distributionType, quantity, workingHours, unitCost, currency, taskType);
        this.actor = actor;

    }

    public String getTitle() {
        return title;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }
}
