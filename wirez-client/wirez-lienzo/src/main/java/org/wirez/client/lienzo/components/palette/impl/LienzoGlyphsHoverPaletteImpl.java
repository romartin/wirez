package org.wirez.client.lienzo.components.palette.impl;

import org.wirez.client.lienzo.components.palette.AbstractLienzoGlyphItemsPalette;
import org.wirez.client.lienzo.components.palette.LienzoGlyphsHoverPalette;
import org.wirez.client.lienzo.components.palette.view.LienzoHoverPaletteView;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.components.glyph.DefinitionGlyphTooltip;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class LienzoGlyphsHoverPaletteImpl
        extends AbstractLienzoGlyphItemsPalette<LienzoHoverPaletteView>
        implements LienzoGlyphsHoverPalette {

    protected LienzoGlyphsHoverPaletteImpl() {
        this( null, null, null );
    }

    @Inject
    public LienzoGlyphsHoverPaletteImpl(final ShapeManager shapeManager,
                                        final LienzoHoverPaletteView view,
                                        final DefinitionGlyphTooltip definitionGlyphTooltip ) {
        super( shapeManager, definitionGlyphTooltip, view );
    }

    @PostConstruct
    public void init() {
        super.doInit();
    }

}
