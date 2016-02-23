package org.wirez.core.api.rule.violations;

import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.rule.RuleViolation;

public abstract class AbstractRuleViolation implements RuleViolation {

    protected String getDefinitionTitle(final Element<? extends View<?>> element) {
        return element.getContent().getDefinition().getTitle();
    }
    
}
