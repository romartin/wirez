package org.wirez.core.diagram;

public interface Settings {

    String getTitle();
    
    String getDefinitionSetId();
    
    String getShapeSetId();

    String getCanvasRootUUID();

    void setCanvasRootUUID( String uuid );
    
    String getVFSPath();
    
    void setVFSPath(String path );
    
}
