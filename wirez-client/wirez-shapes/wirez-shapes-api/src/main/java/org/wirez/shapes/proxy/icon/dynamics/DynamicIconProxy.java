package org.wirez.shapes.proxy.icon.dynamics;

public interface DynamicIconProxy<W> extends IconProxy<W> {
    
    Icons getIcon(W definition);
    
}
