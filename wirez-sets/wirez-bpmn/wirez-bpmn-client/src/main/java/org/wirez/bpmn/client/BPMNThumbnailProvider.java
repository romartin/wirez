package org.wirez.bpmn.client;

import com.google.gwt.safehtml.shared.SafeUri;
import org.wirez.bpmn.BPMNDefinitionSet;
import org.wirez.bpmn.client.resources.BPMNImageResources;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.client.BindableShapeSetThumbProvider;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class BPMNThumbnailProvider extends BindableShapeSetThumbProvider {
    
    @Inject
    public BPMNThumbnailProvider(final DefinitionManager definitionManager ) {
        super( definitionManager );
    }

    @Override
    protected boolean thumbFor(final Class<?> clazz) {
        return isSameClass( clazz, BPMNDefinitionSet.class );
    }

    @Override
    public SafeUri getThumbnailUri() {
        return BPMNImageResources.INSTANCE.bpmnSetThumb().getSafeUri();
    }
    
}
