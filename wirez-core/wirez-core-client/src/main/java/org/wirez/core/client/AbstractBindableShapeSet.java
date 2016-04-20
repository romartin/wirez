package org.wirez.core.client;

import org.wirez.core.api.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.factory.AbstractBindableShapeFactory;

public abstract class AbstractBindableShapeSet implements ShapeSet<AbstractBindableShapeFactory<?, ? extends Shape>> {

    protected abstract Class<?> getDefinitionSetClass();

    @Override
    public String getDefinitionSetId() {
        return BindableAdapterUtils.getDefinitionSetId( getDefinitionSetClass() );
    }

}
