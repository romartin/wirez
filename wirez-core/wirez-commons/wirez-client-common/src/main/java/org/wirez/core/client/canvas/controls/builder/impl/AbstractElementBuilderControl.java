package org.wirez.core.client.canvas.controls.builder.impl;

import com.google.gwt.core.client.GWT;
import org.wirez.core.client.api.ClientDefinitionManager;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.command.CanvasCommandManager;
import org.wirez.core.client.command.CanvasViolation;
import org.wirez.core.client.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.controls.AbstractCanvasHandlerControl;
import org.wirez.core.client.canvas.controls.builder.ElementBuilderControl;
import org.wirez.core.client.canvas.controls.builder.request.ElementBuildRequest;
import org.wirez.core.client.canvas.util.CanvasLayoutUtils;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.service.ServiceCallback;
import org.wirez.core.client.shape.factory.ShapeFactory;
import org.wirez.core.command.Command;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.graph.processing.index.bounds.GraphBoundsIndexer;
import org.wirez.core.graph.util.GraphUtils;
import org.wirez.core.rule.RuleManager;
import org.wirez.core.rule.RuleViolation;
import org.wirez.core.rule.RuleViolations;
import org.wirez.core.rule.model.ModelCardinalityRuleManager;
import org.wirez.core.rule.model.ModelContainmentRuleManager;
import org.wirez.core.util.UUID;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public abstract class AbstractElementBuilderControl extends AbstractCanvasHandlerControl 
        implements ElementBuilderControl<AbstractCanvasHandler> {

    private static Logger LOGGER = Logger.getLogger(AbstractElementBuilderControl.class.getName());

    ClientDefinitionManager clientDefinitionManager;
    ClientFactoryServices clientFactoryServices;
    CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager;
    CanvasCommandFactory canvasCommandFactory;
    GraphUtils graphUtils;
    ModelContainmentRuleManager modelContainmentRuleManager;
    ModelCardinalityRuleManager modelCardinalityRuleManager;
    GraphBoundsIndexer graphBoundsIndexer;
    CanvasLayoutUtils canvasLayoutUtils;

    public AbstractElementBuilderControl(final ClientDefinitionManager clientDefinitionManager,
                                         final ClientFactoryServices clientFactoryServices,
                                         final CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager,
                                         final GraphUtils graphUtils,
                                         final ModelContainmentRuleManager modelContainmentRuleManager,
                                         final ModelCardinalityRuleManager modelCardinalityRuleManager,
                                         final CanvasCommandFactory canvasCommandFactory,
                                         final GraphBoundsIndexer graphBoundsIndexer,
                                         final CanvasLayoutUtils canvasLayoutUtils) {
        this.clientDefinitionManager = clientDefinitionManager;
        this.clientFactoryServices = clientFactoryServices;
        this.canvasCommandManager = canvasCommandManager;
        this.graphUtils = graphUtils;
        this.modelContainmentRuleManager = modelContainmentRuleManager;
        this.modelCardinalityRuleManager = modelCardinalityRuleManager;
        this.canvasCommandFactory = canvasCommandFactory;
        this.graphBoundsIndexer = graphBoundsIndexer;
        this.canvasLayoutUtils = canvasLayoutUtils;
    }
    
    @Override
    public boolean allows(final ElementBuildRequest<AbstractCanvasHandler> request) {
        
        final double x = request.getX();
        final double y = request.getY();
        final Object definition = request.getDefinition();

        final Node<View<?>, Edge> parent = getParent( x, y );
        final Set<String> labels = clientDefinitionManager.adapters().forDefinition().getLabels( definition );

        if ( null != parent ) {
            final Object parentDef = parent.getContent().getDefinition();
            final String parentId = clientDefinitionManager.adapters().forDefinition().getId( parentDef );
            final RuleViolations containmentViolations = modelContainmentRuleManager.evaluate( parentId, labels );

            if ( !isValid( containmentViolations ) ) {
                return false;
            }

        }

        final int count = graphUtils.countDefinitions( canvasHandler.getDiagram().getGraph(), definition );
        final RuleViolations cardinalityViolations =  modelCardinalityRuleManager.evaluate( labels, count, RuleManager.Operation.ADD );

        return isValid( cardinalityViolations );
    }
    
    @Override
    public void build(final ElementBuildRequest<AbstractCanvasHandler> request,
                        final BuildCallback buildCallback ) {

        if ( null == canvasHandler ) {
            buildCallback.onSuccess( null );
            return;
        }

        double x = 0;
        double y = 0;
        if ( request.getX() == -1 || request.getY() == -1 ) {

            final double[] p = canvasLayoutUtils.getNextLayoutPosition( canvasHandler );
            x = p[0] + 50;
            y = p[1] > 0 ? p[1] : 200 ;

        } else {

            x = request.getX();
            y = request.getY();

        }

        final Object definition = request.getDefinition();
        final ShapeFactory<?, AbstractCanvasHandler, ?> factory = request.getShapeFactory();

        // Notify processing starts.
        fireProcessingStarted();

        final Node<View<?>, Edge> parent = getParent( x, y );

        final Double[] childCoordinates = getChildCoordinates( parent, x, y );

        getCommands(definition, factory, parent, childCoordinates[0], childCoordinates[1], new CommandsCallback() {

            @Override
            public void onComplete( final String uuid,
                                    final List<Command<AbstractCanvasHandler, CanvasViolation>> commands ) {

                for ( final Command<AbstractCanvasHandler, CanvasViolation> command : commands ) {
                    canvasCommandManager.batch( command);
                }

                canvasCommandManager.executeBatch( canvasHandler );

                buildCallback.onSuccess( uuid );

                // Notify processing ends.
                fireProcessingCompleted();
            }

            @Override
            public void onError( final ClientRuntimeError error ) {

                buildCallback.onError( error );

                // Notify processing ends.
                fireProcessingCompleted();
            }

        });

    }

    @Override
    protected void doDisable() {
        graphBoundsIndexer.destroy();
        graphBoundsIndexer = null;
        clientDefinitionManager = null;
        clientFactoryServices = null;
        canvasCommandManager = null;
        canvasCommandFactory = null;
        graphUtils = null;
        modelContainmentRuleManager.clearRules();
        modelCardinalityRuleManager.clearRules();
        modelContainmentRuleManager = null;
        modelCardinalityRuleManager = null;
    }

    public interface CommandsCallback {

        void onComplete( String uuid, List<Command<AbstractCanvasHandler, CanvasViolation>> commands );

        void onError( ClientRuntimeError error );

    }

    public void getCommands(final Object definition,
                               final ShapeFactory factory,
                               final Node<View<?>, Edge> parent,
                               final double x,
                               final double y,
                               final CommandsCallback commandsCallback) {

        final String defId = clientDefinitionManager.adapters().forDefinition().getId( definition );
        final String uuid = UUID.uuid();

        clientFactoryServices.newElement( uuid, defId, new ServiceCallback<Element>() {
            @Override
            public void onSuccess(final Element element) {

                getElementCommands(element, parent, factory, x, y, new CommandsCallback() {
                    @Override
                    public void onComplete( final String uuid,
                                            final List<Command<AbstractCanvasHandler, CanvasViolation>> commands ) {
                        commandsCallback.onComplete( uuid, commands );
                    }

                    @Override
                    public void onError( final ClientRuntimeError error ) {
                        commandsCallback.onError( error );
                    }

                });;

            }

            @Override
            public void onError(final ClientRuntimeError error) {
                commandsCallback.onError( error );
        };

        } );

    }

    public void getElementCommands(final Element element,
                               final Node<View<?>, Edge> parent,
                               final ShapeFactory factory,
                               final double x,
                               final double y,
                               final CommandsCallback commandsCallback) {

        Command<AbstractCanvasHandler, CanvasViolation> command = null;
        if ( element instanceof Node) {

            if ( null != parent ) {

                command = canvasCommandFactory.ADD_CHILD_NODE( parent, (Node) element, factory );

            } else {

                command = canvasCommandFactory.ADD_NODE((Node) element, factory);

            }

        } else if ( element instanceof Edge && null != parent ) {

            command = canvasCommandFactory.ADD_EDGE( parent, (Edge) element, factory );

        } else {

            throw new RuntimeException("Unrecognized element type for " + element);

        }

        // Execute both add element and move commands in batch, so undo will be done in batch as well.
        Command<AbstractCanvasHandler, CanvasViolation> moveCanvasElementCommand =
                canvasCommandFactory.UPDATE_POSITION(element, x ,y);


        final List<Command<AbstractCanvasHandler, CanvasViolation>> commandList = new LinkedList<Command<AbstractCanvasHandler, CanvasViolation>>();

        commandList.add( command );
        commandList.add( moveCanvasElementCommand );

        commandsCallback.onComplete( element.getUUID(), commandList );

    }

    public Node<View<?>, Edge> getParent(final double _x,
                                         final double _y ) {

        if ( _x > -1 && _y > -1) {

            final String rootUUID = canvasHandler.getDiagram().getSettings().getCanvasRootUUID();
            graphBoundsIndexer.setRootUUID( rootUUID ).build( canvasHandler.getDiagram().getGraph() );
            final Node<View<?>, Edge> r = graphBoundsIndexer.getAt( _x, _y );
            return r;

        }

        return null;
    }

    public Double[] getChildCoordinates( final Node<View<?>, Edge> parent,
                                            final double _x,
                                            final double _y) {

        if ( null != parent) {
            final Double[] parentCoords = GraphUtils.getPosition(parent.getContent());
            final double x = _x - parentCoords[0];
            final double y = _y - parentCoords[1];
            return new Double[] { x, y };
        }

        return new Double[] { _x, _y };
    }

    protected void fireProcessingStarted() {
        // Nothing to for now.
    }

    protected void fireProcessingCompleted() {
        // Nothing to for now.
    }

    protected boolean isValid( final RuleViolations violations ) {
        return !violations.violations(RuleViolation.Type.ERROR).iterator().hasNext();
    }
    
}
