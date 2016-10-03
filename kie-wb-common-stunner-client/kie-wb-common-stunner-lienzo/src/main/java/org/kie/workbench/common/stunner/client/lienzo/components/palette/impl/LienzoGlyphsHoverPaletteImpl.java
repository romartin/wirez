package org.kie.workbench.common.stunner.client.lienzo.components.palette.impl;

import org.kie.workbench.common.stunner.client.lienzo.components.palette.AbstractLienzoGlyphItemsPalette;
import org.kie.workbench.common.stunner.client.lienzo.components.palette.view.LienzoHoverPaletteView;
import org.kie.workbench.common.stunner.client.lienzo.components.palette.LienzoGlyphsHoverPalette;
import org.kie.workbench.common.stunner.core.client.ShapeManager;
import org.kie.workbench.common.stunner.core.client.components.glyph.DefinitionGlyphTooltip;
import org.kie.workbench.common.stunner.core.client.components.palette.model.GlyphPaletteItem;
import org.kie.workbench.common.stunner.core.client.components.palette.model.HasPaletteItems;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class LienzoGlyphsHoverPaletteImpl
        extends AbstractLienzoGlyphItemsPalette<HasPaletteItems<? extends GlyphPaletteItem>, LienzoHoverPaletteView>
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
