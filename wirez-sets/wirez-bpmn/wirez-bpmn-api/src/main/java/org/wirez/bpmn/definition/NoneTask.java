package org.wirez.bpmn.definition;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.wirez.bpmn.definition.property.Height;
import org.wirez.bpmn.definition.property.Width;
import org.wirez.bpmn.definition.property.background.BackgroundSet;
import org.wirez.bpmn.definition.property.font.FontSet;
import org.wirez.bpmn.definition.property.general.BPMNGeneral;
import org.wirez.bpmn.definition.property.simulation.*;
import org.wirez.bpmn.definition.property.task.TaskType;
import org.wirez.core.definition.annotation.definition.Definition;
import org.wirez.core.definition.annotation.definition.Title;
import org.wirez.core.graph.Node;
import org.wirez.core.rule.annotation.CanDock;

@Portable
@Bindable
@Definition( type = Node.class, builder = NoneTask.NoneTaskBuilder.class )
@CanDock( roles = { "IntermediateEventOnActivityBoundary" } )
public class NoneTask extends BaseTask {

    @Title
    public static final transient String title = "A None Task";

    @NonPortable
    public static class NoneTaskBuilder extends BaseTaskBuilder<NoneTask> {

        @Override
        public NoneTask build() {
            return new NoneTask(  new BPMNGeneral( "Task" ),
                    new BackgroundSet( COLOR, BORDER_COLOR, BORDER_SIZE ),
                    new FontSet(),
                    new Width( WIDTH ),
                    new Height( HEIGHT ),
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
                    new TaskType( TaskType.TaskTypes.NONE ) );
        }

    }
    
    public NoneTask() {
        super( TaskType.TaskTypes.NONE );
    }

    public NoneTask(@MapsTo("general") BPMNGeneral general, 
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
                    @MapsTo("taskType") TaskType taskType) {
        
        super(general, backgroundSet, fontSet, width, height, min, max, mean, timeUnit, standardDeviation, 
                distributionType, quantity, workingHours, unitCost, currency, taskType);
        
    }

    public String getTitle() {
        return title;
    }
}
