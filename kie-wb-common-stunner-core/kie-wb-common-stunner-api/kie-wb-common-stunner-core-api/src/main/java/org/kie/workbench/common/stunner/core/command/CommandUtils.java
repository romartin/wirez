package org.kie.workbench.common.stunner.core.command;

import org.kie.workbench.common.stunner.core.command.batch.BatchCommandResult;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CommandUtils {

    public static boolean isError( final CommandResult<?> result ) {

        if ( result instanceof BatchCommandResult ) {

            final BatchCommandResult<?> batchCommandResult = ( BatchCommandResult<?> ) result;

            return isBatchCommandResultError( batchCommandResult );

        }

        return isCommandResultError( result );
    }


    private static boolean isCommandResultError( final CommandResult<?> result ) {
        return result != null && CommandResult.Type.ERROR.equals( result.getType() );
    }

    @SuppressWarnings("unchecked")
    private static boolean isBatchCommandResultError( final BatchCommandResult result ) {
        return result != null && CommandResult.Type.ERROR.equals( result.getType() );
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
