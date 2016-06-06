package org.wirez.client.widgets.palette;

import org.wirez.core.client.shape.view.ShapeGlyph;

public final class PaletteItemImpl implements PaletteItem {
    
    private final String defId;
    private final String description;
    private final ShapeGlyph glyph;

    public PaletteItemImpl(final String defId, 
                           final String description,
                           final ShapeGlyph glyph) {
        this.defId = defId;
        this.description = description;
        this.glyph = glyph;
    }

    @Override
    public String getDefinitionId() {
        return defId;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public ShapeGlyph getGlyph() {
        return glyph;
    }

}
