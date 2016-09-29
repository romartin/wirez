package org.kie.workbench.common.stunner.core.definition.shape;

public abstract class AbstractGlyphDef<W> implements GlyphDef<W> {

    @Override
    public String getGlyphDefinitionId( final String definitionId ) {
        return definitionId;
    }

    @Override
    public String getGlyphDescription( final W element ) {
        return null;
    }

}
