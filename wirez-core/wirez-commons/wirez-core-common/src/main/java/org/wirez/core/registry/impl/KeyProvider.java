package org.wirez.core.registry.impl;

public interface KeyProvider<T> {

    String getKey( T item );

}
