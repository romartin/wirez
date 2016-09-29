package org.kie.workbench.common.stunner.client.lienzo.components.palette.impl;

import org.kie.workbench.common.stunner.client.lienzo.components.palette.AbstractLienzoGlyphItemsPalette;
import org.kie.workbench.common.stunner.client.lienzo.components.palette.view.element.*;
import org.kie.workbench.common.stunner.client.lienzo.components.palette.LienzoDefinitionSetFlatPalette;
import org.kie.workbench.common.stunner.client.lienzo.components.palette.view.LienzoPaletteViewImpl;
import org.kie.workbench.common.stunner.core.client.ShapeManager;
import org.kie.workbench.common.stunner.core.client.components.glyph.DefinitionGlyphTooltip;
import org.kie.workbench.common.stunner.core.client.components.palette.model.GlyphPaletteItem;
import org.kie.workbench.common.stunner.core.client.components.palette.model.HasPaletteItems;
import org.kie.workbench.common.stunner.core.client.components.palette.model.definition.DefinitionPaletteCategory;
import org.kie.workbench.common.stunner.core.client.components.palette.model.definition.DefinitionPaletteItem;
import org.kie.workbench.common.stunner.core.client.components.palette.model.definition.DefinitionSetPalette;
import org.kie.workbench.common.stunner.core.client.components.palette.view.PaletteGrid;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;

@Dependent
public class LienzoDefinitionSetFlatPaletteImpl
        extends AbstractLienzoGlyphItemsPalette<HasPaletteItems<? extends GlyphPaletteItem>, LienzoPaletteViewImpl>
        implements LienzoDefinitionSetFlatPalette {

    private String lastCategory = null;
    private final List<GlyphPaletteItem> items = new LinkedList<>();

    protected LienzoDefinitionSetFlatPaletteImpl() {
        this( null, null, null );
    }

    @Inject
    public LienzoDefinitionSetFlatPaletteImpl( final ShapeManager shapeManager,
                                               final LienzoPaletteViewImpl view,
                                               final DefinitionGlyphTooltip definitionGlyphTooltip ) {
        super( shapeManager, definitionGlyphTooltip, view );
    }

    @PostConstruct
    public void init() {
        super.doInit();
    }

    @Override
    protected void doBind() {

        items.clear();

        final DefinitionSetPalette definitionSetPalette = (DefinitionSetPalette) paletteDefinition;

        final List<DefinitionPaletteCategory> categories = definitionSetPalette.getItems();

        if ( null != categories && !categories.isEmpty() ) {

            final PaletteGrid grid = getGrid();

            for ( final DefinitionPaletteCategory category : categories ) {

                final List<DefinitionPaletteItem> categoryItems = category.getItems();

                if ( null != categoryItems && !categoryItems.isEmpty() ) {

                    for ( final GlyphPaletteItem item : categoryItems ) {

                        addGlyphItemIntoView( item, grid );

                        items.add( item );

                    }

                    // TODO: addSeparatorIntoView( grid ); - Too much height as using static grid size.
                }

            }

        }


    }

    @Override
    public List<GlyphPaletteItem> getItems() {
        return items;
    }

    protected void addTextIntoView( final String text,
                                    final PaletteGrid grid ) {

        final LienzoTextPaletteElementView separatorPaletteTextView =
                new LienzoTextPaletteElementViewImpl( text, "Verdana", 10 );

        addElementIntoView( separatorPaletteTextView );
    }

    protected void addSeparatorIntoView( final PaletteGrid grid ) {

        final LienzoSeparatorPaletteElementView separatorPaletteElementView =
                new LienzoSeparatorPaletteElementViewImpl( grid.getIconSize(), grid.getIconSize() );

        addElementIntoView( separatorPaletteElementView );
    }

    protected void addElementIntoView( final LienzoPaletteElementView paletteElementView ) {

        itemViews.add( paletteElementView );
        view.add( paletteElementView );

    }

}
