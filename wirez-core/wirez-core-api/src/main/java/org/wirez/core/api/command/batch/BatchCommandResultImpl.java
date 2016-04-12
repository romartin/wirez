package org.wirez.core.api.command.batch;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.command.CommandResult;

import java.util.Iterator;
import java.util.List;

@Portable
public final class BatchCommandResultImpl<V> implements BatchCommandResult<V> {
    
    private final List<CommandResult<V>> results;

    public BatchCommandResultImpl(@MapsTo("results") List<CommandResult<V>> results) {
        this.results = results;
    }

    @Override
    public Iterator<CommandResult<V>> iterator() {
        return results.iterator();
    }

}
