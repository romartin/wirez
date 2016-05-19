package org.wirez.core.client.shape.view;

public interface ShapeView<T> {

    String UUID_PREFFIX = "wirez:";

    T setUUID(String uuid);
    
    String getUUID();
    
    double getShapeX();

    double getShapeY();
    
    T setShapeX(double x);

    T setShapeY(double y);
    
    String getFillColor();
    
    T setFillColor(String color);
    
    double getFillAlpha();
    
    T setFillAlpha(double alpha);
    
    String getStrokeColor();
    
    T setStrokeColor(String color);
    
    double getStrokeAlpha();
    
    T setStrokeAlpha(double alpha);
    
    double getStrokeWidth();
    
    T setStrokeWidth(double width);
    
    T setDragEnabled(boolean isDraggable);
    
    T moveToTop();

    T moveToBottom();

    T moveUp();

    T moveDown();
    
    T setZIndex(int zindez);
    
    int getZIndex();
    
    void removeFromParent();
    
    void destroy();
    
}
