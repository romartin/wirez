/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
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

package org.wirez.core.client.canvas;

import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.client.ShapeSet;

public class DefaultCanvasSettings implements CanvasSettings {
    
    private String uuid;
    private DefinitionSet definitionSet;
    private ShapeSet shapeSet;
    private String title;
    private Graph<? extends Node> graph;
    private Canvas canvas;

    public DefaultCanvasSettings() {
    }

    @Override
    public String getUUID() {
        return uuid;
    }
    
    public void setUUUID(String uuid) {
        this.uuid = uuid;
    }

    public Graph<? extends Node> getGraph() {
        return graph;
    }

    public void setGraph(Graph<? extends Node> graph) {
        this.graph = graph;
    }

    @Override
    public DefinitionSet getDefinitionSet() {
        return definitionSet;
    }

    public void setDefinitionSet(DefinitionSet definitionSet) {
        this.definitionSet = definitionSet;
    }

    @Override
    public ShapeSet getShapeSet() {
        return shapeSet;
    }

    public void setShapeSet(ShapeSet shapeSet) {
        this.shapeSet = shapeSet;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }
}
