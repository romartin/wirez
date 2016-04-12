package org.wirez.core.api;

import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.diagram.Settings;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Graph;

public interface FactoryManager {

    /**
     * Build an instance of the domain model with the given id.
     * @param id The identifier for the model to build. Ex: "Task"
     */
    <W> W newDomainObject( String id );

    /**
     * Build an instance of the graph for the given DefinitionSet.
     * @param uuid The unique identifier for the generated graph.
     */
    <W extends Graph> W newGraph( String uuid, String definitionSetId );
    
    /**
     * Build an instance of the graph element (node or edge) for the domain model with the given Definition id.
     * @param uuid The unique identifier for the generated graph element.
     * @param id The identifier of the graph element's Definition. Ex: "Task"
     */
    <W extends Element> W newElement( String uuid, String id );

    /**
     * Build a new instance of a diagram.
     * @param uuid The unique identifier for the diagram.
     * @param graph The diagram's graph.
     * @param settings The diagram's settings.
     */
    <G extends Graph, S extends Settings> Diagram<G, S> newDiagram( String uuid, G graph, S settings );

}
