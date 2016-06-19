package org.wirez.core.definition.shape;

public abstract class AbstractGlyphProxy<W> implements GlyphProxy<W> {

    @Override
    public String getGlyphDefinitionId( final String definitionId ) {
        return definitionId;
    }

    @Override
    public String getGlyphDescription( final W element ) {
        return null;
    }

}
