package org.kie.workbench.common.stunner.core.registry.definition;

import org.kie.workbench.common.stunner.core.definition.adapter.*;
import org.kie.workbench.common.stunner.core.registry.Registry;

public interface AdapterRegistry extends Registry<Adapter> {

    /**
     * Returns the Definition Set adapter instance for the given type.
     */
    <T> DefinitionSetAdapter<T> getDefinitionSetAdapter( Class<?> type );

    /**
     * Returns the Definition Set rules adapter instance for the given type.
     */
    <T> DefinitionSetRuleAdapter<T> getDefinitionSetRuleAdapter( Class<?> type );

    /**
     * Returns the Definition adapter instance for the given type.
     */
    <T> DefinitionAdapter<T> getDefinitionAdapter( Class<?> type );

    /**
     * Returns the Property Set adapter instance for the given property set's type.
     */
    <T> PropertySetAdapter<T> getPropertySetAdapter( Class<?> type );

    /**
     * Returns the Property adapter instance for the given property's type.
     */
    <T> PropertyAdapter<T, ?> getPropertyAdapter( Class<?> type );

    /**
     * Returns the Morphing adapter instance for a given Definition type.
     */
    <T> MorphAdapter<T> getMorphAdapter( Class<?> type );

}
