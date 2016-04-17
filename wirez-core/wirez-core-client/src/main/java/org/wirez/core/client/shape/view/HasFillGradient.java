package org.wirez.core.client.shape.view;

public interface HasFillGradient<T> {
    
    enum Type {
        LINEAR;
    }
    
    T setFillGradient(Type type, String startColor, String endColor);
    
}
