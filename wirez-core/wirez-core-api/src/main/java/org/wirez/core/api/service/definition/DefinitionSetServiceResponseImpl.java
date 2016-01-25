package org.wirez.core.api.service.definition;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.rule.Rule;

import java.util.Collection;
import java.util.Set;

@Portable
public class DefinitionSetServiceResponseImpl implements DefinitionSetServiceResponse {

    private final DefinitionSet definitionSet;
    private final Definition graphElement;
    private final Set<Definition> definitions;
    private final Set<PropertySet> propertySets;
    private final Collection<Rule> rules;

    public DefinitionSetServiceResponseImpl(@MapsTo("definitionSet") DefinitionSet definitionSet,
                                            @MapsTo("graphElement") Definition graphElement,
                                            @MapsTo("definitions") Set<Definition> definitions,
                                            @MapsTo("propertySets") Set<PropertySet> propertySets,
                                            @MapsTo("rules") Collection<Rule> rules) {
        this.definitionSet = definitionSet;
        this.graphElement = graphElement;
        this.definitions = definitions;
        this.propertySets = propertySets;
        this.rules = rules;
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
    public Set<Definition> getDefinitions() {
        return definitions;
    }

    @Override
    public Set<PropertySet> getPropertySets() {
        return propertySets;
    }

    @Override
    public Collection<Rule> getRules() {
        return rules;
    }

}
