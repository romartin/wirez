package org.wirez.core.client;

import com.google.gwt.safehtml.shared.SafeUri;

public interface ShapeSetThumbProvider {
    
    boolean thumbFor( String definitionSetId );
    
    SafeUri getThumbnailUri();
    
}
