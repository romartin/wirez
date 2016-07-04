package org.wirez.client.widgets.palette.impl;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.widget.LienzoPanel;
import org.wirez.client.lienzo.components.palette.LienzoGlyphsPalette;
import org.wirez.client.lienzo.components.palette.LienzoPalette;
import org.wirez.client.widgets.palette.AbstractPaletteWidget;
import org.wirez.client.widgets.palette.DefinitionsPaletteWidget;
import org.wirez.client.widgets.palette.view.PaletteWidgetViewImpl;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.components.glyph.ShapeGlyphDragHandler;
import org.wirez.core.client.components.palette.model.definition.DefinitionPaletteItem;
import org.wirez.core.client.components.palette.model.definition.DefinitionsPalette;
import org.wirez.core.client.service.ClientFactoryServices;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * Palette Widget that binds with a DefinitionsPalette definition..
 */
@Dependent
public class DefinitionsPaletteWidgetImpl
        extends AbstractPaletteWidget<DefinitionsPalette, PaletteWidgetViewImpl>
        implements DefinitionsPaletteWidget {

    private static final int ICON_SIZE = 50;
    private static final int PADDING = 10;

    LienzoGlyphsPalette lienzoGlyphsPalette;
    ShapeGlyphDragHandler<LienzoPanel, Group> shapeGlyphDragHandler;

    protected DefinitionsPaletteWidgetImpl() {
        this( null, null, null, null, null );
    }

    @Inject
    public DefinitionsPaletteWidgetImpl(final ShapeManager shapeManager,
                                        final ClientFactoryServices clientFactoryServices,
                                        final LienzoGlyphsPalette lienzoGlyphsPalette,
                                        final ShapeGlyphDragHandler<LienzoPanel, Group> shapeGlyphDragHandler,
                                        final PaletteWidgetViewImpl view ) {
        super( shapeManager, clientFactoryServices, view );
        this.lienzoGlyphsPalette = lienzoGlyphsPalette;
        this.shapeGlyphDragHandler = shapeGlyphDragHandler;
        this.view = view;
    }

    @PostConstruct
    public void init() {

        view.setPresenter( this );
        view.showEmptyView( true );

        lienzoGlyphsPalette.setIconSize( ICON_SIZE );
        lienzoGlyphsPalette.setPadding( PADDING );
        lienzoGlyphsPalette.setLayout( LienzoPalette.Layout.VERTICAL );

    }

    @Override
    protected DefinitionsPaletteWidgetImpl bind() {

        lienzoGlyphsPalette.bind( paletteDefinition );

        // TODO: View's panel size.
        view.show( lienzoGlyphsPalette.getView(), maxWidth, 900 );

        lienzoGlyphsPalette.onItemMouseDown((pos, mouseX, mouseY, itemX, itemY) -> {

            final DefinitionPaletteItem item = getItem( pos, paletteDefinition );

            view.showDragProxy( item.getDefinitionId(), mouseX, mouseY );

            return true;

        });

        return this;

    }

    @Override
    public double getIconSize() {
        return ICON_SIZE;
    }

    @Override
    public double getPadding() {
        return PADDING;
    }

    @Override
    protected void doExpandCollapse() {

        if ( expanded ) {

            lienzoGlyphsPalette.expand();

        } else  {

            lienzoGlyphsPalette.collapse();

        }

    }

    protected DefinitionPaletteItem getItem(final int index, final DefinitionsPalette paletteDefinition ) {
        return paletteDefinition.getItems().get( index );
    }

}
