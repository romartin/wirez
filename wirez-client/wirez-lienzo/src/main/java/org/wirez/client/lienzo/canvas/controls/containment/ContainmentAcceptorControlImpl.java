package org.wirez.client.lienzo.canvas.controls.containment;

import com.ait.lienzo.client.core.shape.wires.IContainmentAcceptor;
import com.ait.lienzo.client.core.shape.wires.WiresContainer;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import org.wirez.client.lienzo.canvas.controls.AbstractContainmentBasedControl;
import org.wirez.client.lienzo.canvas.wires.WiresCanvas;
import org.wirez.client.lienzo.canvas.wires.WiresUtils;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.command.CanvasCommandManager;
import org.wirez.core.client.command.CanvasViolation;
import org.wirez.core.client.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.controls.containment.ContainmentAcceptorControl;
import org.wirez.core.client.command.Session;
import org.wirez.core.client.command.SessionCommandManager;
import org.wirez.core.command.Command;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.relationship.Child;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class ContainmentAcceptorControlImpl extends AbstractContainmentBasedControl<AbstractCanvasHandler> 
        implements ContainmentAcceptorControl<AbstractCanvasHandler> {

    @Inject
    public ContainmentAcceptorControlImpl(final CanvasCommandFactory canvasCommandFactory,
                                           final @Session CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager) {
        super( canvasCommandFactory, canvasCommandManager );
    }

    @Override
    protected void doEnable(final WiresCanvas.View view) {
        view.setContainmentAcceptor(CONTAINMENT_ACCEPTOR);
    }

    @Override
    protected void doDisable(final WiresCanvas.View view) {
        view.setContainmentAcceptor(IContainmentAcceptor.NONE);
    }

    @Override
    protected boolean isEdgeAccepted(final Edge edge) {
        return edge.getContent() instanceof Child;
    }

    @Override
    protected Command<AbstractCanvasHandler, CanvasViolation> getAddEdgeCommand(final Node parent, final Node child) {
        return canvasCommandFactory.ADD_CHILD_EDGE( parent,child );
    }

    @Override
    protected Command<AbstractCanvasHandler, CanvasViolation> getDeleteEdgeCommand(final Node parent, final Node child) {
        return canvasCommandFactory.DELETE_CHILD_EDGE( parent, child );
    }

    private final IContainmentAcceptor CONTAINMENT_ACCEPTOR = new IContainmentAcceptor() {
        @Override
        public boolean containmentAllowed(final WiresContainer wiresContainer,
                                          final WiresShape wiresShape) {

            if ( !isAccept(  wiresContainer, wiresShape ) ) {
                return false;
            }
            
            final Node childNode = WiresUtils.getNode( canvasHandler, wiresShape );
            final Node parentNode = WiresUtils.getNode( canvasHandler, wiresContainer );

            return allow(parentNode, childNode);
        }

        @Override
        public boolean acceptContainment(final WiresContainer wiresContainer,
                                         final WiresShape wiresShape) {

            if ( !isAccept(  wiresContainer, wiresShape ) ) {
                return false;
            }
            
            final Node childNode = WiresUtils.getNode( canvasHandler, wiresShape );
            final Node parentNode = WiresUtils.getNode( canvasHandler, wiresContainer );

            return accept(parentNode, childNode);
        }

    };

}
