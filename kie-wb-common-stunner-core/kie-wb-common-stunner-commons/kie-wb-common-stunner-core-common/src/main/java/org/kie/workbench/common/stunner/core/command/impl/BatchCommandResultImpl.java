package org.kie.workbench.common.stunner.core.command.impl;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.kie.workbench.common.stunner.core.command.CommandResult;
import org.kie.workbench.common.stunner.core.command.batch.BatchCommandResult;

import java.util.Iterator;
import java.util.List;

@Portable
public final class BatchCommandResultImpl<V> implements BatchCommandResult<V> {
    
    private final List<CommandResult<V>> results;
    private final Type type;
    private final String message;
    private final Iterable<V> violations;

    public BatchCommandResultImpl( @MapsTo( "results" ) List<CommandResult<V>> results,
                                   @MapsTo( "type" ) Type type,
                                   @MapsTo( "message" ) String message,
                                   @MapsTo( "violations" ) Iterable<V> violations ) {
        this.results = results;
        this.type = type;
        this.message = message;
        this.violations = violations;
    }

    @Override
    public Iterator<CommandResult<V>> iterator() {
        return results.iterator();
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Iterable<V> getViolations() {
        return violations;
    }
}
