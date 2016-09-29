package org.kie.workbench.common.stunner.core.definition.adapter.shared;

import org.kie.workbench.common.stunner.core.definition.impl.DefinitionImpl;
import org.kie.workbench.common.stunner.core.definition.adapter.DefinitionAdapter;
import org.kie.workbench.common.stunner.core.factory.graph.ElementFactory;

import javax.enterprise.context.ApplicationScoped;
import java.util.Set;

// TODO
@ApplicationScoped
public class DefaultDefinitionAdapter implements DefinitionAdapter<DefinitionImpl> {
    
    public DefaultDefinitionAdapter() {
    }

    @Override
    public String getId(DefinitionImpl pojo) {
        return pojo.getId();
    }

    @Override
    public Object getNameProperty(DefinitionImpl pojo) {
        return pojo.getNameProperty();
    }

    @Override
    public String getCategory(DefinitionImpl pojo) {
        return pojo.getCategory();
    }

    @Override
    public String getTitle(DefinitionImpl pojo) {
        return pojo.getTitle();
    }

    @Override
    public String getDescription(DefinitionImpl pojo) {
        return pojo.getDescription();
    }

    @Override
    public Set<String> getLabels(DefinitionImpl pojo) {
        return pojo.getLabels();
    }

    @Override
    public Set<?> getPropertySets(final DefinitionImpl pojo) {
        return pojo.getPropertySets();
    }

    @Override
    public Set<?> getProperties(final DefinitionImpl pojo) {
        return pojo.getProperties();
    }

    @Override
    public Class<? extends ElementFactory> getGraphFactoryType( DefinitionImpl pojo ) {
        // TODO
        return null;
    }

    @Override
    public boolean accepts(final Class<?> pojo) {
        return pojo.getName().equals(DefinitionImpl.class.getName());
    }

    @Override
    public boolean isPojoModel() {
        return false;
    }

    @Override
    public int getPriority() {
        return 1;
    }
    
}
