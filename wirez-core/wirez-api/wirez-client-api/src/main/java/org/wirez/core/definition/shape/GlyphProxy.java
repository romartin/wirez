package org.wirez.core.definition.shape;

public interface GlyphProxy<W> {

    String getGlyphDefinitionId( String definitionId );

    String getGlyphDescription( W element );
    
}
