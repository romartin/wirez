package org.wirez.core.client.view;

public interface HasFillGradient<T> {
    
    enum Type {
        LINEAR;
    }
    
    T setFillGradient(Type type, String startColor, String endColor, double width, double height);
    
}
