package org.wirez.client.lienzo.canvas.controls;

import com.ait.lienzo.client.core.shape.wires.WiresContainer;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.google.gwt.logging.client.LogConfiguration;
import org.wirez.client.lienzo.canvas.wires.WiresCanvas;
import org.wirez.client.lienzo.canvas.wires.WiresUtils;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.command.CanvasViolation;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.controls.CanvasControl;
import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;
import org.wirez.core.command.CommandUtils;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.relationship.Child;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractContainmentBasedControl<H extends CanvasHandler> implements CanvasControl<AbstractCanvasHandler> {

    private static Logger LOGGER = Logger.getLogger(AbstractContainmentBasedControl.class.getName());

    protected CanvasCommandFactory canvasCommandFactory;
    protected CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager;

    protected AbstractCanvasHandler canvasHandler;

    public AbstractContainmentBasedControl(final CanvasCommandFactory canvasCommandFactory,
                                           final CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager) {
        this.canvasCommandFactory = canvasCommandFactory;
        this.canvasCommandManager = canvasCommandManager;
    }
    
    protected abstract void doEnable( WiresCanvas.View view );

    protected abstract void doDisable( WiresCanvas.View view );
    
    protected abstract boolean isEdgeAccepted( Edge edge );
    
    protected abstract Command<AbstractCanvasHandler, CanvasViolation> getAddEdgeCommand( Node parent, Node child );

    protected abstract Command<AbstractCanvasHandler, CanvasViolation> getDeleteEdgeCommand( Node parent, Node child );

    @Override
    public void enable(final AbstractCanvasHandler canvasHandler) {
        this.canvasHandler = canvasHandler;
        final WiresCanvas.View canvasView = (WiresCanvas.View) canvasHandler.getCanvas().getView();
        doEnable( canvasView );
    }

    @Override
    public void disable() {

        if ( null != canvasHandler && null != canvasHandler.getCanvas() ) {
            final WiresCanvas.View canvasView = (WiresCanvas.View) canvasHandler.getCanvas().getView();
            doDisable( canvasView );
        }

        this.canvasHandler = null;
    }

    @SuppressWarnings("unchecked")
    public boolean allow(final Node parent, 
                         final Node child) {

        if ( parent == null && child == null ) {
            return false;
        }

        boolean isAllow = false;
        final Edge dockEdge = getTheEdge(child);
        final boolean isSameParent = isSameParent( parent, dockEdge );
        if ( isSameParent ) {
            log(Level.FINE, "Is same parent. isAllow=true");
            isAllow = true;
        } else {
            final Command<AbstractCanvasHandler, CanvasViolation> command = getAddEdgeCommand( parent, child );
            CommandResult<CanvasViolation> violations = canvasCommandManager.allow( canvasHandler, command );
            isAllow = isAccept(violations);
            log(Level.FINE, "isAllow=" + isAllow);
        }

        return isAllow;
        
        
    }

    @SuppressWarnings("unchecked")
    public boolean accept(final Node parent, 
                          final Node child) {

        if ( parent == null && child == null ) {
            return false;
        }

        final Edge dockEdge = getTheEdge(child);
        final boolean isSameParent = isSameParent( parent, dockEdge );

        boolean isAccept = true;
        if ( !isSameParent ) {

            // Remove current relationship.
            if ( null != dockEdge ) {
                // TODO: Check command results
                CommandResult<CanvasViolation> result = canvasCommandManager.execute( canvasHandler, getDeleteEdgeCommand( dockEdge.getSourceNode() , child ) );
            }

            // Add a new relationship.
            final Command<AbstractCanvasHandler, CanvasViolation> command = getAddEdgeCommand(parent,child);
            final CommandResult<CanvasViolation> violations = canvasCommandManager.execute( canvasHandler, command );
            isAccept = isAccept(violations);

        }

        log(Level.FINE, "isAccept=" + isAccept);
        return isAccept;
        
    }

    protected boolean isEnabled() {
        return canvasHandler != null;
    }

    protected boolean isSameParent(final Node parent,
                                 final Edge<Child, Node> edge) {

        if ( null != edge ) {


            final Node sourceNode = edge.getSourceNode();

            if ( null != sourceNode ) {

                final String parentUUID = null != parent ? parent.getUUID() : canvasHandler.getDiagram().getSettings().getCanvasRootUUID();
                return null != parentUUID && sourceNode.getUUID().equals( parentUUID );

            }

        }

        return parent == null;
    }

    @SuppressWarnings("unchecked")
    protected Edge<Object, Node> getTheEdge(final Node child) {
        if ( child != null) {
            final List<Edge> outEdges = child.getInEdges();
            if ( null != outEdges && !outEdges.isEmpty() ) {
                for ( final Edge edge : outEdges ) {
                    
                    if ( isEdgeAccepted( edge ) ) {
                        return edge;
                    }
                    
                }
            }
        }
        
        return null;
    }

    protected boolean isAccept( final WiresContainer wiresContainer,
                                  final WiresShape wiresShape ) {

        if ( !isEnabled() || !isWirezShape( wiresContainer ) || !isWirezShape( wiresShape ) ) {
            return false;
        }

        return true;
    }

    protected boolean isWirezShape( final WiresContainer wiresShape ) {
        return null != wiresShape.getContainer().getUserData() &&
                wiresShape.getContainer().getUserData().equals( WiresCanvas.WIRES_CANVAS_GROUP_ID );
    }

    protected boolean isAccept(final CommandResult<CanvasViolation> result) {
        return !CommandUtils.isError( result );
    }

    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }
    
}
