package org.wirez.core.api;

import org.jboss.errai.bus.server.annotations.Remote;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Graph;

@Remote
public interface FactoryManager {

    /**
     * Build an instance of the model with the given id.
     * @param id The identifier for the model to build. Ex: "Task"
     */
    <W> W model( String id );

    /**
     * Build an instance of the graph for the given DefinitionSet.
     * @param uuid The unique identifier for the generated graph.
     */
    <W extends Graph> W graph( String uuid, String definitionSetId );
    
    /**
     * Build an instance of the graph element (node or edge) for the model with the given Definition id.
     * @param uuid The unique identifier for the generated graph element.
     * @param id The identifier of the graph element's Definition. Ex: "Task"
     */
    <W extends Element> W element( String uuid, String id );
    
}
