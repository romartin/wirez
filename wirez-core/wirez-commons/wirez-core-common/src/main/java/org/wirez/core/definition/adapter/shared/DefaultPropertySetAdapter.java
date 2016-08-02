package org.wirez.core.definition.adapter.shared;

import org.wirez.core.definition.adapter.PropertySetAdapter;
import org.wirez.core.definition.impl.PropertySetImpl;

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
