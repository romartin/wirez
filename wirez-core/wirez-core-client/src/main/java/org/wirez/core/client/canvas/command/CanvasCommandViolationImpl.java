package org.wirez.core.client.canvas.command;

import org.wirez.core.api.rule.RuleViolation;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class CanvasCommandViolationImpl implements CanvasCommandViolation {
    
    private String message;
    private final List<RuleViolation> ruleViolations = new LinkedList<>();

    public CanvasCommandViolationImpl(final String message) {
        this.message = message;
    }

    public CanvasCommandViolationImpl(final Collection<RuleViolation> ruleViolations ) {
        this.ruleViolations.addAll( ruleViolations );
    }

    public CanvasCommandViolationImpl(final RuleViolation ruleViolation) {
        this.ruleViolations.add( ruleViolation );
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Collection<RuleViolation> getModelViolation() {
        return ruleViolations;
    }
    
}
