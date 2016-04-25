package org.wirez.core.api.rule.impl.graph;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.definition.adapter.DefinitionAdapter;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.content.definition.Definition;
import org.wirez.core.api.graph.content.definition.DefinitionSet;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.rule.Rule;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.rule.impl.AbstractWrappedRuleManager;

import java.util.Set;

public abstract class AbstractGraphRuleManager<R extends Rule, M extends RuleManager<R>> 
        extends AbstractWrappedRuleManager<R, M> {
    
    protected DefinitionManager definitionManager;

    public AbstractGraphRuleManager( final DefinitionManager definitionManager ) {
        this.definitionManager = definitionManager;
    }

    @SuppressWarnings("unchecked")
    protected String getElementDefinitionId( final Element<?> element ) {
        String targetId = null;
        if ( element.getContent() instanceof View) {
            Object definition = ((View) element.getContent()).getDefinition();
            targetId = getDefinitionId( definition );
        } else if ( element.getContent() instanceof DefinitionSet) {
            targetId = ((DefinitionSet) element.getContent()).getId();
        } 
        return targetId;
    }
    
    @SuppressWarnings("unchecked")
    protected String getDefinitionId( final Object definition ) {
        DefinitionAdapter adapter = definitionManager.getDefinitionAdapter( definition.getClass() );
        return adapter.getId( definition );
    }
    
    protected Set<String> getLabels( final Element<? extends Definition<?>> element ) {
        return element != null ? element.getLabels() : null;
    } 
    
}
