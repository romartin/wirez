package org.wirez.core.definition.adapter.binding;

import org.wirez.core.definition.adapter.MorphAdapter;

import java.util.Collection;
import java.util.Map;

public interface BindableMorphAdapter<S, T> extends MorphAdapter<S, T> {

    BindableMorphAdapter<S, T> setDefaultDefinitionType(Class<?> type);

    BindableMorphAdapter<S, T> setDomainMorphs(Map<Class<?>, Collection<Class<?>>> domainMorphs);
    
}
