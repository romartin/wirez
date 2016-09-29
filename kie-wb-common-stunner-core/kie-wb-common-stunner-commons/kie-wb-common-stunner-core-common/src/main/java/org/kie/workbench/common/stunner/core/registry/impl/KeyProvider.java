package org.kie.workbench.common.stunner.core.registry.impl;

public interface KeyProvider<T> {

    String getKey( T item );

}
