package org.wirez.core.client.session.command;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.batch.BatchCommandManager;
import org.wirez.core.api.command.delegate.BatchDelegateCommandManager;
import org.wirez.core.api.command.stack.StackCommandManager;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.command.CanvasViolation;
import org.wirez.core.client.session.CanvasFullSession;
import org.wirez.core.client.session.CanvasSession;
import org.wirez.core.client.session.CanvasSessionManager;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Stack;

@Dependent
@Session
public class SessionCommandManagerImpl extends BatchDelegateCommandManager<AbstractCanvasHandler, CanvasViolation> 
        implements SessionCommandManager<AbstractCanvasHandler> {

    CanvasSessionManager<AbstractCanvas, AbstractCanvasHandler> canvasSessionManager;
    CanvasCommandManager<AbstractCanvasHandler> commandManager;

    @Inject
    public SessionCommandManagerImpl(final CanvasSessionManager<AbstractCanvas, AbstractCanvasHandler> canvasSessionManager,
                                     final CanvasCommandManager<AbstractCanvasHandler> commandManager) {
        this.canvasSessionManager = canvasSessionManager;
        this.commandManager = commandManager;
    }

    @Override
    protected BatchCommandManager<AbstractCanvasHandler, CanvasViolation> getBatchDelegate() {
        final CanvasFullSession<AbstractCanvas, AbstractCanvasHandler> defaultSession = getDefaultSession();
        if ( null != defaultSession ) {
            return defaultSession.getCanvasCommandManager();
        }
        
        return null;
    }

    private CanvasFullSession<AbstractCanvas, AbstractCanvasHandler> getDefaultSession() {
        final CanvasSession<AbstractCanvas, AbstractCanvasHandler> session = getCurrentSession();
        if ( session instanceof CanvasFullSession) {
            return (CanvasFullSession<AbstractCanvas, AbstractCanvasHandler>) session;
        }
        return null;
    }
    
    private CanvasSession<AbstractCanvas, AbstractCanvasHandler> getCurrentSession() {
        return canvasSessionManager.getCurrentSession();
    }

    @Override
    public Stack<Stack<Command<AbstractCanvasHandler, CanvasViolation>>> getHistory() {
        StackCommandManager<AbstractCanvasHandler, CanvasViolation> scm = (StackCommandManager<AbstractCanvasHandler, CanvasViolation>) getBatchDelegate();

        if ( null != scm ) {

            return scm.getHistory();
            
        }
        
        return new Stack<>();
    }
    
}
