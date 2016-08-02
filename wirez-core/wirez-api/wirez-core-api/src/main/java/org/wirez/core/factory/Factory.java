package org.wirez.core.factory;

public interface Factory<T, S> {

    T build( S source );

}
