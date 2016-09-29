package org.kie.workbench.common.stunner.bpmn.client;

import com.google.gwt.safehtml.shared.SafeUri;
import org.kie.workbench.common.stunner.bpmn.BPMNDefinitionSet;
import org.kie.workbench.common.stunner.bpmn.client.resources.BPMNImageResources;
import org.kie.workbench.common.stunner.core.api.DefinitionManager;
import org.kie.workbench.common.stunner.core.client.BindableShapeSetThumbProvider;

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
