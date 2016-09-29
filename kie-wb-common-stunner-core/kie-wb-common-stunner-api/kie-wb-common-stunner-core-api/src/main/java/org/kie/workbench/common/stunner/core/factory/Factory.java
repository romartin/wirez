package org.kie.workbench.common.stunner.core.factory;

public interface Factory<T, S> {

    T build( S source );

}
