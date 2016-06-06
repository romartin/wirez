package org.wirez.bpmn.backend.marshall.json.builder;

import org.wirez.core.definition.adapter.PropertyAdapter;
import org.wirez.core.definition.adapter.InheritanceMorphAdapter;
import org.wirez.core.definition.adapter.MorphProperty;
import org.wirez.core.definition.property.PropertyType;
import org.wirez.core.definition.util.DefinitionUtils;

public class NodeInheritanceMorphBuilderImpl extends NodeBuilderImpl {

    private final InheritanceMorphAdapter morphAdapter;
    
    public NodeInheritanceMorphBuilderImpl(Class<?> baseDefinitionClass,
                                           InheritanceMorphAdapter morphAdapter) {
        super(baseDefinitionClass);
        this.morphAdapter = morphAdapter;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected String getDefinitionToBuild( BuilderContext context ) {
        
        final MorphProperty morphProperty = morphAdapter.getMorphProperty( definitionClass );

        final String defaultDefinitionId = morphAdapter.getDefaultDefinition();
        
        final Object defaultDefinition = context.getFactoryManager().newDomainObject( defaultDefinitionId );
        
        final DefinitionUtils definitionUtils = new DefinitionUtils( context.getDefinitionManager() );

        final Object mp = definitionUtils.getProperty( defaultDefinition, morphProperty.getProperty() );

        final PropertyAdapter<Object, ?> propertyAdapter = context.getDefinitionManager().getPropertyAdapter( mp.getClass() );
        
        final PropertyType propertyType = propertyAdapter.getType( mp );

        final String oryxId = context.getOryxManager().getMappingsManager().getOryxPropertyId( mp.getClass() );

        final String pRawValue = properties.get( oryxId );

        final Object pValue = context.getOryxManager().getPropertyManager().parse( mp, propertyType, pRawValue );
        
        return morphProperty.getMorphTarget( pValue );
        
    }
    
}
