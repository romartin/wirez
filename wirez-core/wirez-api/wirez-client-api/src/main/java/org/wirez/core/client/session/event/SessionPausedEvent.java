package org.wirez.core.client.session.event;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.core.client.session.CanvasSession;

@NonPortable
public class SessionPausedEvent extends AbstractSessionEvent {
    
    public SessionPausedEvent(final CanvasSession session) {
        super(session);
    }
}
