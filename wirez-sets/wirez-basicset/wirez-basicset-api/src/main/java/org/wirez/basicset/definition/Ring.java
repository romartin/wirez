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

package org.wirez.basicset.definition;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.kie.workbench.common.forms.metaModel.FieldDef;
import org.wirez.basicset.definition.property.InnerRadius;
import org.wirez.basicset.definition.property.Name;
import org.wirez.basicset.definition.property.OuterRadius;
import org.wirez.basicset.definition.property.background.BackgroundAndBorderSet;
import org.wirez.basicset.definition.property.font.FontSet;
import org.wirez.basicset.shape.proxy.RingProxy;
import org.wirez.core.definition.annotation.Description;
import org.wirez.core.definition.annotation.Shape;
import org.wirez.core.definition.annotation.definition.*;
import org.wirez.core.definition.builder.Builder;
import org.wirez.core.factory.graph.NodeFactory;
import org.wirez.shapes.factory.BasicShapesFactory;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Portable
@Bindable
@Definition( graphFactory = NodeFactory.class, builder = Ring.RingBuilder.class )
@Shape( factory = BasicShapesFactory.class, proxy = RingProxy.class )
public class Ring {

    @Category
    public static final transient String category = Categories.BASIC;

    @Title
    public static final transient String title = "Ring";

    @Description
    public static final transient String description = "A ring";

    @Property
    @FieldDef( label = "Name", property = "value" )
    @Valid
    private Name name;

    @PropertySet
    @FieldDef( label = "Background and Borders", position = 0 )
    @Valid
    private BackgroundAndBorderSet backgroundSet;

    @PropertySet
    @FieldDef( label = "Font", position = 1 )
    @Valid
    private FontSet fontSet;

    @Property
    @FieldDef( label = "Outer Radius", property = "value" )
    @Valid
    private OuterRadius outerRadius;

    @Property
    @FieldDef( label = "Inner Radius", property = "value" )
    @Valid
    private InnerRadius innerRadius;

    @Labels
    private final Set<String> labels = new HashSet<String>() {{
        add( "all" );
    }};

    @NonPortable
    public static class RingBuilder implements Builder<Ring> {

        public static final String COLOR = "#ffEE00";
        public static final String BORDER_COLOR = "#000000";
        public static final Double OUTER_RADIUS = 25d;
        public static final Double INNER_RADIUS = 15d;
        public static final Double BORDER_SIZE = 1.5d;

        @Override
        public Ring build() {
            return new Ring( new Name( "Ring" ),
                    new BackgroundAndBorderSet( COLOR, BORDER_COLOR, BORDER_SIZE ),
                    new FontSet(),
                    new OuterRadius( OUTER_RADIUS ),
                    new InnerRadius( INNER_RADIUS ) );
        }

    }

    public Ring() {

    }

    public Ring(@MapsTo("name") Name name,
                @MapsTo("backgroundSet") BackgroundAndBorderSet backgroundSet,
                @MapsTo("fontSet") FontSet fontSet,
                @MapsTo("outerRadius") OuterRadius outerRadius,
                @MapsTo("innerRadius") InnerRadius innerRadius) {
        this.name = name;
        this.backgroundSet = backgroundSet;
        this.fontSet = fontSet;
        this.outerRadius = outerRadius;
        this.innerRadius = innerRadius;

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

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public BackgroundAndBorderSet getBackgroundSet() {
        return backgroundSet;
    }

    public void setBackgroundSet(BackgroundAndBorderSet backgroundSet) {
        this.backgroundSet = backgroundSet;
    }

    public FontSet getFontSet() {
        return fontSet;
    }

    public void setFontSet(FontSet fontSet) {
        this.fontSet = fontSet;
    }

    public OuterRadius getOuterRadius() {
        return outerRadius;
    }

    public void setOuterRadius(OuterRadius outerRadius) {
        this.outerRadius = outerRadius;
    }

    public InnerRadius getInnerRadius() {
        return innerRadius;
    }

    public void setInnerRadius(InnerRadius innerRadius) {
        this.innerRadius = innerRadius;
    }
}
