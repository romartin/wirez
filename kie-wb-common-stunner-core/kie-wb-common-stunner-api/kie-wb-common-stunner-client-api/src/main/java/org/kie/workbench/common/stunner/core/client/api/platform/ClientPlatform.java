package org.kie.workbench.common.stunner.core.client.api.platform;

public interface ClientPlatform {
    
    boolean supports( String platform );
    
    String[] getUserAgents();
    
}
