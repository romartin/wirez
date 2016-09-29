package org.kie.workbench.common.stunner.client.widgets.notification.canvas;

public final class CanvasNotificationContext {
    
    private final String canvasUUID;
    private final String diagramUUID;
    private final String diagramTitle;

    public CanvasNotificationContext(final String canvasUUID,
                                     final String diagramUUID, 
                                     final String diagramTitle) {
        this.canvasUUID = canvasUUID;
        this.diagramUUID = diagramUUID;
        this.diagramTitle = diagramTitle;
    }

    public String getCanvasUUID() {
        return canvasUUID;
    }

    public String getDiagramUUID() {
        return diagramUUID;
    }

    public String getDiagramTitle() {
        return diagramTitle;
    }

    @Override
    public String toString() {
        return "[Diagram='" + diagramTitle + "' (" + diagramUUID + "), Canvas=" + canvasUUID + "]";
    }
    
}
