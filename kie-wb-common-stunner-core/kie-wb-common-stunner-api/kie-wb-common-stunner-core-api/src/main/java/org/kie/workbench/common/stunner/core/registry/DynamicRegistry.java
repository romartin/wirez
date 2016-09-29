package org.kie.workbench.common.stunner.core.registry;

public interface DynamicRegistry<T> extends Registry<T> {

    void register( T item );

    boolean remove( T item );

    void clear();

}
