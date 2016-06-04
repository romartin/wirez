package org.wirez.core.client.api.platform;

import com.google.gwt.user.client.Window;
import org.jboss.errai.ioc.client.container.SyncBeanDef;
import org.jboss.errai.ioc.client.container.SyncBeanManager;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class PlatformManagerImpl implements PlatformManager {

    SyncBeanManager beanManager;
    private final List<ClientPlatform> supportedPlatforms = new LinkedList<>();
    
    @Inject
    public PlatformManagerImpl(final SyncBeanManager beanManager ) {
        this.beanManager = beanManager;
    }

    @PostConstruct
    public void init() {

        // Client platforms.
        Collection<SyncBeanDef<ClientPlatform>> sets = beanManager.lookupBeans(ClientPlatform.class);
        for (SyncBeanDef<ClientPlatform> set : sets) {
            ClientPlatform platform = set.getInstance();
            supportedPlatforms.add( platform );
        }
        
    }
    
    @Override
    public Iterable<ClientPlatform> getSupportedPlatforms() {
        return Collections.unmodifiableCollection( supportedPlatforms );
    }

    @Override
    public ClientPlatform getCurrentPlatform() {
        
        final String platform = Window.Navigator.getPlatform();

        for ( final ClientPlatform clientPlatform : supportedPlatforms ) {
            
            if ( clientPlatform.supports( platform ) ) {
                
                return clientPlatform;
                
            }
            
        }
        
        // final String result = "ClientManagerImpl [platform='" + platform + "'] [userAgent='" + userAgent + "']"; 
        // GWT.log( result );
        
        return null;
    }
    
}
