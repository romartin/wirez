package org.wirez.client.lienzo.canvas.controls.docking;

import com.ait.lienzo.client.core.shape.wires.IDockingAcceptor;
import com.ait.lienzo.client.core.shape.wires.WiresContainer;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import org.wirez.client.lienzo.canvas.controls.AbstractContainmentBasedControl;
import org.wirez.client.lienzo.canvas.wires.WiresCanvas;
import org.wirez.client.lienzo.canvas.wires.WiresUtils;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.command.CanvasCommandManager;
import org.wirez.core.client.command.CanvasViolation;
import org.wirez.core.client.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.controls.docking.DockingAcceptorControl;
import org.wirez.core.client.command.Session;
import org.wirez.core.client.command.SessionCommandManager;
import org.wirez.core.command.Command;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.relationship.Dock;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DockingAcceptorControlImpl extends AbstractContainmentBasedControl<AbstractCanvasHandler>
    implements DockingAcceptorControl<AbstractCanvasHandler> {

    @Inject
    public DockingAcceptorControlImpl( final CanvasCommandFactory canvasCommandFactory,
                                       final @Session CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager ) {
        super( canvasCommandFactory, canvasCommandManager );
    }

    @Override
    protected void doEnable( final WiresCanvas.View view ) {
        view.setDockingAcceptor( DOCKING_ACCEPTOR );
    }

    @Override
    protected void doDisable( final WiresCanvas.View view ) {
        view.setDockingAcceptor( IDockingAcceptor.NONE );
    }

    @Override
    protected boolean isEdgeAccepted( final Edge edge ) {
        return edge.getContent() instanceof Dock;
    }

    @Override
    protected Command<AbstractCanvasHandler, CanvasViolation> getAddEdgeCommand( final Node parent, final Node child ) {
        return canvasCommandFactory.ADD_DOCK_EDGE( parent, child );
    }

    @Override
    protected Command<AbstractCanvasHandler, CanvasViolation> getDeleteEdgeCommand( final Node parent, final Node child ) {
        return canvasCommandFactory.DELETE_DOCK_EDGE( parent, child );
    }

    private final IDockingAcceptor DOCKING_ACCEPTOR = new IDockingAcceptor() {
        @Override
        public boolean dockingAllowed( final WiresContainer wiresContainer,
                                       final WiresShape wiresShape ) {

            if ( !isAccept(  wiresContainer, wiresShape ) ) {
                return false;
            }

            final Node childNode = WiresUtils.getNode( canvasHandler, wiresShape );
            final Node parentNode = WiresUtils.getNode( canvasHandler, wiresContainer );

            return allow( parentNode, childNode );
        }

        @Override
        public boolean acceptDocking( final WiresContainer wiresContainer,
                                      final WiresShape wiresShape ) {

            if ( !isAccept(  wiresContainer, wiresShape ) ) {
                return false;
            }

            final Node childNode = WiresUtils.getNode( canvasHandler, wiresShape );
            final Node parentNode = WiresUtils.getNode( canvasHandler, wiresContainer );

            return accept( parentNode, childNode );
        }

        @Override
        public int getHotspotSize() {
            return IDockingAcceptor.HOTSPOT_SIZE;
        }
    };

}
