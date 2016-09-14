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
import org.wirez.basicset.definition.property.Name;
import org.wirez.basicset.definition.property.Radius;
import org.wirez.basicset.definition.property.background.BackgroundAndBorderSet;
import org.wirez.basicset.definition.property.font.FontSet;
import org.wirez.basicset.shape.proxy.PolygonProxy;
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
@Definition( graphFactory = NodeFactory.class, builder = Polygon.PolygonBuilder.class )
@Shape( factory = BasicShapesFactory.class, proxy = PolygonProxy.class )
public class Polygon {

    @Category
    public static final transient String category = Categories.BASIC;

    @Title
    public static final transient String title = "Polygon";

    @Description
    public static final transient String description = "A polygon";

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
    @FieldDef( label = "Radius", property = "value" )
    @Valid
    private Radius radius;

    @Labels
    private final Set<String> labels = new HashSet<String>() {{
        add( "all" );
    }};

    @NonPortable
    public static class PolygonBuilder implements Builder<Polygon> {

        public static final String COLOR = "#00FF66";
        public static final String BORDER_COLOR = "#000000";
        public static final Double RADIUS = 50d;
        public static final Double BORDER_SIZE = 1d;

        @Override
        public Polygon build() {
            return new Polygon( new Name( "Polygon" ),
                    new BackgroundAndBorderSet( COLOR, BORDER_COLOR, BORDER_SIZE ),
                    new FontSet(),
                    new Radius( RADIUS ) );
        }

    }

    public Polygon() {

    }

    public Polygon(@MapsTo("name") Name name,
                   @MapsTo("backgroundSet") BackgroundAndBorderSet backgroundSet,
                   @MapsTo("fontSet") FontSet fontSet,
                   @MapsTo("radius") Radius radius) {
        this.name = name;
        this.backgroundSet = backgroundSet;
        this.fontSet = fontSet;
        this.radius = radius;

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

    public Radius getRadius() {
        return radius;
    }

    public void setRadius(Radius radius) {
        this.radius = radius;
    }
}
