package org.wirez.core.client.view;

public interface ShapeView<T> {

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
    
    T moveToTop();

    T moveToBottom();

    T moveUp();

    T moveDown();
    
    void removeFromParent();
}
