package org.wirez.core.api.definition.adapter;

import java.util.Set;

public interface PropertySetAdapter<T> extends Adapter<T> {

    String getId(T pojo);

    String getName(T pojo);
    
    Set<?> getProperties(T pojo);
    
}
