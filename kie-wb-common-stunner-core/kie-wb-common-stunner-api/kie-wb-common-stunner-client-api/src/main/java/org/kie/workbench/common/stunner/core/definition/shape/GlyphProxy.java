package org.kie.workbench.common.stunner.core.definition.shape;

public interface GlyphProxy<W> {

    String getGlyphDefinitionId( String definitionId );

    String getGlyphDescription( W element );
    
}
