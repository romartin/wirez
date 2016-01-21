package org.wirez.core.api.registry;

import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.rule.Rule;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.*;

public abstract class SharedRuleRegistry implements RuleRegistry<Rule> {
    
    protected final Map<String, List<Rule>> definitionSetRules = new HashMap<>();
    protected final Map<String, List<Rule>> definitionRules = new HashMap<>();

    @Override
    public boolean containsRules(Definition definition) {
        return definitionRules.containsKey(definition.getId());
    }

    @Override
    public boolean containsRules(DefinitionSet definitionSet) {
        return definitionSetRules.containsKey(definitionSet.getId());
    }

    @Override
    public void addRule(final Rule rule, final Definition definition) {
        List<Rule> rules = definitionRules.get(definition.getId());
        if ( null == rules ) {
            rules = new ArrayList<>();
            definitionRules.put(definition.getId(), rules);
        }
        rules.add(rule);
    }

    @Override
    public void addRule(final Rule rule, final DefinitionSet definitionSet) {
        List<Rule> rules = definitionSetRules.get(definitionSet.getId());
        if ( null == rules ) {
            rules = new ArrayList<>();
            definitionSetRules.put(definitionSet.getId(), rules);
        }
        rules.add(rule);
    }
    
    @Override
    public Collection<Rule> getRules(final Definition definition) {
        return definitionRules.get(definition.getId());
    }

    @Override
    public Collection<Rule> getRules(final DefinitionSet definitionSet) {
        return definitionSetRules.get(definitionSet.getId());
    }

    // TODO
    @Override
    public Collection<Rule> getAllRules(final DefinitionSet definitionSet) {
        return null;
    }

    @Override
    public Collection<Rule> getItems() {
        throw new UnsupportedOperationException("Use concrete RuleRegistry methods for now");
    }

    @Override
    public void clear() {
        definitionRules.clear();
        definitionSetRules.clear();
    }
}
