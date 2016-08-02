package org.wirez.core.registry;

import java.util.Collection;

public interface Registry<T> {

    boolean contains( T item );

    Collection<T> getItems();

}
