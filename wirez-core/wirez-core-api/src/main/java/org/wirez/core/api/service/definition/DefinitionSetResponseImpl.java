package org.wirez.core.api.service.definition;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.graph.Element;

import java.util.Collection;

@Portable
public class DefinitionSetResponseImpl implements DefinitionSetResponse {

    private final DefinitionSet definitionSet;
    private final Definition graphElement;
    private final Collection<Definition> definitions;

    public DefinitionSetResponseImpl(@MapsTo("definitionSet") DefinitionSet definitionSet,
                                     @MapsTo("graphElement") Definition graphElement,
                                     @MapsTo("definitions") Collection<Definition> definitions) {
        this.definitionSet = definitionSet;
        this.graphElement = graphElement;
        this.definitions = definitions;
    }

    @Override
    public DefinitionSet getDefinitionSet() {
        return definitionSet;
    }

    @Override
    public Definition getGraphElement() {
        return graphElement;
    }

    @Override
    public Collection<Definition> getDefinitions() {
        return definitions;
    }

}
