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

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import org.wirez.bpmn.client.shape.*;
import org.wirez.bpmn.client.shape.view.glyph.SequenceFlowGlyph;
import org.wirez.bpmn.definition.*;
import org.wirez.client.lienzo.canvas.wires.WiresCanvas;
import org.wirez.core.definition.util.DefinitionUtils;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.shape.MutableShape;
import org.wirez.core.client.shape.view.ShapeGlyph;
import org.wirez.core.client.shape.view.ShapeGlyphBuilder;
import org.wirez.shapes.client.view.CircleView;
import org.wirez.shapes.client.view.ConnectorView;
import org.wirez.shapes.client.view.RectangleView;
import org.wirez.shapes.client.view.ShapeViewFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.LinkedHashSet;
import java.util.Set;

@ApplicationScoped
public class BPMNShapeFactory extends BaseBPMNShapeFactory<BPMNDefinition, MutableShape<BPMNDefinition, ?>> {

    private static final Set<Class<?>> SUPPORTED_CLASSES = new LinkedHashSet<Class<?>>() {{
        add( BPMNDiagram.class );
        add( EndTerminateEvent.class );
        add( Lane.class );
        add( SequenceFlow.class );
        add( Task.class );
    }};

    DefinitionUtils definitionUtils;
    BPMNViewFactory bpmnViewFactory;
    ShapeGlyphBuilder<Group> glyphBuilder;
    
    protected BPMNShapeFactory() {
    }

    @Inject
    public BPMNShapeFactory( final ShapeViewFactory shapeViewFactory,
                             final DefinitionUtils definitionUtils,
                             final BPMNViewFactory bpmnViewFactory,
                             final ShapeGlyphBuilder<Group> glyphBuilder) {
        super(shapeViewFactory);
        this.definitionUtils = definitionUtils;
        this.bpmnViewFactory = bpmnViewFactory;
        this.glyphBuilder = glyphBuilder;
    }


    @Override
    public Set<Class<?>> getSupportedModelClasses() {
        return SUPPORTED_CLASSES;
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
    @SuppressWarnings("unchecked")
    public MutableShape<BPMNDefinition, ?> build(final BPMNDefinition definition,
                                                 final AbstractCanvasHandler context ) {

        final WiresManager wiresManager = context != null ? ((WiresCanvas) context.getCanvas()).getWiresManager() : null;
        final Class<?> definitionClass = definition.getClass();

        MutableShape result = null;
        
        if ( isDiagram( definitionClass ) ) {

            final BPMNDiagram diagram = (BPMNDiagram) definition;
            
            final RectangleView view = shapeViewFactory.rectangle( diagram.getWidth().getValue(),
                    diagram.getHeight().getValue(), wiresManager );
            
            result = new BPMNDiagramShape(view);
            
        } else if ( isEndTerminateEvent( definitionClass ) ) {

            final EndTerminateEvent endTerminateEvent = (EndTerminateEvent) definition;

            final CircleView view = shapeViewFactory.circle( endTerminateEvent.getRadius().getValue(), wiresManager );
            
            result = new EndTerminateEventShape(view);

        } else if ( isLane( definitionClass ) ) {

            final Lane lane = (Lane) definition;

            final RectangleView view = shapeViewFactory.rectangle(
                    lane.getWidth().getValue(),
                    lane.getHeight().getValue(), wiresManager );
            
            result = new LaneShape(view);

        } else if ( isSequenceFlow( definitionClass ) ) {

            final ConnectorView view = shapeViewFactory.connector( wiresManager, 0, 0, 100, 100 );
            
            result = new SequenceFlowShape(view);

        } else if ( isTask( definitionClass ) ) {

            final Task task = (Task) definition;

            final RectangleView view = bpmnViewFactory.task( 
                    task.getWidth().getValue(),
                    task.getHeight().getValue(), wiresManager );
            
            result = new TaskShape(view);

        }
        
        if ( null != result ) {
            
            return result;
            
        }

        throw new RuntimeException( "This factory should provide a shape for definition [" + definitionClass.getName() + "]" );
        
    }

    @Override
    public ShapeGlyph glyph(final Class<?> clazz, 
                            final double width, 
                            final double height) {

        // Custom shape glyphs.
        if ( isSequenceFlow( clazz) ) {
            
            return new SequenceFlowGlyph( width, height, SequenceFlow.COLOR );
            
        }
        
        final String id = getDefinitionId( clazz );

        // Generic glyphs, build from shapes.
        return glyphBuilder
                .definition( id )
                .factory( this )
                .height( height )
                .width( width )
                .build();
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
