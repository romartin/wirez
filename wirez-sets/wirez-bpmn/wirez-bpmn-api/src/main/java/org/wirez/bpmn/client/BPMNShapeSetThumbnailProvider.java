package org.wirez.bpmn.client;

import com.google.gwt.safehtml.shared.SafeUri;
import org.wirez.bpmn.client.resources.BPMNImageResources;
import org.wirez.core.client.util.SafeUriProvider;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BPMNShapeSetThumbnailProvider implements SafeUriProvider {
    
    @Override
    public SafeUri getUri() {
        return BPMNImageResources.INSTANCE.bpmnSetThumb().getSafeUri();
    }
    
}
