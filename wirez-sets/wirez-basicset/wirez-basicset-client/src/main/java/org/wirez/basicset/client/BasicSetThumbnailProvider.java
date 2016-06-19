package org.wirez.basicset.client;

import com.google.gwt.safehtml.shared.SafeUri;
import org.wirez.basicset.BasicSet;
import org.wirez.basicset.client.resources.BasicSetImageResources;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.client.BindableShapeSetThumbProvider;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class BasicSetThumbnailProvider extends BindableShapeSetThumbProvider {
    
    @Inject
    public BasicSetThumbnailProvider( final DefinitionManager definitionManager ) {
        super( definitionManager );
    }

    @Override
    protected boolean thumbFor(final Class<?> clazz) {
        return isSameClass( clazz, BasicSet.class );
    }

    @Override
    public SafeUri getThumbnailUri() {
        return BasicSetImageResources.INSTANCE.basicSetThumb().getSafeUri();
    }
    
}
