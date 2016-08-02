package org.wirez.core.factory.definition;

/**
 * Factory for Definition domain objects based on pojo domain models.
 * The <code>identifier</code> argument for <code>accepts</code> and <code>build</code> methods
 * corresponds with:
 * - the definition type identifier. ( Eg: Task, Rectangle )
 * - or the object type ( Eg: Rectangle.class, Task.class )
 */
public interface TypeDefinitionFactory<T> extends DefinitionFactory<T> {

    boolean accepts( Class<? extends T> type );

    T build( Class<? extends T> type );

}
