package org.wirez.core.client.command;

import org.wirez.core.command.CommandResult;
import org.wirez.core.command.impl.CommandResultBuilder;
import org.wirez.core.command.impl.CommandResultImpl;
import org.wirez.core.rule.RuleViolation;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class CanvasCommandResultBuilder extends CommandResultBuilder<CanvasViolation> {

    public static final CommandResult<CanvasViolation> OK_COMMAND = new CommandResultImpl<>(
            CommandResult.Type.INFO,
            RESULT_SUCCESS,
            new LinkedList<>()
    );
    
    public CanvasCommandResultBuilder() {
        
    }
    
    public CanvasCommandResultBuilder(final Collection<CanvasViolation> violations) {
        super(violations);
    }

    public CanvasCommandResultBuilder(final CommandResult<RuleViolation> commandResult ) {
        
        // Use same message and result type.
        this.setMessage( commandResult.getMessage() );
        this.setType( commandResult.getType() );
        
        // Translate violations.
        final Iterable<RuleViolation> violations = commandResult.getViolations();
        final Iterator<RuleViolation> violationsIt = violations.iterator();
        while ( violationsIt.hasNext() ) {
            final RuleViolation ruleViolation = violationsIt.next();
            final CanvasViolation canvasViolation = 
                    new CanvasViolationImpl.CanvasViolationBuilder( ruleViolation )
                    .build();
            addViolation( canvasViolation );
        }
        
    }

    @Override
    public boolean isError( final CanvasViolation violation ) {
        return RuleViolation.Type.ERROR.equals( violation.getViolationType() );
    }

    @Override
    public String getMessage( final CanvasViolation violation ) {
        return violation.getMessage();
    }
}
