package org.wirez.core.api.registry;

import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.rule.Rule;

import java.util.Collection;

public interface RuleRegistry<R extends Rule> extends Registry<Rule> {
    
    boolean containsRules(Definition definition);

    boolean containsRules(DefinitionSet definitionSet);
    
    void addRule(R rule, Definition definition);

    void addRule(R rule, DefinitionSet definitionSet);

    Collection<R> getRules(Definition definition);

    /**
     * Gets the rules specified in the given Definition Set (not visit children ones).
     */
    Collection<R> getRules(DefinitionSet definitionSet);

    /**
     * Gets the rules specified in the given Definition Set and the ones found on any of its children.
     */
    Collection<R> getAllRules(DefinitionSet definitionSet);

}
