package org.wirez.core.factory.definition;

import org.wirez.core.factory.Factory;

/**
 * Factory for Definition domain objects.
 * The <code>identifier</code> argument for <code>accepts</code> and <code>build</code> methods
 * corresponds with the definition type identifier. ( Eg: Task, Rectangle ).
 */
public interface DefinitionFactory<T> extends Factory<T, String> {

    boolean accepts( String identifier );

}
