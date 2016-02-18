package org.wirez.core.client.canvas.command;

import org.wirez.core.api.rule.RuleViolation;

public interface CanvasCommandViolation {
    
    String getMessage();
    
    RuleViolation getModelViolation();
    
}
