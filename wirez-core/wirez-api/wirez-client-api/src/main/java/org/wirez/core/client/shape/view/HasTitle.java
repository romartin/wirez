package org.wirez.core.client.shape.view;

public interface HasTitle<T> {

    enum Position {
        CENTER, LEFT, RIGHT, TOP, BOTTOM;
    }
    
    T setTitle(String title);
    
    T setPosition(Position position);

    T setTitleStrokeColor(String color);

    T setFontFamily(String fontFamily);
    
    T setTitleFontSize(double fontSize);

    T setTitleStrokeWidth(double strokeWidth);
    
    T moveTitleToTop();
    
    T refreshTitle();
    
}
