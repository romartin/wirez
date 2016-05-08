package org.wirez.core.api.definition.adapter.shared;

import org.wirez.core.api.definition.adapter.DefinitionSetAdapter;
import org.wirez.core.api.definition.impl.DefinitionSetImpl;
import org.wirez.core.api.graph.Graph;

import javax.enterprise.context.ApplicationScoped;
import java.util.Set;

// TODO
@ApplicationScoped
public class DefaultDefinitionSetAdapter implements DefinitionSetAdapter<DefinitionSetImpl> {

    @Override
    public boolean accepts(final Class<?> pojo) {
        return pojo.getName().equals(DefinitionSetImpl.class.getName());
    }

    @Override
    public String getId(final DefinitionSetImpl pojo) {
        return null;
    }

    @Override
    public String getDomain(final DefinitionSetImpl pojo) {
        return null;
    }

    @Override
    public Class<? extends Graph> getGraph(DefinitionSetImpl pojo) {
        return null;
    }

    @Override
    public String getGraphFactory(DefinitionSetImpl pojo) {
        return null;
    }

    @Override
    public String getDescription(final DefinitionSetImpl pojo) {
        return null;
    }

    @Override
    public Set<String> getDefinitions(final DefinitionSetImpl pojo) {
        return null;
    }

    @Override
    public int getPriority() {
        return 1;
    }
    
}
