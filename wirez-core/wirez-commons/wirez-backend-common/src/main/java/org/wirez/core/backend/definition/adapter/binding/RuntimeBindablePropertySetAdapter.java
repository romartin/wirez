package org.wirez.core.backend.definition.adapter.binding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wirez.core.backend.definition.adapter.AbstractRuntimeAdapter;
import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.definition.adapter.binding.BindablePropertySetAdapter;

import java.util.Map;
import java.util.Set;

class RuntimeBindablePropertySetAdapter<T> extends AbstractRuntimeAdapter<T>
        implements BindablePropertySetAdapter<T> {

    private static final Logger LOG = LoggerFactory.getLogger(RuntimeBindablePropertySetAdapter.class);

    private Map<Class, String> propertyNameFieldNames;
    private Map<Class, Set<String>> propertiesFieldNames;

    @Override
    public void setBindings( final Map<Class, String> propertyNameFieldNames,
                             final Map<Class, Set<String>> propertiesFieldNames) {
        this.propertyNameFieldNames = propertyNameFieldNames;
        this.propertiesFieldNames = propertiesFieldNames;
    }

    @Override
    public String getId(T propertySet) {
        return BindableAdapterUtils.getPropertySetId( propertySet.getClass() );
    }

    @Override
    public String getName(T propertySet) {
        Class<?> type = propertySet.getClass();
        try {
            return getFieldValue( propertySet, propertyNameFieldNames.get( type ) );
        } catch ( IllegalAccessException e ) {
            LOG.error("Error obtaining name for Property Set with id " + getId( propertySet ));
        }

        return null;
    }

    @Override
    public Set<?> getProperties(T propertySet) {
        Class<?> type = propertySet.getClass();
        Set<String> fields = propertiesFieldNames.get( type );
        try {
            return getFieldValues( propertySet, fields );
        } catch ( IllegalAccessException e ) {
            LOG.error("Error obtaining properties for Property Set with id " + getId( propertySet ));
        }

        return null;
    }

    @Override
    public boolean accepts(Class<?> type) {
        return null != propertyNameFieldNames && propertyNameFieldNames.containsKey( type );
    }

}
