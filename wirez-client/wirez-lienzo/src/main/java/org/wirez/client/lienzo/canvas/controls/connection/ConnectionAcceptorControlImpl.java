package org.wirez.client.lienzo.canvas.controls.connection;

import com.ait.lienzo.client.core.shape.wires.*;
import com.google.gwt.logging.client.LogConfiguration;
import org.wirez.client.lienzo.canvas.wires.WiresCanvas;
import org.wirez.client.lienzo.canvas.wires.WiresUtils;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.command.CanvasCommandManager;
import org.wirez.core.client.command.CanvasViolation;
import org.wirez.core.client.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.controls.connection.ConnectionAcceptorControl;
import org.wirez.core.client.command.Session;
import org.wirez.core.client.command.SessionCommandManager;
import org.wirez.core.command.CommandResult;
import org.wirez.core.command.CommandUtils;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

@Dependent
public class ConnectionAcceptorControlImpl implements ConnectionAcceptorControl<AbstractCanvasHandler> {

    private static Logger LOGGER = Logger.getLogger( ConnectionAcceptorControlImpl.class.getName() );

    CanvasCommandFactory canvasCommandFactory;
    CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager;

    private AbstractCanvasHandler canvasHandler;

    @Inject
    public ConnectionAcceptorControlImpl( final CanvasCommandFactory canvasCommandFactory,
                                          final @Session CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager ) {
        this.canvasCommandFactory = canvasCommandFactory;
        this.canvasCommandManager = canvasCommandManager;
    }

    @Override
    public void enable( final AbstractCanvasHandler canvasHandler ) {
        this.canvasHandler = canvasHandler;
        final WiresCanvas.View canvasView = ( WiresCanvas.View ) canvasHandler.getCanvas().getView();
        canvasView.setConnectionAcceptor( CONNECTION_ACCEPTOR );
    }

    @Override
    public void disable() {

        if ( null != canvasHandler && null != canvasHandler.getCanvas() ) {
            final WiresCanvas.View canvasView = ( WiresCanvas.View ) canvasHandler.getCanvas().getView();
            canvasView.setConnectionAcceptor( CONNECTION_EMPTY_ACCEPTOR );
        }

        this.canvasHandler = null;
    }


