package org.wirez.core.api.command;

import org.wirez.core.api.command.batch.BatchCommandResult;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CommandUtils {

    public static boolean isError( final CommandResult<?> result ) {
        return CommandResult.Type.ERROR.equals( result.getType() );
    }

    @SuppressWarnings("unchecked")
    public static boolean isError( final BatchCommandResult result ) {
        final Iterator<CommandResult> it = result.iterator();
        while ( it.hasNext() ) {
            final CommandResult r = it.next();
            if ( isError( r ) ) {
                return true;
            }
        }
        return false;
    }
    
    public static <V> List<V> toList( final Iterable<V> iterable ) {
        final List<V> result = new LinkedList<>();
        final Iterator<V> iterator = iterable.iterator();
        while ( iterator.hasNext() ) {
            final V v = iterator.next();
            result.add( v );
        }
        return result;
    }
}
