package org.wirez.core.api.definition.adapter.binding;

import org.wirez.core.api.definition.adapter.DefinitionAdapter;
import org.wirez.core.api.graph.Element;

import java.util.Map;
import java.util.Set;

public abstract class BindableDefinitionAdapter<T> extends AbstractBindableAdapter<T> implements DefinitionAdapter<T> {

    protected abstract Map<Class, Set<String>> getPropertySetsFieldNames();
    protected abstract Map<Class, Set<String>> getPropertiesFieldNames();
    protected abstract Map<Class, Class> getPropertyGraphElementFieldNames();
    protected abstract Map<Class, String> getPropertyElementFactoryFieldNames();
    protected abstract Map<Class, String> getPropertyLabelsFieldNames();
    protected abstract Map<Class, String> getPropertyTitleFieldNames();
    protected abstract Map<Class, String> getPropertyCategoryFieldNames();
    protected abstract Map<Class, String> getPropertyDescriptionFieldNames();
    
    @Override
    public String getId(T pojo) {
        return getPojoId(pojo);
    }

    @Override
    public String getCategory(T pojo) {
        return getProxiedValue( pojo, getPropertyCategoryFieldNames().get(pojo.getClass()) );
    }

    @Override
    public String getTitle(T pojo) {
        return getProxiedValue( pojo, getPropertyTitleFieldNames().get(pojo.getClass()) );
    }

    @Override
    public String getDescription(T pojo) {
        return getProxiedValue( pojo, getPropertyDescriptionFieldNames().get(pojo.getClass()) );
    }

    @Override
    public Set<String> getLabels(T pojo) {
        return getProxiedValue( pojo, getPropertyLabelsFieldNames().get(pojo.getClass()) );
    }

    @Override
    public Set<?> getPropertySets(T pojo) {
        return getProxiedSet( pojo, getPropertySetsFieldNames().get(pojo.getClass()) );
    }

    @Override
    public Set<?> getProperties(T pojo) {
        return getProxiedSet( pojo, getPropertiesFieldNames().get(pojo.getClass()) );
    }

    @Override
    public Class<? extends Element> getGraphElement(T pojo) {
        return getPropertyGraphElementFieldNames().get(pojo.getClass());
    }

    @Override
    public String getElementFactory(T pojo) {
        return getPropertyElementFactoryFieldNames().get(pojo.getClass());
    }

    @Override
    public boolean accepts(Class<?> pojo) {
        return getPropertyCategoryFieldNames().containsKey(pojo);
    }
}
