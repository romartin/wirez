package org.wirez.core.client.api.platform;

public interface PlatformManager {
    
    Iterable<ClientPlatform> getSupportedPlatforms();

    ClientPlatform getCurrentPlatform();
    
}
