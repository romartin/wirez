package org.wirez.core.client.canvas.command;


import org.wirez.core.api.rule.RuleViolation;

public final class CanvasViolationImpl implements CanvasViolation {
    
    private final String message;
    private final Type type;

    CanvasViolationImpl(final String message,
                        final Type type) {
        this.type = type;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Type getViolationType() {
        return type;
    }
    
    public static final class CanvasViolationBuilder {
        
        private final RuleViolation ruleViolation;

        public CanvasViolationBuilder(final RuleViolation ruleViolation) {
            this.ruleViolation = ruleViolation;
        }
        
        public CanvasViolation build() {
            return new CanvasViolationImpl( ruleViolation.getMessage(), ruleViolation.getViolationType());
        }
        
    }
    
}
