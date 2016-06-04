package org.wirez.core.client.api.platform;

public interface ClientPlatform {
    
    boolean supports( String platform );
    
    String[] getUserAgents();
    
}
