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

package org.wirez.core.client.canvas.command.impl;


import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.impl.ViewEdge;
import org.wirez.core.api.graph.impl.ViewElement;
import org.wirez.core.api.graph.impl.ViewNode;
import org.wirez.core.client.factory.ShapeFactory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DefaultCanvasCommands {

    public ClearCanvasCommand CLEAR() {
        return new ClearCanvasCommand( );
    }

    public AddCanvasNodeCommand ADD_NODE(final ViewNode node, final ShapeFactory factory ) {
        return new AddCanvasNodeCommand( node, factory );
    }

    public DeleteCanvasNodeCommand DELETE_NODE(final ViewNode node) {
        return new DeleteCanvasNodeCommand( node );
    }
    
    public AddCanvasEdgeCommand ADD_EDGE(final ViewEdge edge, final ShapeFactory factory ) {
        return new AddCanvasEdgeCommand( edge, factory );
    }

    public DeleteCanvasEdgeCommand DELETE_EDGE(final ViewEdge edge) {
        return new DeleteCanvasEdgeCommand( edge );
    }

    public AddCanvasChildNodeCommand ADD_CHILD(final ViewNode parent, ViewNode child, final ShapeFactory factory ) {
        return new AddCanvasChildNodeCommand( parent, child, factory );
    }
    
    // TODO: Remove parent command.
    
    public MoveCanvasElementCommand MOVE( final ViewElement element ,
                                          final Double x,
                                          final Double y ) {
        return new MoveCanvasElementCommand( element, x, y );
    }

    public SetCanvasElementSizeCommand RESIZE( final ViewElement element ,
                                               final Double w,
                                               final Double h) {
        return new SetCanvasElementSizeCommand( element, w, h );
    }

    public UpdateCanvasElementPropertyValueCommand UPDATE_PROPERTY( final ViewElement element ,
                                                                    final Property property,
                                                                    final Object value ) {
        return new UpdateCanvasElementPropertyValueCommand( element, property, value );
    }
}
