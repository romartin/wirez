package org.wirez.core.client.session.impl;

import com.google.gwt.logging.client.LogConfiguration;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.session.CanvasSession;
import org.wirez.core.client.session.event.SessionDisposedEvent;
import org.wirez.core.client.session.event.SessionOpenedEvent;
import org.wirez.core.client.session.event.SessionPausedEvent;
import org.wirez.core.client.session.event.SessionResumedEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO: Multiple sessions support.
@ApplicationScoped
public class CanvasSessionManagerImpl implements DefaultCanvasSessionManager {
    
    private static Logger LOGGER = Logger.getLogger(CanvasSessionManagerImpl.class.getName());

    Instance<DefaultCanvasReadOnlySession> readOnlySessions;
    Instance<DefaultCanvasFullSession> defaultSessions;
    Event<SessionOpenedEvent> sessionOpenedEvent;
    Event<SessionPausedEvent> sessionPausedEvent;
    Event<SessionResumedEvent> sessionResumedEvent;
    Event<SessionDisposedEvent> sessionDisposedEvent;
    
    private CanvasSession<AbstractCanvas, AbstractCanvasHandler> current;

    protected CanvasSessionManagerImpl() {
    }
    
    @Inject
    public CanvasSessionManagerImpl(final Instance<DefaultCanvasReadOnlySession> readOnlySessions, 
                                    final Instance<DefaultCanvasFullSession> defaultSessions,
                                    final Event<SessionOpenedEvent> sessionOpenedEvent,
                                    final Event<SessionDisposedEvent> sessionDisposedEvent,
                                    final Event<SessionPausedEvent> sessionPausedEvent,
                                    final Event<SessionResumedEvent> sessionResumedEvent) {
        this.readOnlySessions = readOnlySessions;
        this.defaultSessions = defaultSessions;
        this.sessionOpenedEvent = sessionOpenedEvent;
        this.sessionPausedEvent = sessionPausedEvent;
        this.sessionResumedEvent = sessionResumedEvent;
        this.sessionDisposedEvent = sessionDisposedEvent;
    }

    @Override
    public CanvasSession<AbstractCanvas, AbstractCanvasHandler> getCurrentSession() {
        return current;
    }

    @Override
    public void open(final CanvasSession<AbstractCanvas, AbstractCanvasHandler> session) {
        
        if ( null != this.current && !session.equals( this.current ) ) {
            this.dispose();
        }
        
        if ( !session.equals( this.current ) ) {
            this.current = session;
            this.current.onOpen();
            this.sessionOpenedEvent.fire( new SessionOpenedEvent( current ) );
            log( Level.FINE, "Session [" + current.toString() + "] opened");
        }
        
    }

    @Override
    public void pause() {
        // TODO
        
        if ( null != current ) {
            this.sessionPausedEvent.fire( new SessionPausedEvent( current ) );
            log( Level.FINE, "Session [" + current.toString() + "] paused");
        }
        
    }

    @Override
    public void resume(final CanvasSession<AbstractCanvas, AbstractCanvasHandler> session) {
        // TODO
        
        if ( null != current && !current.equals( session )) {
            pause();
        }

        if ( !session.equals( current ) ) {
            this.current = session;
            this.sessionResumedEvent.fire( new SessionResumedEvent( session ) );
            log( Level.FINE, "Session [" + current.toString() + "] resumed");
        }
       
    }

    @Override
    public void dispose() {
        
        if ( null != current ) {
            this.current.onDispose();
            this.sessionDisposedEvent.fire( new SessionDisposedEvent( current ) );
            log( Level.FINE, "Session [" + current.toString() + "] disposed");
            this.current = null;
        }
    }

    @Override
    public DefaultCanvasReadOnlySession newReadOnlySession() {
        return readOnlySessions.get();
    }

    @Override
    public DefaultCanvasFullSession newFullSession() {
        return defaultSessions.get();
    }

    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }
    
}
