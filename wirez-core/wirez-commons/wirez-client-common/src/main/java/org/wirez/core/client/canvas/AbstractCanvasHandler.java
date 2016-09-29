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

import com.google.gwt.logging.client.LogConfiguration;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.api.ClientDefinitionManager;
import org.wirez.core.client.canvas.event.registration.CanvasElementAddedEvent;
import org.wirez.core.client.canvas.event.registration.CanvasElementRemovedEvent;
import org.wirez.core.client.canvas.event.registration.CanvasElementUpdatedEvent;
import org.wirez.core.client.canvas.event.registration.CanvasElementsClearEvent;
import org.wirez.core.client.canvas.listener.CanvasElementListener;
import org.wirez.core.client.canvas.listener.HasCanvasListeners;
import org.wirez.core.client.canvas.util.CanvasLayoutUtils;
import org.wirez.core.client.command.factory.CanvasCommandFactory;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.service.ServiceCallback;
import org.wirez.core.client.shape.GraphShape;
import org.wirez.core.client.shape.Lifecycle;
import org.wirez.core.client.shape.MutationContext;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.factory.ShapeFactory;
import org.wirez.core.client.shape.view.ShapeView;
import org.wirez.core.client.shape.view.animation.AnimationTweener;
import org.wirez.core.client.shape.view.animation.HasAnimations;
import org.wirez.core.diagram.Diagram;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.Bounds;
import org.wirez.core.graph.content.definition.DefinitionSet;
import org.wirez.core.graph.content.view.BoundImpl;
import org.wirez.core.graph.content.view.BoundsImpl;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.graph.processing.index.IncrementalIndexBuilder;
import org.wirez.core.graph.processing.index.Index;
import org.wirez.core.graph.processing.index.IndexBuilder;
import org.wirez.core.graph.util.GraphUtils;
import org.wirez.core.rule.Rule;
import org.wirez.core.rule.graph.GraphRulesManager;
import org.wirez.core.util.UUID;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractCanvasHandler<D extends Diagram, C extends AbstractCanvas>
        implements CanvasHandler<D, C>, HasCanvasListeners<CanvasElementListener> {

    private static Logger LOGGER = Logger.getLogger( AbstractCanvasHandler.class.getName() );

    protected ClientDefinitionManager clientDefinitionManager;
    protected ClientFactoryServices clientFactoryServices;
    protected GraphRulesManager rulesManager;
    protected GraphUtils graphUtils;
    protected IndexBuilder<Graph<?, Node>, Node, Edge, Index<Node, Edge>> indexBuilder;
    protected ShapeManager shapeManager;
    protected Event<CanvasElementAddedEvent> canvasElementAddedEvent;
    protected Event<CanvasElementRemovedEvent> canvasElementRemovedEvent;
    protected Event<CanvasElementUpdatedEvent> canvasElementUpdatedEvent;
    protected Event<CanvasElementsClearEvent> canvasElementsClearEvent;
    protected CanvasCommandFactory canvasCommandFactory;

    private final String uuid;
    protected C canvas;
    protected D diagram;
    protected Index<?, ?> graphIndex;
    protected final List<CanvasElementListener> listeners = new LinkedList<>();

    @Inject
    public AbstractCanvasHandler( final ClientDefinitionManager clientDefinitionManager,
                                  final ClientFactoryServices clientFactoryServices,
                                  final GraphRulesManager rulesManager,
                                  final GraphUtils graphUtils,
                                  final IncrementalIndexBuilder indexBuilder,
                                  final ShapeManager shapeManager,
                                  final Event<CanvasElementAddedEvent> canvasElementAddedEvent,
                                  final Event<CanvasElementRemovedEvent> canvasElementRemovedEvent,
                                  final Event<CanvasElementUpdatedEvent> canvasElementUpdatedEvent,
                                  final Event<CanvasElementsClearEvent> canvasElementsClearEvent,
                                  final CanvasCommandFactory canvasCommandFactory ) {
        this.clientDefinitionManager = clientDefinitionManager;
        this.clientFactoryServices = clientFactoryServices;
        this.rulesManager = rulesManager;
        this.graphUtils = graphUtils;
        this.indexBuilder = ( IndexBuilder<Graph<?, Node>, Node, Edge, Index<Node, Edge>> ) indexBuilder;
        this.shapeManager = shapeManager;
        this.canvasElementAddedEvent = canvasElementAddedEvent;
        this.canvasElementRemovedEvent = canvasElementRemovedEvent;
        this.canvasElementUpdatedEvent = canvasElementUpdatedEvent;
        this.canvasElementsClearEvent = canvasElementsClearEvent;
        this.canvasCommandFactory = canvasCommandFactory;
        this.uuid = UUID.uuid();
    }

    @Override
    public CanvasHandler<D, C> initialize( final C canvas ) {
        this.canvas = canvas;
        return this;
    }

    @Override
    public AbstractCanvasHandler<D, C> draw( final D diagram ) {
        this.diagram = diagram;

        // Initialize the graph handler that provides processing and querying operations over the graph.

        this.graphIndex = indexBuilder.build( diagram.getGraph() );

        initializeGraphBounds( diagram );

        doLoadRules();

        return this;
    }

    // TODO: just temporal...
    @SuppressWarnings( "unchecked" )
    private void initializeGraphBounds( final D diagram ) {

        final double w = getCanvas().getWidth();
        final double h = getCanvas().getHeight();
        final Bounds bounds = new BoundsImpl( new BoundImpl( 0d, 0d ), new BoundImpl( w, h ) );

        final Graph<DefinitionSet, ?> graph = diagram.getGraph();
        graph.getContent().setBounds( bounds );

    }

    protected void doLoadRules() {

        // Load the rules that apply for the diagram.
        final String defSetId = getDiagram().getSettings().getDefinitionSetId();

        clientFactoryServices.newDefinition( defSetId, new ServiceCallback<Object>() {
            @Override
            public void onSuccess( Object definitionSet ) {

                final Collection<Rule> rules = clientDefinitionManager.adapters().forRules().getRules( definitionSet );
                if ( rules != null ) {
                    for ( final Rule rule : rules ) {
                        rulesManager.addRule( rule );
                    }
                }

                // Run the draw command.
                canvasCommandFactory.DRAW().execute( AbstractCanvasHandler.this );


            }

            @Override
            public void onError( ClientRuntimeError error ) {
                showError( error );
            }
        } );

    }

    @Override
    public C getCanvas() {
        return canvas;
    }

    @Override
    public D getDiagram() {
        return diagram;
    }

    /*
        ***************************************************************************************
        * Shape/element handling
        ***************************************************************************************
     */

    public void register( final ShapeFactory<Object, AbstractCanvasHandler, Shape> factory,
                          final Element<View<?>> candidate ) {
        register( factory, candidate, true );
    }

    @SuppressWarnings( "unchecked" )
    public void register( final ShapeFactory<Object, AbstractCanvasHandler, Shape> factory,
                          final Element<View<?>> candidate,
                          final boolean fireEvents ) {
        assert factory != null && candidate != null;

        final Shape shape = factory.build( candidate.getContent().getDefinition(), AbstractCanvasHandler.this );

        // Set the same identifier as the graph element's one.
        if ( null == shape.getUUID() ) {
            shape.setUUID( candidate.getUUID() );
        }

        shape.getShapeView().setZIndex( 0 );

        // Add the shapes on canvas and fire events.
        canvas.addShape( shape );
        canvas.draw();

        // Parents can register controls etc here.
        doRegister( shape, candidate, factory );

        if ( fireEvents ) {

            // Fire listeners.
            fireCanvasElementAdded( candidate );

            // Fire updates.
            afterElementAdded( candidate, shape );

        }

    }

    protected void doRegister( final Shape shape, final Element element, final ShapeFactory factory ) {

    }

    public void deregister( final Element element ) {
        deregister( element, true );
    }

    public void deregister( final Element element,
                            final boolean fireEvents ) {
        final Shape shape = canvas.getShape( element.getUUID() );


        if ( fireEvents ) {

            // Fire listeners.
            fireCanvasElementRemoved( element );

            // Fire events.
            beforeElementDeleted( element, shape );

        }

        // TODO: Delete connector connections to the node being deleted?
        doDeregister( shape, element );
        canvas.deleteShape( shape );
        canvas.draw();

        if ( fireEvents ) {
            afterElementDeleted( element, shape );
        }
    }

    protected void doDeregister( final Shape shape, final Element element ) {

    }

    public void applyElementMutation( final Element element, final MutationContext mutationContext ) {
        applyElementMutation( element, true, true, mutationContext );
    }

    public void updateElementPosition( final Element element, final MutationContext mutationContext ) {
        applyElementMutation( element, true, false, mutationContext );
    }

    public void updateElementProperties( final Element element, final MutationContext mutationContext ) {
        applyElementMutation( element, false, true, mutationContext );
    }

    @SuppressWarnings( "unchecked" )
    public void applyElementMutation( final Element candidate,
                                      final boolean applyPosition,
                                      final boolean applyProperties,
                                      final MutationContext mutationContext ) {

        final Shape shape = canvas.getShape( candidate.getUUID() );

        if ( shape instanceof GraphShape ) {

            final GraphShape graphShape = ( GraphShape ) shape;

            if ( applyPosition ) {
                graphShape.applyPosition( candidate, mutationContext );
            }

            if ( applyProperties ) {
                graphShape.applyProperties( candidate, mutationContext );
            }

            canvas.draw();

            fireCanvasElementUpdated( candidate );

            afterElementUpdated( candidate, graphShape );

            if ( mutationContext instanceof MutationContext.AnimationContext ) {
                final MutationContext.AnimationContext animationContext = ( MutationContext.AnimationContext ) mutationContext;
                animateShape( shape, animationContext.getTweener(), animationContext.getDuration() );
            }

        }

    }

    protected void animateShape( final Shape shape,
                                 final AnimationTweener tweener,
                                 final double duration ) {
        final ShapeView<?> view = shape.getShapeView();
        if ( view instanceof HasAnimations ) {
            final HasAnimations hasAnimation = ( HasAnimations ) view;
            hasAnimation.animate( tweener, duration );
        }
    }

    public void addChild( final Element parent, final Element child ) {

        if ( !isCanvasRoot( parent ) ) {

            final Shape parentShape = canvas.getShape( parent.getUUID() );
            final Shape childShape = canvas.getShape( child.getUUID() );
            handleParentChildZIndex( parent, child, parentShape, childShape, true );
            canvas.addChildShape( parentShape, childShape );

        }

    }

    public void removeChild( final String parentUUID, final String childUUID ) {

        if ( !isCanvasRoot( parentUUID ) ) {

            final Shape parentShape = canvas.getShape( parentUUID );
            final Shape childShape = canvas.getShape( childUUID );
            handleParentChildZIndex( null, null, parentShape, childShape, false );
            canvas.deleteChildShape( parentShape, childShape );

        }

    }

    private boolean isCanvasRoot( final Element parent ) {
        return CanvasLayoutUtils.isCanvasRoot( getDiagram(), parent );
    }

    private boolean isCanvasRoot( final String pUUID ) {
        return CanvasLayoutUtils.isCanvasRoot( getDiagram(), pUUID );
    }

    public void dock( final Element parent, final Element child ) {

        if ( !isCanvasRoot( parent ) ) {

            final Shape parentShape = canvas.getShape( parent.getUUID() );
            final Shape childShape = canvas.getShape( child.getUUID() );
            handleParentChildZIndex( parent, child, parentShape, childShape, true );
            canvas.dock( parentShape, childShape );

        }

    }

    public void undock( final String parentUUID, final String childUUID ) {

        if ( !isCanvasRoot( parentUUID ) ) {

            final Shape parentShape = canvas.getShape( parentUUID );
            final Shape childShape = canvas.getShape( childUUID );
            handleParentChildZIndex( null, null, parentShape, childShape, false );
            canvas.undock( parentShape, childShape );

        }

    }

    protected void handleParentChildZIndex( final Element parent,
                                            final Element child,
                                            final Shape parentShape,
                                            final Shape childShape,
                                            final boolean add ) {

        if ( add ) {

            handleZIndex( childShape, parentShape.getShapeView().getZIndex() + 1 );
            handleZIndex( child, parentShape.getShapeView().getZIndex() + 1 );

        } else {

            handleZIndex( childShape, 0 );
            final Element element = getGraphIndex().get( childShape.getUUID() );
            if ( null != element ) {
                handleZIndex( element, 0 );
            }

        }

    }

    @SuppressWarnings( "unchecked" )
    protected void handleZIndex( final Element child,
                                 final int zindex ) {

        // ZIndex for child shape's outgoing connectors.
        if ( child instanceof Node ) {
            final Node childNode = ( Node ) child;
            final List<Edge> outEdges = childNode.getOutEdges();
            if ( null != outEdges && !outEdges.isEmpty() ) {

                final Set<String> suuids = new LinkedHashSet<>();

                for ( final Edge edge : outEdges ) {
                    if ( edge.getContent() instanceof View ) {
                        suuids.add( edge.getUUID() );
                    }
                }

                handleZIndex( suuids, zindex );
            }
        }

    }

    protected void handleZIndex( final Set<String> shapeUUIDs,
                                 final int zindex ) {

        for ( final String suuid : shapeUUIDs ) {
            final Shape edgeShape = canvas.getShape( suuid );
            handleZIndex( edgeShape, zindex );
        }

    }

    protected void handleZIndex( final Shape shape,
                                 final int zindex ) {
        if ( null != shape ) {
            shape.getShapeView().setZIndex( zindex );
        }
    }

    public void clearCanvas() {

        fireCanvasClear();

        canvasElementsClearEvent.fire( new CanvasElementsClearEvent( this ) );

        canvas.clear();
        canvas.draw();

    }

    @Override
    public void destroy() {
        canvas.destroy();
        graphIndex.clear();
        listeners.clear();

        canvas = null;
        graphIndex = null;
        diagram = null;
        clientDefinitionManager = null;
        clientFactoryServices = null;
        rulesManager = null;
        graphUtils = null;
        indexBuilder = null;
        shapeManager = null;
    }

    @Override
    public HasCanvasListeners<CanvasElementListener> addRegistrationListener( final CanvasElementListener instance ) {
        listeners.add( instance );
        return this;
    }

    @Override
    public HasCanvasListeners<CanvasElementListener> removeRegistrationListener( final CanvasElementListener instance ) {
        listeners.remove( instance );
        return this;
    }

    @Override
    public HasCanvasListeners<CanvasElementListener> clearRegistrationListeners() {
        listeners.clear();
        return this;
    }

    protected void fireCanvasElementRemoved( final Element candidate ) {

        for ( final CanvasElementListener instance : listeners ) {

            instance.deregister( candidate );

        }

    }

    protected void fireCanvasElementAdded( final Element candidate ) {

        for ( final CanvasElementListener instance : listeners ) {

            instance.register( candidate );

        }

    }

    protected void fireCanvasElementUpdated( final Element candidate ) {

        for ( final CanvasElementListener instance : listeners ) {

            instance.update( candidate );

        }

    }

    protected void fireCanvasClear() {

        for ( final CanvasElementListener instance : listeners ) {

            instance.clear();

        }

    }

    protected void afterElementAdded( final Element element, final Shape shape ) {

        // Fire a canvas element added event. 
        canvasElementAddedEvent.fire( new CanvasElementAddedEvent( this, element ) );

    }

    protected void beforeElementDeleted( final Element element, final Shape shape ) {

        // Fire a canvas element deleted event. 
        canvasElementRemovedEvent.fire( new CanvasElementRemovedEvent( this, element ) );

    }

    protected void afterElementDeleted( final Element element, final Shape shape ) {

    }

    protected void afterElementUpdated( final Element element, final Shape shape ) {
        if ( shape instanceof Lifecycle ) {
            final Lifecycle lifecycle = ( Lifecycle ) shape;
            lifecycle.afterDraw();
        }

        // Fire a canvas element added event. 
        canvasElementUpdatedEvent.fire( new CanvasElementUpdatedEvent( this, element ) );

    }

    protected void showError( final ClientRuntimeError error ) {
        final String message = error.getThrowable() != null ?
                error.getThrowable().getMessage() : error.getMessage();
        log( Level.SEVERE, message );
    }

    public ClientDefinitionManager getClientDefinitionManager() {
        return clientDefinitionManager;
    }

    public ClientFactoryServices getClientFactoryServices() {
        return clientFactoryServices;
    }

    public GraphRulesManager getRuleManager() {
        return rulesManager;
    }

    public GraphUtils getGraphUtils() {
        return graphUtils;
    }

    public Index<?, ?> getGraphIndex() {
        return graphIndex;
    }

    public IndexBuilder<Graph<?, Node>, Node, Edge, Index<Node, Edge>> getIndexBuilder() {
        return indexBuilder;
    }

    public ShapeManager getShapeManager() {
        return shapeManager;
    }

    protected String getDefinitionId( final Object definition ) {
        return clientDefinitionManager.adapters().forDefinition().getId( definition );
    }

    @Override
    public boolean equals( final Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( !( o instanceof AbstractCanvasHandler ) ) {
            return false;
        }

        AbstractCanvasHandler that = ( AbstractCanvasHandler ) o;

        return uuid.equals( that.uuid );

    }

    @Override
    public String toString() {
        return "AbstractCanvasHandler [" + uuid + "]";
    }

    private void log( final Level level, final String message ) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log( level, message );
        }
    }

}
