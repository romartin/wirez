/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import org.wirez.bpmn.shape.proxy.TaskShapeProxy;
import org.wirez.core.definition.annotation.Description;
import org.wirez.core.definition.annotation.Shape;
import org.wirez.core.definition.annotation.definition.*;
import org.wirez.core.definition.factory.Builder;
import org.wirez.core.graph.Node;
import org.wirez.core.rule.annotation.CanDock;
import org.wirez.shapes.factory.BasicShapesFactory;

import java.util.HashSet;
import java.util.Set;

@Portable
@Bindable
@Definition( type = Node.class, builder = Task.TaskBuilder.class )
@Shape( factory = BasicShapesFactory.class, proxy = TaskShapeProxy.class )
@CanDock( roles = { "IntermediateEventOnActivityBoundary" } )
public class Task implements BPMNDefinition {

    @Category
    public static final transient String category = Categories.ACTIVITIES;

    @Title
    public static final transient String title = "A Task";

    @Description
    public static final transient String description = "A task is a unit of work - the job to be performed";
    
    @PropertySet
    private BPMNGeneral general;

    @PropertySet
    private BackgroundSet backgroundSet;

    @PropertySet
    private FontSet fontSet;

    @Property
    private Width width;

    @Property
    private Height height;

    @Property
    private org.wirez.bpmn.definition.property.simulation.Min min;

    @Property
    private org.wirez.bpmn.definition.property.simulation.Max max;

    @Property
    private org.wirez.bpmn.definition.property.simulation.Mean mean;

    @Property
    private org.wirez.bpmn.definition.property.simulation.TimeUnit timeUnit;

    @Property
    private org.wirez.bpmn.definition.property.simulation.StandardDeviation standardDeviation;

    @Property
    private org.wirez.bpmn.definition.property.simulation.DistributionType distributionType;

    @Property
    private org.wirez.bpmn.definition.property.simulation.Quantity quantity;

    @Property
    private org.wirez.bpmn.definition.property.simulation.WorkingHours workingHours;

    @Property
    private org.wirez.bpmn.definition.property.simulation.UnitCost unitCost;

    @Property
    private org.wirez.bpmn.definition.property.simulation.Currency currency;

    @Property
    private TaskType taskType;
    
    @Labels
    private final Set<String> labels = new HashSet<String>() {{
        add( "all" );
        add( "sequence_start" );
        add( "sequence_end" );
        add( "from_task_event" );
        add( "to_task_event" );
        add( "FromEventbasedGateway" );
        add( "messageflow_start" );
        add( "messageflow_end" );
        add( "fromtoall" );
        add( "ActivitiesMorph" );
    }};

    @NonPortable
    public static class TaskBuilder implements Builder<Task> {

        public static final String COLOR = "#f9fad2";
        public static final Double WIDTH = 136d;
        public static final Double HEIGHT = 48d;
        public static final Double BORDER_SIZE = 1d;
        public static final String BORDER_COLOR = "#000000";

        @Override
        public Task build() {
            return new Task(  new BPMNGeneral( "Task" ),
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
                    new TaskType() );
        }

    }

    public Task() {

    }

