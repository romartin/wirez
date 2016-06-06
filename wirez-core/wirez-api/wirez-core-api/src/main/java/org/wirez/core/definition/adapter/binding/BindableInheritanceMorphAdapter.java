package org.wirez.core.definition.adapter.binding;

import org.wirez.core.definition.adapter.InheritanceMorphAdapter;
import org.wirez.core.definition.adapter.MorphProperty;

import java.util.Map;

public interface BindableInheritanceMorphAdapter<S, T extends S, P> extends InheritanceMorphAdapter<S, T, P> {

    BindableMorphAdapter<S, T> setDefaultDefinitionType(Class<?> type);

    BindableInheritanceMorphAdapter<S, T, P> setPropertyMorph(Map<Class<?>, MorphProperty<P>> propertyMorphs);

}
