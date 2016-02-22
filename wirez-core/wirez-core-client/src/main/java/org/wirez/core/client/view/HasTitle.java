package org.wirez.core.client.view;

public interface HasTitle<T> {

    T setTitle(String title);

    T setTitleStrokeColor(String color);

    T setFontFamily(String fontFamily);
    
    T setTitleFontSize(double fontSize);

    T setTitleStrokeWidth(double strokeWidth);
    
    T moveTitleToTop();
    
    T refreshTitle();
    
}
