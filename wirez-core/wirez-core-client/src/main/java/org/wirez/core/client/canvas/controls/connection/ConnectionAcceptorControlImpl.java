package org.wirez.core.client.canvas.controls.connection;

import com.ait.lienzo.client.core.shape.wires.*;
import com.google.gwt.logging.client.LogConfiguration;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.client.canvas.command.CanvasViolation;
import org.wirez.core.client.canvas.wires.WiresCanvas;
import org.wirez.core.client.canvas.wires.WiresCanvasHandler;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.view.ShapeView;

import javax.enterprise.context.Dependent;
import java.util.logging.Level;
import java.util.logging.Logger;

@Dependent
public class ConnectionAcceptorControlImpl implements ConnectionAcceptorControl<WiresCanvasHandler> {

    private static Logger LOGGER = Logger.getLogger(ConnectionAcceptorControlImpl.class.getName());
    
    private WiresCanvasHandler canvasHandler;
    
    @Override
    public void enable(final WiresCanvasHandler canvasHandler) {
        this.canvasHandler = canvasHandler;
        final WiresCanvas.View canvasView = (WiresCanvas.View) canvasHandler.getCanvas().getView();
        canvasView.setConnectionAcceptor(CONNECTION_ACCEPTOR);
    }

    @Override
    public void disable() {
        this.canvasHandler = null;
    }

    
    @Override
    public boolean allowSource(final Node source,
                               final Edge<View<?>, Node> connector,
                               final int magnet) {
        if ( null != canvasHandler ) {
            CommandResult<CanvasViolation> violations =
                    canvasHandler.allow( canvasHandler.getCommandFactory().SET_SOURCE_NODE(source, connector, magnet) );
            return isAccept(violations);
        }
        
        return true;
    }

    @Override
    public boolean allowTarget(final Node target,
                               final Edge<View<?>, Node> connector,
                               final int magnet) {

        if ( null != canvasHandler ) {
            CommandResult<CanvasViolation> violations =
                    canvasHandler.allow( canvasHandler.getCommandFactory().SET_TARGET_NODE(target, connector, magnet) );
            return isAccept(violations);    
        }
        
        return true;
    }

    @Override
    public boolean acceptSource(final Node source,
                                final Edge<View<?>, Node> connector,
                                final int magnet) {

        if ( null != canvasHandler ) {
            CommandResult<CanvasViolation> violations =
                    canvasHandler.execute( canvasHandler.getCommandFactory().SET_SOURCE_NODE(source, connector, magnet) );
            return isAccept(violations);    
        }
        
        return false;
    }

    @Override
    public boolean acceptTarget(final Node target,
                                final Edge<View<?>, Node> connector,
                                final int magnet) {

        if ( null != canvasHandler ) {
            CommandResult<CanvasViolation> violations =
                    canvasHandler.execute( canvasHandler.getCommandFactory().SET_TARGET_NODE(target, connector, magnet) );
            return isAccept(violations);    
        }
        
        return false;
    }

    private boolean isAccept(final CommandResult<CanvasViolation> result) {
        return !CommandResult.Type.ERROR.equals( result.getType() );
    }

    private final IConnectionAcceptor CONNECTION_ACCEPTOR = new IConnectionAcceptor() {

        // Set the source Node for the connector.
        @Override
        public boolean acceptHead(WiresConnection head, WiresMagnet magnet) {
            final ShapeView connector = (ShapeView) head.getConnector();
            final Shape sourceShape = (Shape) magnet.getMagnets().getWiresShape();
            final Node sourceNode = canvasHandler.getGraphIndex().getNode(sourceShape.getUUID());
            final Edge edge = canvasHandler.getGraphIndex().getEdge(connector.getUUID());
            final String sourceUUID = sourceNode != null ? sourceNode.getUUID() : null;

            final int mIndex = getMagnetIndex(magnet);

            final String message = "Executed SetConnectionSourceNodeCommand [source=" + sourceUUID + ", magnet=" + mIndex +  "]";
            log(Level.FINE, message);

            return acceptSource(sourceNode, edge, mIndex);
        }

        // Set the target Node for the connector.
        @Override
        public boolean acceptTail(WiresConnection tail, WiresMagnet magnet) {
            WiresConnection head = tail.getConnector().getHeadConnection();
            final ShapeView connector = (ShapeView) head.getConnector();
            final Shape targetShape = (Shape) magnet.getMagnets().getWiresShape();
            final Node targetNode = canvasHandler.getGraphIndex().getNode(targetShape.getUUID());
            final Edge edge = canvasHandler.getGraphIndex().getEdge(connector.getUUID());
            final String targetUUID = targetNode != null ? targetNode.getUUID() : null;

            final int mIndex = getMagnetIndex(magnet);

            final String message = "Executed SetConnectionTargetNodeCommand [target=" + targetUUID + ", magnet=" + mIndex +  "]";
            log(Level.FINE, message);

            return acceptTarget(targetNode, edge, mIndex);
        }

        @Override
        public boolean headConnectionAllowed(WiresConnection head, WiresShape shape) {
            WiresConnection tail = head.getConnector().getTailConnection();

            final ShapeView connector = (ShapeView) head.getConnector();
            final Shape outNode = (Shape) shape;

            final Node sourceNode = canvasHandler.getGraphIndex().getNode(outNode.getUUID());
            final Edge<View<?>, Node> edge = canvasHandler.getGraphIndex().getEdge(connector.getUUID());

            return allowSource(sourceNode, edge, 0);
        }

        @Override
        public boolean tailConnectionAllowed(WiresConnection tail, WiresShape shape) {
            final ShapeView connector = (ShapeView) tail.getConnector();
            final Shape inNode = (Shape) shape;

            final Node targetNode = canvasHandler.getGraphIndex().getNode(inNode.getUUID());
            final Edge<View<?>, Node> edge = canvasHandler.getGraphIndex().getEdge(connector.getUUID());

            return allowTarget(targetNode, edge, 0);
        }

        private int getMagnetIndex(final WiresMagnet magnet) {
            if ( null != magnet ) {
                MagnetManager.Magnets magnets = magnet.getMagnets();
                for (int x = 0; x < magnets.size(); x++) {
                    WiresMagnet _m = magnets.getMagnet(x);
                    if ( _m.equals(magnet) ) {
                        return x;
                    }
                }
            }
            return -1;
        }

    };

    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }
}
