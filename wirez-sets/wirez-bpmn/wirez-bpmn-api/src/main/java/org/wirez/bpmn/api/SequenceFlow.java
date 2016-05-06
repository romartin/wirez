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

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.wirez.bpmn.api.property.general.BPMNGeneral;
import org.wirez.bpmn.api.property.general.BackgroundSet;
import org.wirez.core.api.definition.annotation.Description;
import org.wirez.core.api.definition.annotation.definition.Category;
import org.wirez.core.api.definition.annotation.definition.Definition;
import org.wirez.core.api.definition.annotation.definition.Labels;
import org.wirez.core.api.definition.annotation.definition.PropertySet;
import org.wirez.core.api.definition.annotation.definition.Title;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.rule.annotation.CanConnect;
import org.wirez.core.api.rule.annotation.EdgeOccurrences;

@Portable
@Bindable
@Definition( type = Edge.class )

// Connection rules.
@CanConnect( startRole = "sequence_start", endRole = "sequence_end" )
@CanConnect( startRole = "choreography_sequence_start", endRole = "choreography_sequence_end" )
@CanConnect( startRole = "Exclusive_Eventbased_Gateway", endRole = "FromEventbasedGateway" )
@CanConnect( startRole = "EventbasedGateway", endRole = "FromEventbasedGateway" )

// Edge cardinality rules.
@EdgeOccurrences(role="Startevents_all", type = EdgeOccurrences.EdgeType.INCOMING, max = 0)
@EdgeOccurrences(role="Endevents_all", type = EdgeOccurrences.EdgeType.OUTGOING, max = 0)

public class SequenceFlow implements BPMNDefinition {

    @Category
    public static final transient String category = "Connecting Objects";

    @Title
    public static final transient String title = "Sequence Flow";

    @Description
    public static final transient String description = "A Sequence Flow";

    public static final transient String COLOR = "#000000";
    public static final transient String BORDER_COLOR = "#000000";
    public static final Double BORDER_SIZE = 3d;

    @PropertySet
    private BPMNGeneral general;

    @PropertySet
    private BackgroundSet backgroundSet;

    @Labels
    private final Set<String> labels = new HashSet<String>() {{
        add( "all" );
        add( "ConnectingObjectsMorph" );
    }};

    public SequenceFlow() {

    }

    public SequenceFlow(@MapsTo("general") BPMNGeneral general,
                 @MapsTo("backgroundSet") BackgroundSet backgroundSet) {
        this.general = general;
        this.backgroundSet = backgroundSet;
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

    public void setGeneral( BPMNGeneral general ) {
        this.general = general;
    }

    public void setBackgroundSet( BackgroundSet backgroundSet ) {
        this.backgroundSet = backgroundSet;
    }
}
