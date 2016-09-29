package org.kie.workbench.common.stunner.core.definition.adapter.shared;

import org.kie.workbench.common.stunner.core.definition.impl.PropertySetImpl;
import org.kie.workbench.common.stunner.core.definition.adapter.PropertySetAdapter;

import javax.enterprise.context.ApplicationScoped;
import java.util.Set;

// TODO
@ApplicationScoped
public class DefaultPropertySetAdapter implements PropertySetAdapter<PropertySetImpl> {

    @Override
    public boolean accepts(final Class<?> pojo) {
        return pojo.getName().equals(PropertySetImpl.class.getName());
    }

    @Override
    public boolean isPojoModel() {
        return false;
    }

    @Override
    public String getId(final PropertySetImpl pojo) {
        return null;
    }

    @Override
    public String getName(final PropertySetImpl pojo) {
        return null;
    }

    @Override
    public Set<?> getProperties(final PropertySetImpl pojo) {
        return null;
    }

    @Override
    public int getPriority() {
        return 1;
    }
    
}
