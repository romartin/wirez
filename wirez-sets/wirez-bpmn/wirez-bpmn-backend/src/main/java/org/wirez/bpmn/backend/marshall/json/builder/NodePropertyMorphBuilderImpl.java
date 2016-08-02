package org.wirez.bpmn.backend.marshall.json.builder;

import org.wirez.core.definition.adapter.DefinitionAdapter;
import org.wirez.core.definition.adapter.PropertyAdapter;
import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.definition.adapter.binding.HasInheritance;
import org.wirez.core.definition.morph.BindablePropertyMorphDefinition;
import org.wirez.core.definition.morph.MorphProperty;
import org.wirez.core.definition.property.PropertyType;
import org.wirez.core.definition.util.DefinitionUtils;

import java.util.Collection;
import java.util.LinkedList;

public class NodePropertyMorphBuilderImpl extends NodeBuilderImpl {

    private final BindablePropertyMorphDefinition propertyMorphDefinition;

    public NodePropertyMorphBuilderImpl(Class<?> baseDefinitionClass,
                                        BindablePropertyMorphDefinition propertyMorphDefinition) {
        super(baseDefinitionClass);
        this.propertyMorphDefinition = propertyMorphDefinition;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected String getDefinitionToBuild( BuilderContext context ) {
        
        final String defaultDefinitionId = propertyMorphDefinition.getDefault();

        final String definitionId = BindableAdapterUtils.getDefinitionId( definitionClass );

        Collection<MorphProperty> mps = new LinkedList<MorphProperty>();

        // TODO: Iterate over all morph properties.

        final Iterable<MorphProperty> mps1 = propertyMorphDefinition.getMorphProperties( definitionId );
        if ( mps1 != null && mps1.iterator().hasNext() ) {
            mps.add( mps1.iterator().next() );
        }

        DefinitionAdapter<Object> definitionAdapter = context.getDefinitionManager().adapters().registry().getDefinitionAdapter( definitionClass );
        String baseId = ( (HasInheritance) definitionAdapter).getBaseType( definitionClass );
        if ( null != baseId ) {
            final Iterable<MorphProperty> mps2 = propertyMorphDefinition.getMorphProperties( baseId );
            if ( mps2 != null && mps2.iterator().hasNext() ) {
                mps.add( mps2.iterator().next() );
            }
        }

        final MorphProperty morphProperty = mps.iterator().next();
        
        final Object defaultDefinition = context.getFactoryManager().newDefinition( defaultDefinitionId );
        
        final DefinitionUtils definitionUtils = new DefinitionUtils( context.getDefinitionManager(), context.getFactoryManager() );

        final Object mp = definitionUtils.getProperty( defaultDefinition, morphProperty.getProperty() );

        final PropertyType propertyType = context.getDefinitionManager().adapters().forProperty().getType( mp );

        final String oryxId = context.getOryxManager().getMappingsManager().getOryxPropertyId( mp.getClass() );

        final String pRawValue = properties.get( oryxId );

        final Object pValue = context.getOryxManager().getPropertyManager().parse( mp, propertyType, pRawValue );
        
        return morphProperty.getMorphTarget( pValue );
        
    }
    
}
