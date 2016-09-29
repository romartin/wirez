package org.kie.workbench.common.stunner.basicset.client;

import com.google.gwt.safehtml.shared.SafeUri;
import org.kie.workbench.common.stunner.basicset.BasicSet;
import org.kie.workbench.common.stunner.basicset.client.resources.BasicSetImageResources;
import org.kie.workbench.common.stunner.core.api.DefinitionManager;
import org.kie.workbench.common.stunner.core.client.BindableShapeSetThumbProvider;

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
