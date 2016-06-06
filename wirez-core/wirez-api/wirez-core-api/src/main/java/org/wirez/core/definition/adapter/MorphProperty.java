package org.wirez.core.definition.adapter;

import java.util.Collection;

public interface MorphProperty<P> {
    
    String getProperty();
    
    String getMorphTarget( Object value );

    Collection<String> getMorphTargets();
    
}
