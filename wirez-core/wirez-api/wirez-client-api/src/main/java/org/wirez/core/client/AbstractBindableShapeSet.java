package org.wirez.core.client;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.definition.adapter.DefinitionSetAdapter;
import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.factory.ShapeFactory;

public abstract class AbstractBindableShapeSet implements ShapeSet<ShapeFactory<?, ?, ? extends Shape>> {

    protected abstract Class<?> getDefinitionSetClass();
    
    protected DefinitionManager definitionManager;

    protected String description;
    
    public AbstractBindableShapeSet(final DefinitionManager definitionManager) {
        this.definitionManager = definitionManager;
    }

    public void doInit() {
        
        final Object defSet = definitionManager.getDefinitionSet( getDefinitionSetId() );
        final DefinitionSetAdapter<Object> definitionSetAdapter = definitionManager.getDefinitionSetAdapter( getDefinitionSetClass() );
        this.description = definitionSetAdapter.getDescription( defSet );
        
    }
    
    @Override
    public String getName() {
        return this.description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getDefinitionSetId() {
        return BindableAdapterUtils.getDefinitionSetId( getDefinitionSetClass() );
    }

}
