package org.wirez.client.lienzo.canvas.controls.containment;

import com.ait.lienzo.client.core.shape.wires.IContainmentAcceptor;
import com.ait.lienzo.client.core.shape.wires.WiresContainer;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.google.gwt.logging.client.LogConfiguration;
import org.wirez.client.lienzo.canvas.wires.WiresCanvas;
import org.wirez.client.lienzo.canvas.wires.WiresUtils;
import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;
import org.wirez.core.command.CommandUtils;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.relationship.Child;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.command.CanvasViolation;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.controls.containment.ContainmentAcceptorControl;
import org.wirez.core.client.session.command.Session;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Dependent
public class ContainmentAcceptorControlImpl implements ContainmentAcceptorControl<AbstractCanvasHandler> {

    private static Logger LOGGER = Logger.getLogger(ContainmentAcceptorControlImpl.class.getName());

    CanvasCommandFactory canvasCommandFactory;
    CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager;
    
    private AbstractCanvasHandler canvasHandler;

    @Inject
    public ContainmentAcceptorControlImpl(final CanvasCommandFactory canvasCommandFactory, 
                                          final @Session  CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager) {
        this.canvasCommandFactory = canvasCommandFactory;
        this.canvasCommandManager = canvasCommandManager;
    }

    @Override
    public void enable(final AbstractCanvasHandler canvasHandler) {
        this.canvasHandler = canvasHandler;
        final WiresCanvas.View canvasView = (WiresCanvas.View) canvasHandler.getCanvas().getView();
        canvasView.setContainmentAcceptor(CONTAINMENT_ACCEPTOR);
    }

    @Override
    public void disable() {
        
        if ( null != canvasHandler && null != canvasHandler.getCanvas() ) {
            final WiresCanvas.View canvasView = (WiresCanvas.View) canvasHandler.getCanvas().getView();
            canvasView.setContainmentAcceptor(CONTAINMENT_EMPTY_ACCEPTOR);
        }

        this.canvasHandler = null;

    }

    @Override
    public boolean allow(final Node parent, 
                         final Node child) {

        if ( parent == null && child == null ) {
            return false;
        }
        
        boolean isAllow = false;
        final Edge childEdge = getChildEdge(child);
        final boolean isSameParent = isSameParent(parent, childEdge);
        if ( isSameParent ) {
            log(Level.FINE, "Is same parent. isAllow=true");
            isAllow = true;
        } else {
            final Command<AbstractCanvasHandler, CanvasViolation> command = canvasCommandFactory.ADD_CHILD_EDGE(parent,child);
            CommandResult<CanvasViolation> violations = canvasCommandManager.allow( canvasHandler, command );
            isAllow = isAccept(violations);
            log(Level.FINE, "isAllow=" + isAllow);
        }

        return isAllow;
        
        
    }

    @Override
    public boolean accept(final Node parent, 
                          final Node child) {

        if ( parent == null && child == null ) {
            return false;
        }
        
        final Edge childEdge = getChildEdge(child);
        final boolean isSameParent = isSameParent(parent, childEdge);

        boolean isAccept = true;
        if ( !isSameParent ) {

            // Remove current child relationship.
            if ( null != childEdge ) {
                // TODO: Check command results
                CommandResult<CanvasViolation> result = canvasCommandManager.execute( canvasHandler, canvasCommandFactory.DELETE_CHILD_EDGE( childEdge.getSourceNode() , child ) );
            }

            // Add a new child relationship.
            final Command<AbstractCanvasHandler, CanvasViolation> command = canvasCommandFactory.ADD_CHILD_EDGE(parent,child);
            final CommandResult<CanvasViolation> violations = canvasCommandManager.execute( canvasHandler, command );
            isAccept = isAccept(violations);

        }

        log(Level.FINE, "isAccept=" + isAccept);
        return isAccept;
        
        
    }

    private final IContainmentAcceptor CONTAINMENT_EMPTY_ACCEPTOR = new IContainmentAcceptor() {

        @Override
        public boolean containmentAllowed( final WiresContainer wiresContainer, 
                                           final WiresShape wiresShape ) {
            return false;
        }

        @Override
        public boolean acceptContainment( final WiresContainer wiresContainer, 
                                          final WiresShape wiresShape ) {
            return false;
        }
        
    };
    private final IContainmentAcceptor CONTAINMENT_ACCEPTOR = new IContainmentAcceptor() {
        @Override
        public boolean containmentAllowed(final WiresContainer wiresContainer, 
                                          final WiresShape wiresShape) {

            final Node childNode = WiresUtils.getNode( canvasHandler, wiresShape );
            final Node parentNode = WiresUtils.getNode( canvasHandler, wiresContainer );
           
            return allow(parentNode, childNode);
        }

        @Override
        public boolean acceptContainment(final WiresContainer wiresContainer, 
                                         final WiresShape wiresShape) {

            final Node childNode = WiresUtils.getNode( canvasHandler, wiresShape );
            final Node parentNode = WiresUtils.getNode( canvasHandler, wiresContainer );
            
            return accept(parentNode, childNode);
        }

    };


    private boolean isSameParent(final Node parent,
                                 final Edge<Child, Node> edge) {

        if ( null != edge ) {
            final Node sourceNode = edge.getSourceNode();
            if ( sourceNode != null && sourceNode.getUUID().equals(parent.getUUID())) {
                return true;
            }
        }

        return parent == null;
    }

    private Edge<Child, Node> getChildEdge(final Node child) {
        if ( child != null) {
            final List<Edge> outEdges = child.getInEdges();
            if ( null != outEdges && !outEdges.isEmpty() ) {
                for ( final Edge edge : outEdges ) {
                    if ( edge.getContent() instanceof Child ) {
                        return edge;
                    }
                }
            }
        }
        return null;
    }

    private boolean isAccept(final CommandResult<CanvasViolation> result) {
        return !CommandUtils.isError( result );
    }

    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }

    
}
