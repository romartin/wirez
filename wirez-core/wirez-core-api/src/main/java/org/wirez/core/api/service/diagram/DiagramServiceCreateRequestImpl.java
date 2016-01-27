package org.wirez.core.api.service.diagram;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;

@Portable
public class DiagramServiceCreateRequestImpl implements DiagramServiceCreateRequest {
    
    final String defSetId;
    final String shapeSetId;
    final String title;

    public DiagramServiceCreateRequestImpl(@MapsTo("defSetId") String defSetId,
                                           @MapsTo("shapeSetId") String shapeSetId,
                                           @MapsTo("title") String title) {
        this.defSetId = defSetId;
        this.shapeSetId = shapeSetId;
        this.title = title;
    }

    @Override
    public String getDefinitionSetId() {
        return defSetId;
    }

    @Override
    public String getShapeSetId() {
        return shapeSetId;
    }

    @Override
    public String getTitle() {
        return title;
    }
}
