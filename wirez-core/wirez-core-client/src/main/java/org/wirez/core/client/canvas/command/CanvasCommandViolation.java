package org.wirez.core.client.canvas.command;

import org.wirez.core.api.rule.RuleViolation;

import java.util.Collection;

public interface CanvasCommandViolation {
    
    String getMessage();
    
    Collection<RuleViolation> getModelViolation();
    
}
