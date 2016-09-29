package org.kie.workbench.common.stunner.core.client.shape.view;

import org.kie.workbench.common.stunner.core.definition.adapter.binding.BindableAdapterUtils;
import org.kie.workbench.common.stunner.core.definition.shape.AbstractBindableGlyphDef;
import org.kie.workbench.common.stunner.core.definition.shape.GlyphDef;

public abstract class AbstractBindableShapeGlyphBuilder<G> extends AbstractShapeGlyphBuilder<G> {

    @SuppressWarnings("unchecked")
    public ShapeGlyphBuilder<G> glyphProxy( final GlyphDef<?> glyphProxy,
                                            final Class<?> type ) {

        if ( glyphProxy instanceof AbstractBindableGlyphDef ) {

            final AbstractBindableGlyphDef bindableGlyphProxy = (AbstractBindableGlyphDef ) glyphProxy;
            this.id = bindableGlyphProxy.getGlyphDefinitionId( type );

            return this;
        } else {

            final String _id = BindableAdapterUtils.getDefinitionId( type );
            return super.glyphProxy( glyphProxy, _id );
        }

    }


}
