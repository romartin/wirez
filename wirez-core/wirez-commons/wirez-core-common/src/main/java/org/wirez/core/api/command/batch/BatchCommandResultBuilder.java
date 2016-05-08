package org.wirez.core.api.command.batch;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.command.CommandResultBuilder;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@NonPortable
public class BatchCommandResultBuilder<V> {

    private final List<CommandResult<V>> results = new LinkedList<>();

    public BatchCommandResultBuilder() {
    }
    
    public BatchCommandResultBuilder<V> add( final CommandResult<V> result ) {
        this.results.add( result );
        return this;
    }

    public BatchCommandResult<V> build() {
        return new BatchCommandResultImpl<>( results );
    }

    public CommandResult<V> asResult( final CommandResultBuilder<V> builder) {
        for ( CommandResult<V> rr : results ) {
            final Iterable<V> rrIter = rr.getViolations();
            if ( null != rrIter ) {
                final Iterator<V> rrIt = rrIter.iterator();
                while( rrIt.hasNext() ) {
                    final V v = rrIt.next();
                    builder.addViolation( v );
                }

            }
        }

        return builder.build();

    }   
    
}
