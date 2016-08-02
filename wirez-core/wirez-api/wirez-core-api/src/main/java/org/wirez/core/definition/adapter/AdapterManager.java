package org.wirez.core.definition.adapter;

import org.wirez.core.definition.adapter.*;
import org.wirez.core.registry.definition.AdapterRegistry;

/**
 * Entry point for handling all definition adapters present on the context.
 */
public interface AdapterManager {

    /**
     * A generic definition set adapter for any type. It provides a shortcut
     * for introspecting definition sets.
     *
     * @return The definition set adapter.
     */
    DefinitionSetAdapter<Object> forDefinitionSet();

    /**
     * A generic rule adapter for any type. It provides a shortcut
     * for introspecting the rules on for the different definition sets.
     *
     * @return The rule adapter.
     */
    DefinitionSetRuleAdapter<Object> forRules();

    /**
     * A generic definition adapter for any type. It provides a shortcut
     * for introspecting definitions.
     *
     * @return The definition adapter.
     */
    DefinitionAdapter<Object> forDefinition();

    /**
     * A generic property set adapter for any type. It provides a shortcut
     * for introspecting property sets.
     *
     * @return The property set adapter.
     */
    PropertySetAdapter<Object> forPropertySet();

    /**
     * A generic property adapter for any type. It provides a shortcut
     * for introspecting properties.
     *
     * @return The property adapter.
     */
    PropertyAdapter<Object, Object> forProperty();

    /**
     * The registry that contains all the adapters present on the context.
     *
     * @return The adapter registry.
     */
    AdapterRegistry registry();

}
