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
import org.kie.workbench.common.forms.metaModel.FieldDef;
import org.kie.workbench.common.forms.metaModel.Slider;
import org.wirez.bpmn.definition.property.Radius;
import org.wirez.bpmn.definition.property.background.BackgroundSet;
import org.wirez.bpmn.definition.property.font.FontSet;
import org.wirez.bpmn.definition.property.general.BPMNGeneral;
import org.wirez.bpmn.definition.property.simulation.ThrowEventAttributes;
import org.wirez.bpmn.shape.proxy.EndNoneEventShapeProxy;
import org.wirez.core.definition.annotation.Description;
import org.wirez.core.definition.annotation.Shape;
import org.wirez.core.definition.annotation.definition.Definition;
import org.wirez.core.definition.annotation.definition.Title;
import org.wirez.core.definition.annotation.morph.Morph;
import org.wirez.core.graph.Node;
import org.wirez.shapes.factory.BasicShapesFactory;

@Portable
@Bindable
@Definition( type = Node.class, builder = EndNoneEvent.EndNoneEventBuilder.class )
@Shape( factory = BasicShapesFactory.class, proxy = EndNoneEventShapeProxy.class )
@Morph( base = BaseEndEvent.class )
public class EndNoneEvent extends BaseEndEvent {

    @Title
    public static final transient String title = "End Event";

    @Description
    public static final transient String description = "Untyped end event";

    @NonPortable
    public static class EndNoneEventBuilder extends BaseEndEventBuilder<EndNoneEvent> {

        @Override
        public EndNoneEvent build() {
            return new EndNoneEvent(  new BPMNGeneral( "End" ),
                    new BackgroundSet( COLOR, BORDER_COLOR, BORDER_SIZE ),
                    new FontSet(),
                    new ThrowEventAttributes(),
                    new Radius( RADIUS ) );
        }

    }

    public EndNoneEvent()  {

    }

    public EndNoneEvent(@MapsTo("general") BPMNGeneral general,
                 @MapsTo("backgroundSet") BackgroundSet backgroundSet,
                 @MapsTo("fontSet") FontSet fontSet,
                 @MapsTo("throwEventAttributes") ThrowEventAttributes throwEventAttributes,
                 @MapsTo("radius") Radius radius) {

        super( general, backgroundSet, fontSet, throwEventAttributes, radius );

    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

}