    @Override
    @SuppressWarnings( "unchecked" )
    public boolean allowSource( final Node source,
                                final Edge<View<?>, Node> connector,
                                final int magnet ) {
        if ( null != canvasHandler && null != source ) {
            CommandResult<CanvasViolation> violations =
                    canvasCommandManager.allow( canvasHandler, canvasCommandFactory.SET_SOURCE_NODE( source, connector, magnet ) );
            return isAccept( violations );
        }

        return false;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public boolean allowTarget( final Node target,
                                final Edge<View<?>, Node> connector,
                                final int magnet ) {

        if ( null != canvasHandler && null != target ) {
            CommandResult<CanvasViolation> violations =
                    canvasCommandManager.allow( canvasHandler, canvasCommandFactory.SET_TARGET_NODE( target, connector, magnet ) );
            return isAccept( violations );
        }

        return false;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public boolean acceptSource( final Node source,
                                 final Edge<View<?>, Node> connector,
                                 final int magnet ) {

        if ( null != canvasHandler ) {
            CommandResult<CanvasViolation> violations =
                    canvasCommandManager.execute( canvasHandler, canvasCommandFactory.SET_SOURCE_NODE( source, connector, magnet ) );
            return isAccept( violations );
        }

        return false;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public boolean acceptTarget( final Node target,
                                 final Edge<View<?>, Node> connector,
                                 final int magnet ) {

        if ( null != canvasHandler ) {
            CommandResult<CanvasViolation> violations =
                    canvasCommandManager.execute( canvasHandler, canvasCommandFactory.SET_TARGET_NODE( target, connector, magnet ) );
            return isAccept( violations );
        }

        return false;
    }

    private final IConnectionAcceptor CONNECTION_EMPTY_ACCEPTOR = new IConnectionAcceptor() {

        @Override
        public boolean acceptHead( final WiresConnection wiresConnection,
                                   final WiresMagnet wiresMagnet ) {
            return false;
        }

        @Override
        public boolean acceptTail( final WiresConnection wiresConnection,
                                   final WiresMagnet wiresMagnet ) {
            return false;
        }

        @Override
        public boolean headConnectionAllowed( final WiresConnection wiresConnection,
                                              final WiresShape wiresShape ) {
            return false;
        }

        @Override
        public boolean tailConnectionAllowed( final WiresConnection wiresConnection,
                                              final WiresShape wiresShape ) {
            return false;
        }

    };

    private final IConnectionAcceptor CONNECTION_ACCEPTOR = new IConnectionAcceptor() {

        // Set the source Node for the connector.
        @Override
        @SuppressWarnings( "unchecked" )
        public boolean acceptHead( final WiresConnection head,
                                   final WiresMagnet magnet ) {

            log( Level.FINE, "## Accept Head ##" );
            final Edge edge = WiresUtils.getEdge( canvasHandler, head.getConnector() );
            final Node sourceNode = WiresUtils.getNode( canvasHandler, magnet );
            final int mIndex = getMagnetIndex( magnet );

            final String sourceUUID = sourceNode != null ? sourceNode.getUUID() : null;
            final String message = "Executed SetConnectionSourceNodeCommand [source=" + sourceUUID + ", magnet=" + mIndex + "]";
            log( Level.FINE, message );

            return acceptSource( sourceNode, edge, mIndex );
        }

        // Set the target Node for the connector.
        @Override
        @SuppressWarnings( "unchecked" )
        public boolean acceptTail( final WiresConnection tail,
                                   final WiresMagnet magnet ) {
            log( Level.FINE, "## Accept tail ##" );
            final WiresConnection head = tail.getConnector().getHeadConnection();

            final Edge edge = WiresUtils.getEdge( canvasHandler, head.getConnector() );
            final Node targetNode = WiresUtils.getNode( canvasHandler, magnet );
            final int mIndex = getMagnetIndex( magnet );

            final String targetUUID = targetNode != null ? targetNode.getUUID() : null;
            final String message = "Executed SetConnectionTargetNodeCommand [target=" + targetUUID + ", magnet=" + mIndex + "]";
            log( Level.FINE, message );

            return acceptTarget( targetNode, edge, mIndex );
        }

        @Override
        @SuppressWarnings( "unchecked" )
        public boolean headConnectionAllowed( final WiresConnection head,
                                              final WiresShape shape ) {
            log( Level.FINE, "## Allow Head ##" );
            final Edge<View<?>, Node> edge = WiresUtils.getEdge( canvasHandler, head.getConnector() );
            final Node sourceNode = WiresUtils.getNode( canvasHandler, shape );

            final boolean b = allowSource( sourceNode, edge, 0 );
            final String nUUID = null != sourceNode ? sourceNode.getUUID() : "null";
            log( Level.FINE, "  Is head allowed [" + nUUID + "] = " + b );
            return b;
        }

        @Override
        @SuppressWarnings( "unchecked" )
        public boolean tailConnectionAllowed( final WiresConnection tail,
                                              final WiresShape shape ) {
            log( Level.FINE, "## Allow tail ##" );
            final Edge<View<?>, Node> edge = WiresUtils.getEdge( canvasHandler, tail.getConnector() );
            final Node targetNode = WiresUtils.getNode( canvasHandler, shape );

            final boolean b = allowTarget( targetNode, edge, 0 );
            final String nUUID = null != targetNode ? targetNode.getUUID() : "null";
            log( Level.FINE, "  Is tail allowed [" + nUUID + "] = " + b );
            return b;
        }

        private int getMagnetIndex( final WiresMagnet magnet ) {
            if ( null != magnet ) {
                MagnetManager.Magnets magnets = magnet.getMagnets();
                for ( int x = 0; x < magnets.size(); x++ ) {
                    WiresMagnet _m = magnets.getMagnet( x );
                    if ( _m.equals( magnet ) ) {
                        return x;
                    }
                }
            }
            return -1;
        }

    };

    private boolean isAccept( final CommandResult<CanvasViolation> result ) {
        return !CommandUtils.isError( result );
    }

    private void log( final Level level, final String message ) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log( level, message );
        }
    }
}
