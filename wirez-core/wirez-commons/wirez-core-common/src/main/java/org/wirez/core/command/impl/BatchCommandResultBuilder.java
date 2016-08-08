package org.wirez.core.command.impl;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.wirez.core.command.CommandResult;
import org.wirez.core.command.batch.BatchCommandResult;

import java.util.Collection;
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

        CommandResult.Type type = CommandResult.Type.INFO;
        String message = "Found" + results.size() + " violations.";
        final Collection<V> violations = new LinkedList<V>();

        for ( CommandResult<V> rr : results ) {

            if ( hasMoreSeverity( rr.getType(), type ) ) {

                type = rr.getType();

            }

            final Iterable<V> rrIter = rr.getViolations();

            if ( null != rrIter ) {

                final Iterator<V> rrIt = rrIter.iterator();

                while( rrIt.hasNext() ) {

                    final V v = rrIt.next();

                    violations.add( v );

                }

            }

        }

        return new BatchCommandResultImpl<>( results, type, message, violations );
    }

    private boolean hasMoreSeverity( final CommandResult.Type type, final CommandResult.Type reference ) {

        return type.getSeverity() > reference.getSeverity();

    }

}
