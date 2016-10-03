package org.kie.workbench.common.stunner.core.client.command;

import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvas;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.session.CanvasFullSession;
import org.kie.workbench.common.stunner.core.client.session.CanvasSession;
import org.kie.workbench.common.stunner.core.client.session.CanvasSessionManager;
import org.kie.workbench.common.stunner.core.command.Command;
import org.kie.workbench.common.stunner.core.command.CommandResult;
import org.kie.workbench.common.stunner.core.command.batch.BatchCommandManager;
import org.kie.workbench.common.stunner.core.command.delegate.BatchDelegateCommandManager;
import org.kie.workbench.common.stunner.core.command.stack.StackCommandManager;
import org.kie.workbench.common.stunner.core.registry.command.CommandRegistry;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;

@ApplicationScoped
@Session
public class SessionCommandManagerImpl extends BatchDelegateCommandManager<AbstractCanvasHandler, CanvasViolation>
        implements SessionCommandManager<AbstractCanvasHandler> {

    private final CanvasSessionManager<AbstractCanvas, AbstractCanvasHandler> canvasSessionManager;

    protected SessionCommandManagerImpl() {
        this( null );
    }

    @Inject
    public SessionCommandManagerImpl(final CanvasSessionManager<AbstractCanvas, AbstractCanvasHandler> canvasSessionManager ) {
        this.canvasSessionManager = canvasSessionManager;
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
    public CommandRegistry<Command<AbstractCanvasHandler, CanvasViolation>> getRegistry() {
        final StackCommandManager<AbstractCanvasHandler, CanvasViolation> scm = (StackCommandManager<AbstractCanvasHandler, CanvasViolation>) getBatchDelegate();

        if ( null != scm ) {

            return scm.getRegistry();

        }

        return null;
    }

    @Override
    public CommandResult<CanvasViolation> undo( final AbstractCanvasHandler context ) {

        final StackCommandManager<AbstractCanvasHandler, CanvasViolation> scm = (StackCommandManager<AbstractCanvasHandler, CanvasViolation>) getBatchDelegate();

        if ( null != scm ) {

            return scm.undo( context ) ;

        }

        return null;

    }

    @Override
    public Collection<Command<AbstractCanvasHandler, CanvasViolation>> getBatchCommands() {
        final StackCommandManager<AbstractCanvasHandler, CanvasViolation> scm = (StackCommandManager<AbstractCanvasHandler, CanvasViolation>) getBatchDelegate();

        if ( null != scm ) {

            return scm.getBatchCommands();

        }

        return null;
    }
}