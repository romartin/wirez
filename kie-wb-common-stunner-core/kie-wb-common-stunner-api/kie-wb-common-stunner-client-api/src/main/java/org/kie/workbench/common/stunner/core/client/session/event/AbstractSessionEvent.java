package org.kie.workbench.common.stunner.core.client.session.event;

import org.uberfire.workbench.events.UberFireEvent;
import org.kie.workbench.common.stunner.core.client.session.CanvasSession;

public abstract class AbstractSessionEvent implements UberFireEvent {
    
    protected final CanvasSession session;

    public AbstractSessionEvent(final CanvasSession session) {
        this.session = session;
    }

    public CanvasSession getSession() {
        return session;
    }
    
}
