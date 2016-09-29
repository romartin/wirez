package org.kie.workbench.common.stunner.core.validation.graph;

import org.kie.workbench.common.stunner.core.graph.Element;
import org.kie.workbench.common.stunner.core.rule.RuleViolation;
import org.kie.workbench.common.stunner.core.validation.AbstractValidationViolation;

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
