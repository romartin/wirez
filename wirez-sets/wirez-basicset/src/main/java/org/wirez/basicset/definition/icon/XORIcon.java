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

package org.wirez.basicset.definition.icon;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.wirez.basicset.definition.Categories;
import org.wirez.basicset.definition.property.Height;
import org.wirez.basicset.definition.property.Name;
import org.wirez.basicset.definition.property.Width;
import org.wirez.basicset.definition.property.background.BackgroundAndBorderSet;
import org.wirez.basicset.shape.proxy.icon.XORIconProxy;
import org.wirez.core.definition.annotation.Description;
import org.wirez.core.definition.annotation.definition.*;
import org.wirez.core.graph.Node;
import org.wirez.core.definition.annotation.Shape;
import org.wirez.shapes.factory.BasicShapesFactory;

import java.util.HashSet;
import java.util.Set;

@Portable
@Bindable
@Definition( type = Node.class )
@Shape( factory = BasicShapesFactory.class, 
        proxy = XORIconProxy.class )
public class XORIcon implements Icon {

    @Category
    public static final transient String category = Categories.ICONS;

    @Title
    public static final transient String title = "XOR Icon";

    @Description
    public static final transient String description = "XOR Icon";

    public static final transient String COLOR = Icon.COLOR;
    public static final transient Double WIDTH = Icon.WIDTH;
    public static final transient Double HEIGHT = Icon.HEIGHT;
    public static final transient Double BORDER_SIZE = Icon.BORDER_SIZE;

    @Property
    private Name name;

    @PropertySet
    private BackgroundAndBorderSet backgroundSet;

    @Property
    private Width width;

    @Property
    private Height height;

    @Labels
    private final Set<String> labels = new HashSet<String>() {{
        add( "all" );
    }};

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
