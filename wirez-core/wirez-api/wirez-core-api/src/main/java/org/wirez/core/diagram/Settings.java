package org.wirez.core.diagram;

public interface Settings {

    String getDefinitionSetId();

    String getTitle();

    void setTitle( String title );

    String getShapeSetId();

    void setShapeSetId( String id );

    String getCanvasRootUUID();

    void setCanvasRootUUID( String uuid );
    
    String getVFSPath();
    
    void setVFSPath(String path );

    String getThumbData();

    void setThumbData( String data );
    
}
