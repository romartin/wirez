package org.wirez.core.api.definition.adapter.shared;

import org.wirez.core.api.definition.adapter.DefinitionAdapter;
import org.wirez.core.api.definition.impl.DefinitionImpl;
import org.wirez.core.api.graph.Element;

import javax.enterprise.context.ApplicationScoped;
import java.util.Set;

// TODO
@ApplicationScoped
public class DefaultDefinitionAdapter implements DefinitionAdapter<DefinitionImpl> {
    
    public DefaultDefinitionAdapter() {
    }

    @Override
    public String getId(DefinitionImpl pojo) {
        return null;
    }

    @Override
    public String getCategory(DefinitionImpl pojo) {
        return null;
    }

    @Override
    public String getTitle(DefinitionImpl pojo) {
        return null;
    }

    @Override
    public String getDescription(DefinitionImpl pojo) {
        return null;
    }

    @Override
    public Set<String> getLabels(DefinitionImpl pojo) {
        return null;
    }

    @Override
    public Set<?> getPropertySets(final DefinitionImpl pojo) {
        return null;
    }

    @Override
    public Set<?> getProperties(final DefinitionImpl pojo) {
        return null;
    }

    @Override
    public Class<? extends Element> getGraphElement(final DefinitionImpl pojo) {
        // TODO
        return null;
    }

    @Override
    public String getElementFactory(DefinitionImpl pojo) {
        // TODO
        return null;
    }

    @Override
    public boolean accepts(final Class<?> pojo) {
        return pojo.getName().equals(DefinitionImpl.class.getName());
    }

    @Override
    public int getPriority() {
        return 1;
    }
    
}
