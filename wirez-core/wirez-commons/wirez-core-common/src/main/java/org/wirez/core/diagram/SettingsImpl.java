package org.wirez.core.diagram;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;

@Portable
public final class SettingsImpl implements Settings {

    private final String defSetId;
    private String title;
    private String shapeSetId;
    private String canvasRootUUID;
    private String path;
    private String thumbData;

    public SettingsImpl( @MapsTo("defSetId") String defSetId ) {
        this.defSetId = defSetId;
    }
    
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle( final String title ) {
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
    public void setShapeSetId( final String id ) {
        this.shapeSetId = id;
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

    @Override
    public String getThumbData() {
        return thumbData;
    }

    @Override
    public void setThumbData( final String data ) {
        this.thumbData = data;
    }

}
