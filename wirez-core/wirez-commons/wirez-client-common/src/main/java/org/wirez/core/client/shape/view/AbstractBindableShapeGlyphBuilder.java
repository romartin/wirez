package org.wirez.core.client.shape.view;

import org.wirez.core.api.FactoryManager;
import org.wirez.core.client.shape.MutableShape;
import org.wirez.core.client.shape.MutationContext;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.factory.ShapeFactory;
import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.definition.shape.AbstractBindableGlyphProxy;
import org.wirez.core.definition.shape.GlyphProxy;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.util.UUID;

public abstract class AbstractBindableShapeGlyphBuilder<G> extends AbstractShapeGlyphBuilder<G> {

    @SuppressWarnings("unchecked")
    public ShapeGlyphBuilder<G> glyphProxy( final GlyphProxy<?> glyphProxy,
                                            final Class<?> type ) {

        if ( glyphProxy instanceof AbstractBindableGlyphProxy ) {

            final AbstractBindableGlyphProxy bindableGlyphProxy = (AbstractBindableGlyphProxy) glyphProxy;
            this.id = bindableGlyphProxy.getGlyphDefinitionId( type );

            return this;
        } else {

            final String _id = BindableAdapterUtils.getDefinitionId( type );
            return super.glyphProxy( glyphProxy, _id );
        }

    }


}
