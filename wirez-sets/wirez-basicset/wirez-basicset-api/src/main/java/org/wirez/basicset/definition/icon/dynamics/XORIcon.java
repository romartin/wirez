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

package org.wirez.basicset.definition.icon.dynamics;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.kie.workbench.common.forms.metaModel.FieldDef;
import org.wirez.basicset.definition.Categories;
import org.wirez.basicset.definition.property.Height;
import org.wirez.basicset.definition.property.Name;
import org.wirez.basicset.definition.property.Width;
import org.wirez.basicset.definition.property.background.BackgroundAndBorderSet;
import org.wirez.basicset.shape.proxy.icon.dynamics.XORIconProxy;
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
@Definition( graphFactory = NodeFactory.class, builder = XORIcon.XORIconBuilder.class )
@Shape( factory = BasicShapesFactory.class,
        proxy = XORIconProxy.class )
public class XORIcon implements DynamicIcon {

    @Category
    public static final transient String category = Categories.ICONS;

    @Title
    public static final transient String title = "XOR Icon";

    @Description
    public static final transient String description = "XOR Icon";

    @Property
    @FieldDef( label = "Name", property = "value" )
    @Valid
    private Name name;

    @PropertySet
    @FieldDef( label = "Background and Borders", position = 0 )
    @Valid
    private BackgroundAndBorderSet backgroundSet;

    @Property
    @FieldDef( label = "Width", property = "value" )
    @Valid
    private Width width;

    @Property
    @FieldDef( label = "Height", property = "value" )
    @Valid
    private Height height;

    @Labels
    private final Set<String> labels = new HashSet<String>() {{
        add( "all" );
    }};

    @NonPortable
    public static class XORIconBuilder implements Builder<XORIcon> {

        public static final String COLOR = DynamicIcon.COLOR;
        public static final Double WIDTH = DynamicIcon.WIDTH;
        public static final Double HEIGHT = DynamicIcon.HEIGHT;
        public static final Double BORDER_SIZE = DynamicIcon.BORDER_SIZE;
        public static final String BORDER_COLOR = "#000000";

        @Override
        public XORIcon build() {
            return new XORIcon( new Name( "XOR" ),
                    new BackgroundAndBorderSet( COLOR, BORDER_COLOR, BORDER_SIZE ),
                    new Width( WIDTH ),
                    new Height( HEIGHT ) );
        }

    }

    public XORIcon() {

    }

    public XORIcon(@MapsTo("name") Name name,
                   @MapsTo("backgroundSet") BackgroundAndBorderSet backgroundSet,
                   @MapsTo("width") Width width,
                   @MapsTo("height") Height height) {
        this.name = name;
        this.backgroundSet = backgroundSet;
        this.width = width;
        this.height = height;

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

    public Width getWidth() {
        return width;
    }

    public void setWidth(Width width) {
        this.width = width;
    }

    public Height getHeight() {
        return height;
    }

    public void setHeight(Height height) {
        this.height = height;
    }

}
