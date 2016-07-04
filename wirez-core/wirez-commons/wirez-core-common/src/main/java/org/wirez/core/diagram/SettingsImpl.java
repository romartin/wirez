package org.wirez.core.diagram;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;

@Portable
public final class SettingsImpl implements Settings {
    
    private final String title;
    private final String defSetId;
    private final String shapeSetId;
    private String canvasRootUUID;
    private String path;

    public SettingsImpl(@MapsTo("title") String title,
                        @MapsTo("defSetId") String defSetId,
                        @MapsTo("shapeSetId") String shapeSetId,
                        @MapsTo("canvasRootUUID") String canvasRootUUID) {
        this.title = title;
        this.defSetId = defSetId;
        this.shapeSetId = shapeSetId;
        this.canvasRootUUID = canvasRootUUID;
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
    public String getCanvasRootUUID() {
        return canvasRootUUID;
    }

    @Override
    public void setCanvasRootUUID( final String uuid) {
        this.canvasRootUUID = uuid;
    }

    @Override
    public String getVFSPath() {
        return path;
    }

    @Override
    public void setVFSPath(final String path) {
        this.path = path;
    }

}
