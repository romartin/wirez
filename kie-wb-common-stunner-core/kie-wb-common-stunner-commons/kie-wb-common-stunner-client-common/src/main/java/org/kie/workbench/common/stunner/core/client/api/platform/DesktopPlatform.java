package org.kie.workbench.common.stunner.core.client.api.platform;

import org.kie.workbench.common.stunner.core.client.session.impl.*;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class DesktopPlatform implements ClientPlatform, DefaultCanvasSessionProducer {
    
    private static final String[] USER_AGENTS = {""};

    Instance<CanvasReadOnlySessionImpl> readOnlySessions;
    Instance<CanvasFullSessionImpl> fullSessions;

    @Inject
    public DesktopPlatform( final Instance<CanvasReadOnlySessionImpl> readOnlySessions, 
                            final Instance<CanvasFullSessionImpl> fullSessions ) {
        this.readOnlySessions = readOnlySessions;
        this.fullSessions = fullSessions;
    }

    @Override
    public boolean supports( final String platform ) {
        return null != platform && !isMobilePlatform( platform );
    }

    public static boolean isMobilePlatform( final String platform ) {
        return platform.contains( "arm" ) || platform.contains("iOS");
    }

    @Override
    public String[] getUserAgents() {
        return USER_AGENTS;
    }

    @Override
    public DefaultCanvasReadOnlySession newReadOnlySession() {
        return readOnlySessions.get();
    }

    @Override
    public DefaultCanvasFullSession newFullSession() {
        return fullSessions.get();
    }
    
}
