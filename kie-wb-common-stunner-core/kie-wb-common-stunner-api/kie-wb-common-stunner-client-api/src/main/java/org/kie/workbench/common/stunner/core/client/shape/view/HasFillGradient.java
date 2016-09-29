package org.kie.workbench.common.stunner.core.client.shape.view;

public interface HasFillGradient<T> {
    
    enum Type {
        LINEAR;
    }
    
    T setFillGradient(Type type, String startColor, String endColor);
    
}
