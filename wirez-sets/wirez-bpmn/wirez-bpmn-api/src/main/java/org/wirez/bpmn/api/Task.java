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

import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.livespark.formmodeler.metaModel.FieldDef;
import org.wirez.bpmn.api.property.Height;
import org.wirez.bpmn.api.property.Width;
import org.wirez.bpmn.api.property.general.BPMNGeneral;
import org.wirez.bpmn.api.property.general.BackgroundSet;
import org.wirez.bpmn.api.property.general.FontSet;
import org.wirez.bpmn.api.property.simulation.Currency;
import org.wirez.bpmn.api.property.simulation.DistributionType;
import org.wirez.bpmn.api.property.simulation.Max;
import org.wirez.bpmn.api.property.simulation.Mean;
import org.wirez.bpmn.api.property.simulation.Min;
import org.wirez.bpmn.api.property.simulation.Quantity;
import org.wirez.bpmn.api.property.simulation.StandardDeviation;
import org.wirez.bpmn.api.property.simulation.TimeUnit;
import org.wirez.bpmn.api.property.simulation.UnitCost;
import org.wirez.bpmn.api.property.simulation.WorkingHours;
import org.wirez.core.api.definition.annotation.Description;
import org.wirez.core.api.definition.annotation.definition.Category;
import org.wirez.core.api.definition.annotation.definition.Definition;
import org.wirez.core.api.definition.annotation.definition.Labels;
import org.wirez.core.api.definition.annotation.definition.Property;
import org.wirez.core.api.definition.annotation.definition.PropertySet;
import org.wirez.core.api.definition.annotation.definition.Title;
import org.wirez.core.api.graph.Node;

@Portable
@Bindable
@Definition( type = Node.class )
public class Task implements BPMNDefinition {

    @Category
    public static final transient String category = "Activities";

    @Title
    public static final transient String title = "A Task";

    @Description
    public static final transient String description = "A task is a unit of work - the job to be performed";

    public static final transient String COLOR = "#dfeff8";
    public static final Double WIDTH = 136d;
    public static final Double HEIGHT = 48d;
    public static final Double BORDER_SIZE = 0.5d;

    @PropertySet
    @FieldDef( label = "General Settings", position = 0)
    private BPMNGeneral general;

    @PropertySet
    @FieldDef( label = "Background Settings", position = 1)
    @Valid
    private BackgroundSet backgroundSet;

    @PropertySet
    private FontSet fontSet;

    @Property
    @FieldDef(label = "Width", property = "value")
    @Valid
    private Width width;

    @Property
    @FieldDef(label = "Height", property = "value")
    @Valid
    private Height height;

    @Property
    private Min min;

    @Property
    private Max max;

    @Property
    private Mean mean;

    @Property
    @FieldDef(label = "TimeUnit", property = "value")
    @Valid
    private TimeUnit timeUnit;

    @Property
    @FieldDef(label = "Standard Deviation", property = "value")
    private StandardDeviation standardDeviation;

    @Property
    @FieldDef(label = "Distribution Type", property = "value")
    private DistributionType distributionType;

    @Property
    @FieldDef(label = "Quantity", property = "value")
    private Quantity quantity;

    @Property
    @FieldDef(label = "WorkingHours", property = "value")
    private WorkingHours workingHours;

    @Property
    @FieldDef(label = "UnitCost", property = "value")
    private UnitCost unitCost;

    @Property
    @FieldDef(label = "Currency", property = "value")
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

    public void setMin( Min min ) {
        this.min = min;
    }

    public void setMax( Max max ) {
        this.max = max;
    }

    public void setMean( Mean mean ) {
        this.mean = mean;
    }

    public void setTimeUnit( TimeUnit timeUnit ) {
        this.timeUnit = timeUnit;
    }

    public void setStandardDeviation( StandardDeviation standardDeviation ) {
        this.standardDeviation = standardDeviation;
    }

    public void setDistributionType( DistributionType distributionType ) {
        this.distributionType = distributionType;
    }

    public void setQuantity( Quantity quantity ) {
        this.quantity = quantity;
    }

    public void setWorkingHours( WorkingHours workingHours ) {
        this.workingHours = workingHours;
    }

    public void setUnitCost( UnitCost unitCost ) {
        this.unitCost = unitCost;
    }

    public void setCurrency( Currency currency ) {
        this.currency = currency;
    }
}
