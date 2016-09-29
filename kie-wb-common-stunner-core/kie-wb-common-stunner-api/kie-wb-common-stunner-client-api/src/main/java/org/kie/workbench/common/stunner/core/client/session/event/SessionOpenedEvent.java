package org.kie.workbench.common.stunner.core.client.session.event;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.kie.workbench.common.stunner.core.client.session.CanvasSession;

@NonPortable
public class SessionOpenedEvent extends AbstractSessionEvent {
    
    public SessionOpenedEvent(final CanvasSession session) {
        super(session);
    }
}
