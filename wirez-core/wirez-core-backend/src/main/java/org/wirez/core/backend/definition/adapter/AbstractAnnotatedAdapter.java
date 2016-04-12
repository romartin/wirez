package org.wirez.core.backend.definition.adapter;


import org.wirez.core.api.definition.adapter.Adapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public abstract class AbstractAnnotatedAdapter<T> implements Adapter<T> {
    
    @SuppressWarnings("unchecked")
    protected <A extends Annotation, V> V getAnnotatedFieldValue(T object, Class<A> annotationClass) throws IllegalAccessException {
        V result = null;

        Field[] fields = object.getClass().getDeclaredFields();
        if ( null != fields ) {
            for (Field field : fields) {
                A annotation = field.getAnnotation(annotationClass);
                if ( null != annotation) {
                    field.setAccessible(true);
                    result = (V) field.get(object);
                }
            }
        }

        return result;
    }
    
}
