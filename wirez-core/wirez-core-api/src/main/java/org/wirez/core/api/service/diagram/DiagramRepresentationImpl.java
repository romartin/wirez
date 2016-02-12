package org.wirez.core.api.service.diagram;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;

@Portable
public class DiagramRepresentationImpl implements DiagramRepresentation {
    
    private final String uuid;
    private final String name;
    private final String definitionSetId;
    private final String shapeSetId;
    private final String path;

    public DiagramRepresentationImpl(@MapsTo("uuid") final String uuid,
                                     @MapsTo("name") final String name,
                                     @MapsTo("definitionSetId") final String definitionSetId,
                                     @MapsTo("shapeSetId") final String shapeSetId,
                                     @MapsTo("path") final String path) {
        this.uuid = uuid;
        this.name = name;
        this.definitionSetId = definitionSetId;
        this.shapeSetId = shapeSetId;
        this.path = path;
    }

    @Override
    public String getUUID() {
        return uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDefinitionSetId() {
        return definitionSetId;
    }

    @Override
    public String getShapeSetId() {
        return shapeSetId;
    }

    @Override
    public String getPath() {
        return path;
    }
    
}
