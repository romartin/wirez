package org.wirez.core.definition.annotation.morph;

import java.util.Map;

public interface MorphPropertyValueBinding<P, V> {


    V getValue( P property );

    Map<V, Class<?>> getMorphTargets();

}
