package org.wirez.bpmn.api;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.rule.Rule;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

@ApplicationScoped
@Portable
public class BPMNDefinitionSet implements DefinitionSet {

    @Inject
    BPMNDiagram diagram;

    private final Collection<Definition> DEFINITIONS = new ArrayList<Definition>();
    
    @PostConstruct
    public void init() {
        DEFINITIONS.add( diagram );
    }
    
    @Override
    public String getId() {
        return "bpmnDefSet";
    }

    @Override
    public String getDomain() {
        return "bpmn.org";
    }

    @Override
    public String getDescription() {
        return "THe BPMN definition set";
    }

    @Override
    public Collection<PropertySet> getPropertySets() {
        return new ArrayList<>();
    }

    @Override
    public Collection<Definition> getDefinitions() {
        return DEFINITIONS;
    }

    @Override
    public Collection<Rule> getRules() {
        return new ArrayList<>();
    }
}