    public Task(@MapsTo("general") BPMNGeneral general,
                @MapsTo("backgroundSet") BackgroundSet backgroundSet,
                @MapsTo("fontSet") FontSet fontSet,
                @MapsTo("width") Width width,
                @MapsTo("height") Height height,
                @MapsTo("min") org.wirez.bpmn.definition.property.simulation.Min min,
                @MapsTo("max") org.wirez.bpmn.definition.property.simulation.Max max,
                @MapsTo("mean") org.wirez.bpmn.definition.property.simulation.Mean mean,
                @MapsTo("timeUnit") org.wirez.bpmn.definition.property.simulation.TimeUnit timeUnit,
                @MapsTo("standardDeviation") org.wirez.bpmn.definition.property.simulation.StandardDeviation standardDeviation,
                @MapsTo("distributionType") org.wirez.bpmn.definition.property.simulation.DistributionType distributionType,
                @MapsTo("quantity") org.wirez.bpmn.definition.property.simulation.Quantity quantity,
                @MapsTo("workingHours") org.wirez.bpmn.definition.property.simulation.WorkingHours workingHours,
                @MapsTo("unitCost") org.wirez.bpmn.definition.property.simulation.UnitCost unitCost,
                @MapsTo("currency") org.wirez.bpmn.definition.property.simulation.Currency currency,
                @MapsTo("taskType") TaskType taskType ) {
        this.general = general;
        this.backgroundSet = backgroundSet;
        this.fontSet = fontSet;
        this.width = width;
        this.height = height;
        this.min = min;
        this.max = max;
        this.mean = mean;
        this.timeUnit = timeUnit;
        this.standardDeviation = standardDeviation;
        this.distributionType = distributionType;
        this.quantity = quantity;
        this.workingHours = workingHours;
        this.unitCost = unitCost;
        this.currency = currency;
        this.taskType = taskType;

    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
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

    public BackgroundSet getBackgroundSet() {
        return backgroundSet;
    }

    public FontSet getFontSet() {
        return fontSet;
    }

    public Width getWidth() {
        return width;
    }

    public Height getHeight() {
        return height;
    }

    public org.wirez.bpmn.definition.property.simulation.Min getMin() {
        return min;
    }

    public org.wirez.bpmn.definition.property.simulation.Max getMax() {
        return max;
    }

    public org.wirez.bpmn.definition.property.simulation.Mean getMean() {
        return mean;
    }

    public org.wirez.bpmn.definition.property.simulation.TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public org.wirez.bpmn.definition.property.simulation.StandardDeviation getStandardDeviation() {
        return standardDeviation;
    }

    public org.wirez.bpmn.definition.property.simulation.DistributionType getDistributionType() {
        return distributionType;
    }

    public org.wirez.bpmn.definition.property.simulation.Quantity getQuantity() {
        return quantity;
    }

    public org.wirez.bpmn.definition.property.simulation.WorkingHours getWorkingHours() {
        return workingHours;
    }

    public org.wirez.bpmn.definition.property.simulation.UnitCost getUnitCost() {
        return unitCost;
    }

    public org.wirez.bpmn.definition.property.simulation.Currency getCurrency() {
        return currency;
    }

    public void setGeneral( BPMNGeneral general ) {
        this.general = general;
    }

    public void setBackgroundSet( BackgroundSet backgroundSet ) {
        this.backgroundSet = backgroundSet;
    }

    public void setFontSet( FontSet fontSet ) {
        this.fontSet = fontSet;
    }

    public void setWidth( Width width ) {
        this.width = width;
    }

    public void setHeight( Height height ) {
        this.height = height;
    }

    public void setMin( org.wirez.bpmn.definition.property.simulation.Min min ) {
        this.min = min;
    }

    public void setMax( org.wirez.bpmn.definition.property.simulation.Max max ) {
        this.max = max;
    }

    public void setMean( org.wirez.bpmn.definition.property.simulation.Mean mean ) {
        this.mean = mean;
    }

    public void setTimeUnit( org.wirez.bpmn.definition.property.simulation.TimeUnit timeUnit ) {
        this.timeUnit = timeUnit;
    }

    public void setStandardDeviation( org.wirez.bpmn.definition.property.simulation.StandardDeviation standardDeviation ) {
        this.standardDeviation = standardDeviation;
    }

    public void setDistributionType( org.wirez.bpmn.definition.property.simulation.DistributionType distributionType ) {
        this.distributionType = distributionType;
    }

    public void setQuantity( org.wirez.bpmn.definition.property.simulation.Quantity quantity ) {
        this.quantity = quantity;
    }

    public void setWorkingHours( org.wirez.bpmn.definition.property.simulation.WorkingHours workingHours ) {
        this.workingHours = workingHours;
    }

    public void setUnitCost( org.wirez.bpmn.definition.property.simulation.UnitCost unitCost ) {
        this.unitCost = unitCost;
    }

    public void setCurrency( org.wirez.bpmn.definition.property.simulation.Currency currency ) {
        this.currency = currency;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }
    
}
