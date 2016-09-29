package org.kie.workbench.common.stunner.core.client;

import org.kie.workbench.common.stunner.core.api.DefinitionManager;
import org.kie.workbench.common.stunner.core.definition.adapter.binding.BindableAdapterUtils;
import org.kie.workbench.common.stunner.core.client.shape.Shape;
import org.kie.workbench.common.stunner.core.client.shape.factory.ShapeFactory;

public abstract class AbstractBindableShapeSet implements ShapeSet<ShapeFactory<?, ?, ? extends Shape>> {

    protected abstract Class<?> getDefinitionSetClass();
    
    protected DefinitionManager definitionManager;

    protected String description;

    protected AbstractBindableShapeSet() {

    }

    public AbstractBindableShapeSet(final DefinitionManager definitionManager) {
        this.definitionManager = definitionManager;
    }

    public void doInit() {
        
        final Object defSet = definitionManager.definitionSets().getDefinitionSetById( getDefinitionSetId() );
        this.description = definitionManager.adapters().forDefinitionSet().getDescription( defSet );
        
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
