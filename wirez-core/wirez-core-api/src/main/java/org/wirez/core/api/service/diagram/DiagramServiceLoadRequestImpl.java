package org.wirez.core.api.service.diagram;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;

@Portable
public class DiagramServiceLoadRequestImpl implements DiagramServiceLoadRequest {
    
    final String path;

    public DiagramServiceLoadRequestImpl(@MapsTo("path") String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }
}
