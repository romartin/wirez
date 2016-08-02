package org.wirez.core.api;

import org.wirez.core.diagram.Diagram;
import org.wirez.core.diagram.Settings;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.content.definition.Definition;
import org.wirez.core.registry.factory.FactoryRegistry;

/**
 * Entry point that handles the different Factories and provides
 * different high level operations for constructing types.
 */
public interface FactoryManager {

    /**
     * Creates a new Definition by a given identifier.
     *
     * @param id The definition identifier ( Eg: "task" )
     * @param <T> The definition instance type ( Eg. Task )
     * @return A new definition instance.
     */
    <T> T newDefinition( String id );

    /**
     * Creates a new Definition by a given type, if the domain model is based on java POJO classes.
     *
     * @param type The definition type ( Eg: Task.class )
     * @param <T> The definition instance type ( Eg. Task )
     * @return A new definition instance.
     */
    <T> T newDefinition( Class<T> type );

    /**
     * Creates a new graph element for the given Definition identifier.
     * TODO: Generics.
     *
     * @param uuid The element unique identifier.
     * @param id The definition identifier.
     * @return A new graph, node or edge which content is based on the a Definition.
     */
    Element newElement( String uuid, String id );

    /**
     * Creates a new graph element for the given Definition type.
     * TODO: Generics.
     *
     * @param uuid The element unique identifier.
     * @param type The definition type.
     * @return A new graph, node or edge which content is based on the a Definition.
     */
    Element newElement( String uuid, Class<?> type );

    /**
     * Creates a new diagram for the given Definition Set identifier.
     *
     * @param uuid The unique diagram's identifier.
     * @param id The definition set identifier.
     * @param <D> The diagram type.
     * @return A new diagram instance.
     */
    <D extends Diagram> D newDiagram( String uuid, String id );

    /**
     * Creates a new diagram for the given Definition Set type.
     *
     * @param uuid The unique diagram's identifier.
     * @param type The definition set type.
     * @param <D> The diagram type.
     * @return A new diagram instance.
     */
    <D extends Diagram> D newDiagram( String uuid, Class<?> type );

    /**
     * The registry that handles all different factories.
     *
     * @return The factory registry.
     */
    FactoryRegistry registry();

}
