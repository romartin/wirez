package org.wirez.core.client.definition.adapter.binding;

import org.wirez.core.definition.adapter.binding.AbstractBindableDefinitionAdapter;
import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.definition.adapter.binding.BindableDefinitionAdapter;
import org.wirez.core.definition.util.DefinitionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

class ClientBindableDefinitionAdapter extends AbstractBindableDefinitionAdapter<Object>
        implements BindableDefinitionAdapter<Object> {

    ClientBindableDefinitionAdapter(final DefinitionUtils definitionUtils ) {
        super( definitionUtils );
    }

    public String getCategory(final Object pojo) {
        return getProxiedValue( pojo, getPropertyCategoryFieldNames().get( pojo.getClass() ) );
    }

    public String getTitle(final Object pojo) {
        return getProxiedValue( pojo, getPropertyTitleFieldNames().get( pojo.getClass() ) );
    }

    public String getDescription(final Object pojo) {
        return getProxiedValue( pojo, getPropertyDescriptionFieldNames().get( pojo.getClass() ) );
    }

    public Set<String> getLabels(final Object pojo) {
        final String fName = getPropertyLabelsFieldNames().get( pojo.getClass() );
        return getProxiedValue( pojo, fName );
    }

    public Set<?> getPropertySets(final Object pojo) {
        return getProxiedSet( pojo, getPropertySetsFieldNames().get( pojo.getClass() ) );
    }

    @Override
    protected Set<?> getBindProperties(final Object pojo) {
        return getProxiedSet( pojo, getPropertiesFieldNames().get( pojo.getClass()) );
    }

    private <T, R> R getProxiedValue(final T pojo, final String fieldName) {
        return ClientBindingUtils.getProxiedValue( pojo, fieldName );
    }

    private <T, R> Set<R> getProxiedSet(final T pojo, final Collection<String> fieldNames) {
        return ClientBindingUtils.getProxiedSet( pojo, fieldNames );
    }

    private <T, V> void setProxiedValue(final T pojo, final String fieldName, final V value) {
        ClientBindingUtils.setProxiedValue( pojo, fieldName, value );
    }
    
}
