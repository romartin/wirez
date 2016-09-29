package org.kie.workbench.common.stunner.core.graph.command;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.kie.workbench.common.stunner.core.command.CommandResult;
import org.kie.workbench.common.stunner.core.command.impl.CommandResultBuilder;
import org.kie.workbench.common.stunner.core.command.impl.CommandResultImpl;
import org.kie.workbench.common.stunner.core.rule.RuleViolation;

import java.util.Collection;
import java.util.LinkedList;

@NonPortable
public class GraphCommandResultBuilder extends CommandResultBuilder<RuleViolation> {

    public static final CommandResult<RuleViolation> RESULT_OK = new CommandResultImpl<>(
            CommandResult.Type.INFO,
            RESULT_SUCCESS,
            new LinkedList<>()
    );
    
    public GraphCommandResultBuilder() {
    }

    public GraphCommandResultBuilder(final Collection<RuleViolation> violations) {
        super(violations);
    }

    @Override
    public boolean isError( final RuleViolation violation ) {
        return RuleViolation.Type.ERROR.equals( violation.getViolationType() );
    }

    @Override
    public String getMessage( final RuleViolation violation ) {
        return violation.getMessage();
    }
}
