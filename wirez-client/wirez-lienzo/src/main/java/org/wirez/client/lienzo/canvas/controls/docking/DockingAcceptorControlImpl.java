package org.wirez.client.lienzo.canvas.controls.docking;

import com.ait.lienzo.client.core.shape.wires.IDockingAcceptor;
import com.ait.lienzo.client.core.shape.wires.WiresContainer;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.google.gwt.logging.client.LogConfiguration;
import org.wirez.client.lienzo.canvas.wires.WiresCanvas;
import org.wirez.client.lienzo.canvas.wires.WiresUtils;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.relationship.Child;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.controls.docking.DockingAcceptorControl;
import org.wirez.core.client.session.command.Session;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

@Dependent
public class DockingAcceptorControlImpl implements DockingAcceptorControl<AbstractCanvasHandler> {

    private static Logger LOGGER = Logger.getLogger(DockingAcceptorControlImpl.class.getName());

    CanvasCommandFactory canvasCommandFactory;
    CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager;
    
    private AbstractCanvasHandler canvasHandler;

    @Inject
    public DockingAcceptorControlImpl(final CanvasCommandFactory canvasCommandFactory,
                                      final @Session  CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager) {
        this.canvasCommandFactory = canvasCommandFactory;
        this.canvasCommandManager = canvasCommandManager;
    }

    @Override
    public void enable(final AbstractCanvasHandler canvasHandler) {
        this.canvasHandler = canvasHandler;
        final WiresCanvas.View canvasView = (WiresCanvas.View) canvasHandler.getCanvas().getView();
        canvasView.setDockingAcceptor(DOCKING_ACCEPTOR);
    }

    @Override
    public void disable() {
        this.canvasHandler = null;
    }

    @Override
    public boolean allow(final Node parent, 
                         final Node child) {

        if ( parent == null && child == null ) {
            return false;
        }
        
        // TODO
        
        return true;
        
        
    }

    @Override
    public boolean accept(final Node parent, 
                          final Node child) {

        if ( parent == null && child == null ) {
            return false;
        }
        
        // TODO
        
        return true;
        
        
    }

    private boolean isEnabled() {
        return canvasHandler != null;
    }
    
    private final IDockingAcceptor DOCKING_ACCEPTOR = new IDockingAcceptor() {
        @Override
        public boolean dockingAllowed(final WiresContainer wiresContainer, 
                                      final WiresShape wiresShape) {

            if ( !isEnabled() ) {
                return false;
            }
            
            final Node childNode = WiresUtils.getNode( canvasHandler, wiresShape );
            final Node parentNode = WiresUtils.getNode( canvasHandler, wiresContainer );
          
            return allow( parentNode, childNode );
        }

        @Override
        public boolean acceptDocking(final WiresContainer wiresContainer, 
                                     final WiresShape wiresShape) {

            if ( !isEnabled() ) {
                return false;
            }

            final Node childNode = WiresUtils.getNode( canvasHandler, wiresShape );
            final Node parentNode = WiresUtils.getNode( canvasHandler, wiresContainer );
            
            return accept( parentNode, childNode );
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

    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }

    
}
