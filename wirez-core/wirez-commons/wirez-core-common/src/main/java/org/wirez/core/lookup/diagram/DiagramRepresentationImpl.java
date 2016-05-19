package org.wirez.core.lookup.diagram;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.diagram.Diagram;

@Portable
public final class DiagramRepresentationImpl implements DiagramRepresentation {

    private final String uuid;
    private final String graphUUID;
    private final String title;
    private final String defSetId;
    private final String shapeSetId;
    private final String vfsPath;

    public DiagramRepresentationImpl(@MapsTo("uuid")  String uuid,
                                     @MapsTo("graphUUID")  String graphUUID,
                                     @MapsTo("title")  String title,
                                     @MapsTo("defSetId")  String defSetId,
                                     @MapsTo("shapeSetId")  String shapeSetId,
                                     @MapsTo("vfsPath")  String vfsPath) {
        this.uuid = uuid;
        this.graphUUID = graphUUID;
        this.title = title;
        this.defSetId = defSetId;
        this.shapeSetId = shapeSetId;
        this.vfsPath = vfsPath;
    }

    @Override
    public String getUUID() {
        return uuid;
    }

    @Override
    public String getGraphUUID() {
        return graphUUID;
    }

    @Override
    public String getTitle() {
        return title;
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
    public String getVFSPath() {
        return vfsPath;
    }
    
    @NonPortable
    public static final class DiagramRepresentationBuilder {
        
        private final Diagram diagram;

        public DiagramRepresentationBuilder(final Diagram diagram) {
            this.diagram = diagram;
        }
        
        public DiagramRepresentation build() {
            return new DiagramRepresentationImpl( diagram.getUUID(),
                    diagram.getGraph().getUUID(), diagram.getSettings().getTitle(),
                    diagram.getSettings().getDefinitionSetId(),
                    diagram.getSettings().getShapeSetId(),
                    diagram.getSettings().getVFSPath());
        }
        
    }
    
}
