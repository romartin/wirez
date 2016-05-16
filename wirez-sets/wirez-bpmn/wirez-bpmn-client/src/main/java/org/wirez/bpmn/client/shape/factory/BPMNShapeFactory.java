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

package org.wirez.bpmn.client.shape.factory;

import org.wirez.bpmn.api.*;
import org.wirez.bpmn.client.shape.*;
import org.wirez.bpmn.client.shape.glyph.EndTerminateEventGlyph;
import org.wirez.client.lienzo.canvas.wires.WiresCanvas;
import org.wirez.client.shapes.view.CircleView;
import org.wirez.client.shapes.view.ConnectorView;
import org.wirez.client.shapes.view.RectangleView;
import org.wirez.client.shapes.view.ShapeViewFactory;
import org.wirez.client.shapes.view.glyph.ConnectorGlyph;
import org.wirez.client.shapes.view.glyph.RectangleGlyph;
import org.wirez.core.api.definition.util.DefinitionUtils;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.shape.MutableShape;
import org.wirez.core.client.shape.factory.ShapeGlyphFactory;
import org.wirez.core.client.shape.view.ShapeGlyph;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.LinkedHashSet;
import java.util.Set;

@ApplicationScoped
public class BPMNShapeFactory extends BaseBPMNShapeFactory<BPMNDefinition, MutableShape<BPMNDefinition, ?>> 
        implements ShapeGlyphFactory {

    private static final Set<Class<?>> SUPPORTED_CLASSES = new LinkedHashSet<Class<?>>() {{
        add( BPMNDiagram.class );
        add( EndTerminateEvent.class );
        add( Lane.class );
        add( SequenceFlow.class );
        add( Task.class );
    }};

    DefinitionUtils definitionUtils;
    BPMNViewFactory bpmnViewFactory;
    
    protected BPMNShapeFactory() {
    }

    @Inject
    public BPMNShapeFactory( final ShapeViewFactory shapeViewFactory,
                             final DefinitionUtils definitionUtils,
                             final BPMNViewFactory bpmnViewFactory ) {
        super(shapeViewFactory);
        this.definitionUtils = definitionUtils;
        this.bpmnViewFactory = bpmnViewFactory;
    }


    @Override
    public Set<Class<?>> getSupportedModelClasses() {
        return SUPPORTED_CLASSES;
    }

    @Override
    protected ShapeGlyphFactory getGlyphFactory( final Class<?> clazz ) {
        return this;
    }

    @Override
    public String getDescription( final Class<?> clazz ) {
        
        if ( isDiagram( clazz ) ) {

            return BPMNDiagram.title;

        }  else if ( isEndTerminateEvent( clazz ) ) {

            return EndTerminateEvent.title;

        } else if ( isLane( clazz ) ) {

            return Lane.title;

        } else if ( isSequenceFlow( clazz ) ) {

            return SequenceFlow.title;

        } else if ( isTask( clazz ) ) {

            return Task.title;

        }
        
        return "BPMN";
    }

    @Override
    public ShapeGlyph build( final Class<?> clazz ) {
        return build( clazz, 50, 50 );
    }

    @Override
    @SuppressWarnings("unchecked")
    public MutableShape<BPMNDefinition, ?> build( final BPMNDefinition definition, 
                                                  final AbstractCanvasHandler context ) {

        final WiresCanvas wiresCanvas = (WiresCanvas) context.getCanvas();
        final Class<?> definitionClass = definition.getClass();

        MutableShape result = null;
        
        if ( isDiagram( definitionClass ) ) {

            final BPMNDiagram diagram = (BPMNDiagram) definition;
            
            final RectangleView view = shapeViewFactory.rectangle( diagram.getWidth().getValue(),
                    diagram.getHeight().getValue(),
                    wiresCanvas.getWiresManager());
            
            result = new BPMNDiagramShape(view);
            
        } else if ( isEndTerminateEvent( definitionClass ) ) {

            final EndTerminateEvent endTerminateEvent = (EndTerminateEvent) definition;

            final CircleView view = shapeViewFactory.circle( endTerminateEvent.getRadius().getValue(),
                    wiresCanvas.getWiresManager());
            
            result = new EndTerminateEventShape(view);

        } else if ( isLane( definitionClass ) ) {

            final Lane lane = (Lane) definition;

            final RectangleView view = shapeViewFactory.rectangle(
                    lane.getWidth().getValue(),
                    lane.getHeight().getValue(),
                    wiresCanvas.getWiresManager());
            
            result = new LaneShape(view);

        } else if ( isSequenceFlow( definitionClass ) ) {

            final ConnectorView view = shapeViewFactory.connector(wiresCanvas.getWiresManager(), 0,0,100,100);
            
            result = new SequenceFlowShape(view);

        } else if ( isTask( definitionClass ) ) {

            final Task task = (Task) definition;

            final RectangleView view = bpmnViewFactory.task( 
                    task.getWidth().getValue(),
                    task.getHeight().getValue(), 
                    wiresCanvas.getWiresManager());
            
            result = new TaskShape(view);

        }
        
        if ( null != result ) {
            
            return result;
            
        }

        throw new RuntimeException( "This factory should provide a shape for definition [" + definitionClass.getName() + "]" );
        
    }

    @Override
    public ShapeGlyph build(final Class<?> clazz, 
                            final double width, 
                            final double height) {
        
        if ( isDiagram( clazz ) ) {

            return new RectangleGlyph(width, height, BPMNDiagram.COLOR);
            
        }  else if ( isEndTerminateEvent( clazz ) ) {

            return new EndTerminateEventGlyph(width/2, EndTerminateEvent.COLOR);
            
        } else if ( isLane( clazz ) ) {

            return new RectangleGlyph(width, height, Lane.COLOR);

        } else if ( isSequenceFlow( clazz ) ) {

            return new ConnectorGlyph(width, height, SequenceFlow.COLOR);

        } else if ( isTask( clazz ) ) {

            return new RectangleGlyph(width, height, Task.COLOR);

        }

        throw new RuntimeException( "This factory should provide a shape glyph for definition [" + clazz.getName() + "]" );
    }
    
    private boolean isDiagram( final Class<?> clazz ) {
        return clazz.equals( BPMNDiagram.class );
    }

    private boolean isEndTerminateEvent( final Class<?> clazz ) {
        return clazz.equals( EndTerminateEvent.class );
    }

    private boolean isLane( final Class<?> clazz ) {
        return clazz.equals( Lane.class );
    }

    private boolean isSequenceFlow( final Class<?> clazz ) {
        return clazz.equals( SequenceFlow.class );
    }

    private boolean isTask( final Class<?> clazz ) {
        return clazz.equals( Task.class );
    }

}
