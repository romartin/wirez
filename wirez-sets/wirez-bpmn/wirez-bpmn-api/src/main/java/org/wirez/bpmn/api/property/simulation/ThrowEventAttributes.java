package org.wirez.bpmn.api.property.simulation;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.wirez.bpmn.api.BPMNPropertySet;

@Portable
@Bindable
@org.wirez.core.api.annotation.propertyset.PropertySet
public class ThrowEventAttributes implements BPMNPropertySet {

    public static final String ID = "throwEventAttributes";
    public static final String NAME = "ThrowEventAttributes";

    @org.wirez.core.api.annotation.definition.Property
    private Min min;

    @org.wirez.core.api.annotation.definition.Property
    private Max max;

    @org.wirez.core.api.annotation.definition.Property
    private Mean mean;

    @org.wirez.core.api.annotation.definition.Property
    private TimeUnit timeUnit;

    @org.wirez.core.api.annotation.definition.Property
    private StandardDeviation standardDeviation;

    @org.wirez.core.api.annotation.definition.Property
    private DistributionType distributionType;

    public ThrowEventAttributes() {
    }

    public ThrowEventAttributes(@MapsTo("min") Min min,
                                @MapsTo("max") Max max,
                                @MapsTo("mean") Mean mean,
                                @MapsTo("timeUnit") TimeUnit timeUnit,
                                @MapsTo("standardDeviation") StandardDeviation standardDeviation,
                                @MapsTo("distributionType") DistributionType distributionType) {
        this.min = min;
        this.max = max;
        this.mean = mean;
        this.timeUnit = timeUnit;
        this.standardDeviation = standardDeviation;
        this.distributionType = distributionType;
    }

    @Override
    public String getPropertySetId() {
        return ID;
    }

    @Override
    public String getPropertySetName() {
        return NAME;
    }

    public Min getMin() {
        return min;
    }

    public void setMin(Min min) {
        this.min = min;
    }

    public Max getMax() {
        return max;
    }

    public void setMax(Max max) {
        this.max = max;
    }

    public Mean getMean() {
        return mean;
    }

    public void setMean(Mean mean) {
        this.mean = mean;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public StandardDeviation getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(StandardDeviation standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    public DistributionType getDistributionType() {
        return distributionType;
    }

    public void setDistributionType(DistributionType distributionType) {
        this.distributionType = distributionType;
    }
}
