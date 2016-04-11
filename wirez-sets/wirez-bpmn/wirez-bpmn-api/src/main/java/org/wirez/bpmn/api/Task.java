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

package org.wirez.bpmn.api;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.livespark.formmodeler.metaModel.FieldDef;
import org.wirez.bpmn.api.property.Height;
import org.wirez.bpmn.api.property.Width;
import org.wirez.bpmn.api.property.general.BPMNGeneral;
import org.wirez.bpmn.api.property.general.BackgroundSet;
import org.wirez.bpmn.api.property.general.FontSet;
import org.wirez.bpmn.api.property.simulation.*;
import org.wirez.core.api.definition.annotation.Description;
import org.wirez.core.api.definition.annotation.definition.*;
import org.wirez.core.api.graph.Node;

import java.util.HashSet;
import java.util.Set;

@Portable
@Bindable
@Definition( type = Node.class )
public class Task implements BPMNDefinition {

    @Category
    public static final String category = "Activities";

    @Title
    public static final String title = "A Task";

    @Description
    public static final String description = "A task is a unit of work - the job to be performed";
    
    public static final String COLOR = "#dfeff8";
    public static final Double WIDTH = 136d;
    public static final Double HEIGHT = 48d;
    public static final Double BORDER_SIZE = 0.5d;
    
    @PropertySet
    private BPMNGeneral general;

    @PropertySet
    private BackgroundSet backgroundSet;

    @PropertySet
    private FontSet fontSet;

    @Property
    @FieldDef(label = "Width", property = "value")
    private Width width;

    @Property
    @FieldDef(label = "Height", property = "value")
    private Height height;

    @Property
    private Min min;

    @Property
    private Max max;

    @Property
    private Mean mean;

    @Property
    @FieldDef(label = "TimeUnit", property = "value")
    private TimeUnit timeUnit;

    @Property
    private StandardDeviation standardDeviation;

    @Property
    private DistributionType distributionType;

    @Property
    private Quantity quantity;

    @Property
    private WorkingHours workingHours;

    @Property
    private UnitCost unitCost;

    @Property
    private Currency currency;

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

    public Task() {
        
    }
    
    public Task(@MapsTo("general") BPMNGeneral general,
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
                @MapsTo("currency") Currency currency) {
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

    public Min getMin() {
        return min;
    }

    public Max getMax() {
        return max;
    }

    public Mean getMean() {
        return mean;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public StandardDeviation getStandardDeviation() {
        return standardDeviation;
    }

    public DistributionType getDistributionType() {
        return distributionType;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public WorkingHours getWorkingHours() {
        return workingHours;
    }

    public UnitCost getUnitCost() {
        return unitCost;
    }

    public Currency getCurrency() {
        return currency;
    }

}
