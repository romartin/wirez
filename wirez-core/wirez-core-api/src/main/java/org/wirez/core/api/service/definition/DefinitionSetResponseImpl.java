package org.wirez.core.api.service.definition;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.graph.Element;

@Portable
public class DefinitionSetResponseImpl implements DefinitionSetResponse {

    private final DefinitionSet definitionSet;
    private final Definition graphElement;

    public DefinitionSetResponseImpl(@MapsTo("definitionSet") DefinitionSet definitionSet,
                                     @MapsTo("graphElement") Definition graphElement) {
        this.definitionSet = definitionSet;
        this.graphElement = graphElement;
    }

    @Override
    public DefinitionSet getDefinitionSet() {
        return definitionSet;
    }

    @Override
    public Definition getGraphElement() {
        return graphElement;
    }

}
