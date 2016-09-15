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
import org.wirez.basicset.definition.property.background.BackgroundAndBorderSet;
import org.wirez.basicset.shape.proxy.BasicConnectorProxy;
import org.wirez.core.definition.annotation.Description;
import org.wirez.core.definition.annotation.Shape;
import org.wirez.core.definition.annotation.definition.*;
import org.wirez.core.definition.builder.Builder;
import org.wirez.core.factory.graph.EdgeFactory;
import org.wirez.core.rule.annotation.CanConnect;
import org.wirez.shapes.factory.BasicShapesFactory;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Portable
@Bindable
@Definition( graphFactory = EdgeFactory.class, builder = BasicConnector.BasicConnectorBuilder.class )
@CanConnect( startRole = "all", endRole = "all" )
@Shape( factory = BasicShapesFactory.class, proxy = BasicConnectorProxy.class )
public class BasicConnector  {

    @Category
    public static final transient String category = Categories.CONNECTORS;

    @Title
    public static final transient String title = "Basic Connector";

    @Description
    public static final transient String description = "A Basic Connector";

    @Property
    @FieldDef( label = "Name", property = "value" )
    @Valid
    private Name name;

    @PropertySet
    @FieldDef( label = "Background and Borders", position = 0 )
    @Valid
    private BackgroundAndBorderSet backgroundSet;

    @Labels
    private final Set<String> labels = new HashSet<String>() {{
        add( "all" );
    }};

    @NonPortable
    public static class BasicConnectorBuilder implements Builder<BasicConnector> {

        public static final transient String COLOR = "#000000";
        public static final transient String BORDER_COLOR = "#000000";
        public static final Double BORDER_SIZE = 3d;

        @Override
        public BasicConnector build() {
            return new BasicConnector(  new Name( "Connector" ),
                    new BackgroundAndBorderSet( COLOR, BORDER_COLOR, BORDER_SIZE ) );
        }

    }

    public BasicConnector() {

    }

    public BasicConnector( @MapsTo("name") Name name,
                           @MapsTo("backgroundSet") BackgroundAndBorderSet backgroundSet) {
        this.name = name;
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

    public Name getName() {
        return name;
    }

    public void setName( Name name ) {
        this.name = name;
    }

    public BackgroundAndBorderSet getBackgroundSet() {
        return backgroundSet;
    }

    public void setBackgroundSet( BackgroundAndBorderSet backgroundSet ) {
        this.backgroundSet = backgroundSet;
    }

}
