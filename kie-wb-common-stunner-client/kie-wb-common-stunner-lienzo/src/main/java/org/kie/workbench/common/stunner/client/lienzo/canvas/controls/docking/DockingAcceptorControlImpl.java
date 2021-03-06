package org.kie.workbench.common.stunner.client.lienzo.canvas.controls.docking;

import com.ait.lienzo.client.core.shape.wires.IDockingAcceptor;
import com.ait.lienzo.client.core.shape.wires.WiresContainer;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import org.kie.workbench.common.stunner.client.lienzo.canvas.controls.AbstractContainmentBasedControl;
import org.kie.workbench.common.stunner.client.lienzo.canvas.wires.WiresCanvas;
import org.kie.workbench.common.stunner.client.lienzo.canvas.wires.WiresUtils;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.command.CanvasCommandManager;
import org.kie.workbench.common.stunner.core.client.command.CanvasViolation;
import org.kie.workbench.common.stunner.core.client.command.factory.CanvasCommandFactory;
import org.kie.workbench.common.stunner.core.client.canvas.controls.docking.DockingAcceptorControl;
import org.kie.workbench.common.stunner.core.client.command.Session;
import org.kie.workbench.common.stunner.core.command.Command;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.relationship.Dock;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DockingAcceptorControlImpl extends AbstractContainmentBasedControl<AbstractCanvasHandler>
    implements DockingAcceptorControl<AbstractCanvasHandler> {

    private static final int HOTSPOT = 10;
    private CanvasCommandFactory canvasCommandFactory;

    @Inject
    public DockingAcceptorControlImpl( final CanvasCommandFactory canvasCommandFactory,
                                       final @Session CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager ) {
        super( canvasCommandManager );
        this.canvasCommandFactory = canvasCommandFactory;
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

            final Node childNode = WiresUtils.getNode( getCanvasHandler(), wiresShape );
            final Node parentNode = WiresUtils.getNode( getCanvasHandler(), wiresContainer );

            return allow( parentNode, childNode );
        }

        @Override
        public boolean acceptDocking( final WiresContainer wiresContainer,
                                      final WiresShape wiresShape ) {

            if ( !isAccept(  wiresContainer, wiresShape ) ) {
                return false;
            }

            final Node childNode = WiresUtils.getNode( getCanvasHandler(), wiresShape );
            final Node parentNode = WiresUtils.getNode( getCanvasHandler(), wiresContainer );

            return accept( parentNode, childNode );
        }

        @Override
        public int getHotspotSize() {
            return HOTSPOT;
        }
    };

}
