package org.wirez.core.validation.graph;

import org.wirez.core.graph.Element;
import org.wirez.core.rule.RuleViolation;
import org.wirez.core.validation.AbstractValidationViolation;

public final class GraphValidationViolationImpl
        extends AbstractValidationViolation<Element<?>>
        implements GraphValidationViolation {

    private final RuleViolation ruleViolation;

    protected GraphValidationViolationImpl( final Element<?> entity,
                                            final RuleViolation ruleViolation ) {
        super( entity );
        this.ruleViolation = ruleViolation;
    }

    @Override
    public RuleViolation getRuleViolation() {
        return ruleViolation;
    }

    @Override
    public String getMessage() {
        return ruleViolation.getMessage();
    }

}
