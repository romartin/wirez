package org.kie.workbench.common.stunner.core.definition.shape;

import org.kie.workbench.common.stunner.core.definition.adapter.binding.BindableAdapterUtils;

public abstract class AbstractBindableGlyphProxy<W> extends AbstractGlyphProxy<W> {

    public String getGlyphDefinitionId( final Class<?> clazz ) {
        return getGlyphDefinitionId(BindableAdapterUtils.getDefinitionId( clazz ) );
    }

    @Override
    public String getGlyphDefinitionId( final String definitionId ) {
        return definitionId;
    }

    @Override
    public String getGlyphDescription( final W element ) {
        return null;
    }

}
