package org.wirez.basicset.client;

import com.google.gwt.safehtml.shared.SafeUri;
import org.wirez.basicset.client.resources.BasicSetImageResources;
import org.wirez.core.client.util.SafeUriProvider;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BasicSetThumbnailProvider implements SafeUriProvider {
    
    @Override
    public SafeUri getUri() {
        return BasicSetImageResources.INSTANCE.basicSetThumb().getSafeUri();
    }
    
}
