package org.wirez.core.backend.definition.adapter.binding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wirez.core.backend.definition.adapter.AbstractRuntimeAdapter;
import org.wirez.core.definition.adapter.binding.BindableDefinitionAdapter;
import org.wirez.core.graph.Element;

import java.util.Map;
import java.util.Set;

class RuntimeBindableDefinitionAdapter<T>  extends AbstractRuntimeAdapter<T>
        implements BindableDefinitionAdapter<T> {

    private static final Logger LOG = LoggerFactory.getLogger(RuntimeBindableDefinitionAdapter.class);

    private Class<?> namePropertyClass;
    private Map<Class, Set<String>> propertySetsFieldNames;
    private Map<Class, Set<String>> propertiesFieldNames;
    private Map<Class, Class> propertyGraphElementFieldNames;
    private Map<Class, String> propertyElementFactoryFieldNames;
    private Map<Class, String> propertyLabelsFieldNames;
    private Map<Class, String> propertyTitleFieldNames;
    private Map<Class, String> propertyCategoryFieldNames;
    private Map<Class, String> propertyDescriptionFieldNames;

    @Override
    public void setBindings( final Class<?> namePropertyClass,
                             final Map<Class, Set<String>> propertySetsFieldNames,
                             final Map<Class, Set<String>> propertiesFieldNames,
                             final Map<Class, Class> propertyGraphElementFieldNames,
                             final Map<Class, String> propertyElementFactoryFieldNames,
                             final Map<Class, String> propertyLabelsFieldNames,
                             final Map<Class, String> propertyTitleFieldNames,
                             final Map<Class, String> propertyCategoryFieldNames,
                             final Map<Class, String> propertyDescriptionFieldNames) {
        this.namePropertyClass = namePropertyClass;
        this.propertySetsFieldNames = propertySetsFieldNames;
        this.propertiesFieldNames = propertiesFieldNames;
        this.propertyGraphElementFieldNames = propertyGraphElementFieldNames;
        this.propertyElementFactoryFieldNames = propertyElementFactoryFieldNames;
        this.propertyLabelsFieldNames = propertyLabelsFieldNames;
        this.propertyTitleFieldNames = propertyTitleFieldNames;
        this.propertyCategoryFieldNames = propertyCategoryFieldNames;
        this.propertyDescriptionFieldNames = propertyDescriptionFieldNames;
    }

    @Override
    public String getId(T definition) {
        return getDefinitionId( definition );
    }

    @Override
    public Object getNameProperty(T definition) {
        String namePropId = getPropertyId( namePropertyClass );
        Set<?> properties = getProperties( definition );
        if ( null != properties && !properties.isEmpty() ) {
            for ( Object property : properties ) {
                String pId = getPropertyId( property.getClass() );
                if ( pId.equals( namePropId) ) {
                    return property;
                }
            }
        }

        return null;
    }

    @Override
    public String getCategory(T definition) {
        Class<?> type = definition.getClass();
        try {
            return getFieldValue( definition, propertyCategoryFieldNames.get( type ) );
        } catch ( IllegalAccessException e ) {
            LOG.error("Error obtaining category for Definition with id " + getId( definition ));
        }

        return null;
    }

    @Override
    public String getTitle(T definition) {
        Class<?> type = definition.getClass();
        try {
            return getFieldValue( definition, propertyTitleFieldNames.get( type ) );
        } catch ( IllegalAccessException e ) {
            LOG.error("Error obtaining title for Definition with id " + getId( definition ));
        }

        return null;
    }

    @Override
    public String getDescription(T definition) {
        Class<?> type = definition.getClass();
        try {
            return getFieldValue( definition, propertyDescriptionFieldNames.get( type ) );
        } catch ( IllegalAccessException e ) {
            LOG.error("Error obtaining description for Definition with id " + getId( definition ));
        }

        return null;
    }

    @Override
    public Set<String> getLabels(T definition) {
        Class<?> type = definition.getClass();
        try {
            return getFieldValue( definition, propertyLabelsFieldNames.get( type ) );
        } catch ( IllegalAccessException e ) {
            LOG.error("Error obtaining labels for Definition with id " + getId( definition ));
        }

        return null;
    }

    @Override
    public Set<?> getPropertySets(T definition) {
        Class<?> type = definition.getClass();
        Set<String> fields = propertySetsFieldNames.get( type );
        try {
            return getFieldValues( definition, fields );
        } catch ( IllegalAccessException e ) {
            LOG.error("Error obtaining property sets for Definition with id " + getId( definition ));
        }

        return null;
    }

    @Override
    public Set<?> getProperties(T definition) {
        Class<?> type = definition.getClass();
        Set<String> fields = propertiesFieldNames.get( type );
        try {
            return getFieldValues( definition, fields );
        } catch ( IllegalAccessException e ) {
            LOG.error("Error obtaining properties for Definition with id " + getId( definition ));
        }

        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends Element> getGraphElement(T definition) {
        Class<?> type = definition.getClass();
        return propertyGraphElementFieldNames.get( type );
    }

    @Override
    public String getElementFactory(T definition) {
        Class<?> type = definition.getClass();
        return propertyElementFactoryFieldNames.get( type );
    }

    @Override
    public boolean accepts(Class<?> type) {
        return null != propertyGraphElementFieldNames && propertyGraphElementFieldNames.containsKey( type );
    }

}
