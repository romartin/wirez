package org.kie.workbench.common.stunner.core.client.api.platform;

public interface PlatformManager {
    
    Iterable<ClientPlatform> getSupportedPlatforms();

    ClientPlatform getCurrentPlatform();
    
}
