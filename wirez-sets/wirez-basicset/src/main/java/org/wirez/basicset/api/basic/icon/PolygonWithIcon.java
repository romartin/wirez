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

package org.wirez.basicset.api.basic.icon;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.wirez.basicset.api.Categories;
import org.wirez.basicset.api.property.IconType;
import org.wirez.basicset.api.property.Name;
import org.wirez.basicset.api.property.Radius;
import org.wirez.basicset.api.property.background.BackgroundAndBorderSet;
import org.wirez.basicset.api.property.font.FontSet;
import org.wirez.basicset.client.shape.proxy.PolygonWithIconProxy;
import org.wirez.client.shapes.factory.BasicShapesFactory;
import org.wirez.core.api.definition.annotation.Description;
import org.wirez.core.api.definition.annotation.definition.*;
import org.wirez.core.api.graph.Node;
import org.wirez.core.client.annotation.Shape;

import java.util.HashSet;
import java.util.Set;

@Portable
@Bindable
@Definition( type = Node.class )
@Shape( factory = BasicShapesFactory.class, 
        proxy = PolygonWithIconProxy.class )
public class PolygonWithIcon {

    @Category
    public static final transient String category = Categories.BASIC_WITH_ICONS;

    @Title
    public static final transient String title = "A polygon with an icon";

    @Description
    public static final transient String description = "A polygon with an icon";
    
    public static final transient String COLOR = "#00FF66";
    public static final Double RADIUS = 50d;
    public static final Double BORDER_SIZE = 1d;

    @Property
    private Name name;

    @PropertySet
    private BackgroundAndBorderSet backgroundSet;

    @PropertySet
    private FontSet fontSet;

    @Property
    private Radius radius;

    @Property
    private IconType iconType;

    @Labels
    private final Set<String> labels = new HashSet<String>() {{
        add( "all" );
    }};

    public PolygonWithIcon() {

    }

    public PolygonWithIcon(@MapsTo("name") Name name,
                           @MapsTo("backgroundSet") BackgroundAndBorderSet backgroundSet,
                           @MapsTo("fontSet") FontSet fontSet,
                           @MapsTo("radius") Radius radius,
                           @MapsTo("iconType") IconType iconType) {
        this.name = name;
        this.backgroundSet = backgroundSet;
        this.fontSet = fontSet;
        this.radius = radius;
        this.iconType = iconType;

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

    public IconType getIconType() {
        return iconType;
    }

    public void setIconType(IconType iconType) {
        this.iconType = iconType;
    }
}
