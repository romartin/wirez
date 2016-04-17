package org.wirez.client.widgets.canvas;

import com.google.gwt.user.client.ui.IsWidget;
import org.uberfire.mvp.Command;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.client.canvas.CanvasHandler;

public interface CanvasHandlerPresenter<D extends Diagram, C extends CanvasHandler> extends IsWidget {
    
    void initialize( int width, int height );
    
    void newDiagram(String uuid, String title, String definitionSetId, String shapeSetId, Command callback );
    
    void load( String diagramUUID, Command callback );
    
    void save( Command callback );
    
    void open( D diagram, Command callback );
    
    void clear();
    
    void undo();
    
    void clearSelection();
    
    void deleteSelected();
    
    D getDiagram();
    
    C getCanvasHandler();
    
}
