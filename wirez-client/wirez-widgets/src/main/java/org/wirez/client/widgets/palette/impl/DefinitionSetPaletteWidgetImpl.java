package org.wirez.client.widgets.palette.impl;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.ait.lienzo.shared.core.types.ColorName;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.wirez.client.lienzo.components.palette.LienzoDefinitionSetPalette;
import org.wirez.client.lienzo.components.palette.LienzoGlyphItemsPalette;
import org.wirez.client.lienzo.components.palette.LienzoGlyphsHoverPalette;
import org.wirez.client.lienzo.components.palette.LienzoPalette;
import org.wirez.client.widgets.palette.AbstractPaletteWidget;
import org.wirez.client.widgets.palette.DefinitionSetPaletteWidget;
import org.wirez.client.widgets.palette.view.PaletteWidgetFloatingView;
import org.wirez.client.widgets.palette.view.PaletteWidgetViewImpl;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.components.glyph.DefinitionGlyphTooltip;
import org.wirez.core.client.components.glyph.GlyphTooltip;
import org.wirez.core.client.components.glyph.ShapeGlyphDragHandler;
import org.wirez.core.client.components.palette.ClientPaletteUtils;
import org.wirez.core.client.components.palette.model.GlyphPaletteItem;
import org.wirez.core.client.components.palette.model.HasPaletteItems;
import org.wirez.core.client.components.palette.model.definition.DefinitionSetPalette;
import org.wirez.core.client.service.ClientFactoryServices;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;

/**
 * Palette Widget that binds with a DefinitionSetPalette definition.
 *
 * It has two views, the main ( the view instance returned by calling #asWidget() ) view and
 * the floating view. Both views have a canvas on which a Lienzo palette
 * implementation can be attached. The floating view is at same time attached to the GWT RootPanel, so can be
 * displayed outside the current widget ( or workbench screen for upper layers ) area.
 *
 * Provides two visual modes:
 *
 * - Collapsed ( grouping strategy by morph base )
 *      The main view displays a lienzo vertical palette with all the morphing bases and the floating view displays
 *      a lienzo horizontal palette with all shape glyphs for the given definitions for this morph base group.
 *
 * - Expanded ( grouping strategy by category (
 *      The main view displays a lienzo vertical palette with all categories for the defintion set and the floating view displays
 *      a lienzo category palette, as it's able to draw a palette that binds to a DefinitionPaletteCategory.
 *
 * TODO: Call to floatingView.destroy() to detach the floating panel from the root one.
 */
@Dependent
public class DefinitionSetPaletteWidgetImpl
        extends AbstractPaletteWidget<DefinitionSetPalette, PaletteWidgetViewImpl>
        implements DefinitionSetPaletteWidget {

    private static final int COLLAPSED_ICON_SIZE = 40;
    private static final int COLLAPSED_PADDING = 15;
    private static final int EXPANDED_ICON_SIZE = 50;
    private static final int EXPANDED_PADDING = 30;

    SyncBeanManager beanManager;

    PaletteWidgetFloatingView floatingView;

    private LienzoDefinitionSetPalette mainPalette;
    private LienzoGlyphsHoverPalette glyphsFloatingPalette;

    protected DefinitionSetPaletteWidgetImpl() {
        this( null, null, null, null, null );
    }

    @Inject
    public DefinitionSetPaletteWidgetImpl(final ShapeManager shapeManager,
                                          final ClientFactoryServices clientFactoryServices,
                                          final PaletteWidgetViewImpl view,
                                          final SyncBeanManager beanManager,
                                          final PaletteWidgetFloatingView floatingView) {
        super(shapeManager, clientFactoryServices, view);
        this.beanManager = beanManager;
        this.floatingView = floatingView;
    }

    @PostConstruct
    public void init() {

        this.mainPalette = beanManager.lookupBean( LienzoDefinitionSetPalette.class ).newInstance();
        this.glyphsFloatingPalette = beanManager.lookupBean( LienzoGlyphsHoverPalette.class ).newInstance();

        view.setPresenter( this );
        view.setBackgroundColor( ColorName.LIGHTGREY.getColorString() );
        floatingView.setPresenter( this );

        mainPalette.setExpandable( false );
        mainPalette.setLayout( LienzoPalette.Layout.VERTICAL );
        updateMainPaletteIconsSize();
        mainPalette.onShowGlyTooltip( mainPaletteGlyphTooltipCallback );

        glyphsFloatingPalette.setExpandable( false );
        glyphsFloatingPalette.setIconSize( COLLAPSED_ICON_SIZE );
        glyphsFloatingPalette.setPadding( COLLAPSED_PADDING );
        glyphsFloatingPalette.setLayout( LienzoPalette.Layout.HORIZONTAL );
        glyphsFloatingPalette.onClose( floatingPaletteCloseCallback );
        glyphsFloatingPalette.onShowGlyTooltip( floatingPaletteGlyphTooltipCallback );

    }

    private final LienzoGlyphItemsPalette.GlyphTooltipCallback floatingPaletteGlyphTooltipCallback =
            (glyphTooltip, item, mouseX, mouseY, itemX, itemY) -> {

                final int[] mainPaletteSize = getMainPaletteSize();

                final double pX = mainPaletteSize[0] + itemX - ( getIconSize() / 2 );
                final double pY = itemY + floatingView.getAbsoluteTop() + getIconSize() + getPadding();

                glyphTooltip.showTooltip( item.getDefinitionId(), pX, pY, GlyphTooltip.Direction.NORTH );

                return false;

            };

    private final LienzoGlyphItemsPalette.GlyphTooltipCallback mainPaletteGlyphTooltipCallback =
            (glyphTooltip, item, mouseX, mouseY, itemX, itemY) -> {

                final int[] mainPaletteSize = getMainPaletteSize();

                final double pX = mainPaletteSize[0] + itemX - ( getIconSize() / 2 );
                final double pY = itemY + view.getAbsoluteTop() + ( getIconSize() / 2 );

                glyphTooltip.showTooltip( item.getDefinitionId(), pX, pY, GlyphTooltip.Direction.WEST );

                return false;

            };

    private final CloseCallback floatingPaletteCloseCallback = new CloseCallback() {

        @Override
        public boolean onClose() {

            floatingView.hidePopup();
            return true;

        }

    };

    @Override
    protected void beforeBind() {
        super.beforeBind();

        floatingView.clear();

    }

    @Override
    @SuppressWarnings("unchecked")
    protected DefinitionSetPaletteWidgetImpl bind() {

        mainPalette.bind( paletteDefinition );

        final int[] mainPaletteSize = getMainPaletteSize();

        view.show( mainPalette.getView(), mainPaletteSize[0], mainPaletteSize[1] );

        mainPalette.onItemMouseDown((pos, mouseX, mouseY, itemX, itemY) -> {

            final GlyphPaletteItem item = getMainPaletteItem( pos );

            view.showDragProxy( item.getDefinitionId(), mouseX, mouseY );

            return !hasPaletteItems( item );

        });

        mainPalette.onItemHover( ( pos, x, y, itemX, itemY ) ->
                showFloatingPalette( pos, x, y, itemX, itemY, paletteDefinition ) );

        return this;

    }

    @Override
    public void unbind() {

        super.unbind();

        floatingView.clear();

    }

    @SuppressWarnings("unchecked")
    private boolean showFloatingPalette( final int pos,
                                         final double x,
                                         final double y,
                                         final double itemX,
                                         final double itemY,
                                         final HasPaletteItems<? extends GlyphPaletteItem> palette ) {

        final GlyphPaletteItem item = getMainPaletteItem( pos );

        if ( hasPaletteItems( item ) ) {

            final HasPaletteItems<GlyphPaletteItem> hasPaletteItems =
                    (HasPaletteItems<GlyphPaletteItem>) item;

            glyphsFloatingPalette.bind( hasPaletteItems );

            final int[] mainPaletteSize = getMainPaletteSize();

            final double pX = mainPaletteSize[0] - ( getPadding() / 2 );
            final double pY = itemY + view.getAbsoluteTop() - getPadding();

            floatingView
                    .setX( pX )
                    .setY( pY );

            // TODO: Let the floating panel use same size as the palette view's one.
            floatingView.show( glyphsFloatingPalette.getView() );

            glyphsFloatingPalette.onItemMouseDown( (pos1, mouseX, mouseY, itemX1, itemY1) -> {

                final GlyphPaletteItem item1 = getPaletteItem( pos1, hasPaletteItems );

                if ( !hasPaletteItems( item1 ) ) {

                    view.showDragProxy( item1.getDefinitionId(), mouseX, mouseY );

                }

                floatingView.hidePopup();

                return true;

            } );

            floatingView.showPopup();

            return false;

        }

        return true;

    }

    @Override
    protected void  doDestroy() {

        super.doDestroy();

        floatingView.destroy();

    }

    @Override
    public double getIconSize() {
        return expanded ? EXPANDED_ICON_SIZE : COLLAPSED_ICON_SIZE;
    }

    @Override
    public double getPadding() {
        return expanded ? EXPANDED_PADDING : COLLAPSED_PADDING ;
    }

    private int[] getMainPaletteSize() {

        double width = 0;
        double height = 0;

        if ( null != paletteDefinition ) {

            final List<? extends GlyphPaletteItem> items = getMainPaletteItems();
            final int itemsSize = null != items ? items.size() : 0;

            final double[] mainPaletteSize =
                    ClientPaletteUtils.computeSizeForVerticalLayout( itemsSize, _getIconSize(), _getPadding(), 0 );

            width = mainPaletteSize[0];
            height = mainPaletteSize[1];
        }

        return new int[] { (int) width, (int) height };
    }

    private boolean hasPaletteItems(final GlyphPaletteItem item ) {
        return item instanceof HasPaletteItems;
    }

    protected void doExpandCollapse() {

        // Apply the given icon size for each visual mode.
        updateMainPaletteIconsSize();

        // Destroy the floating view as it will be reattached to other palette.
        floatingView.destroy();

        // Rebind views with collapsed/expanded the given palette definitions.
        bind();

        // Resize the main palette canvas view's size.
        updateMainPaletteViewSize();

    }

    private void updateMainPaletteIconsSize() {

        mainPalette.setIconSize( _getIconSize() );
        mainPalette.setPadding( _getPadding() );

    }

    private void updateMainPaletteViewSize() {

        final int[] size = getMainPaletteSize();
        view.setPaletteSize( size[0], size[1] );

    }

    private List<GlyphPaletteItem> getMainPaletteItems() {
        return mainPalette.getItems();
    }

    private GlyphPaletteItem getMainPaletteItem( final int index ) {
        return mainPalette.getItem( index );
    }

    private GlyphPaletteItem getPaletteItem(final int index, final HasPaletteItems<? extends GlyphPaletteItem> paletteDefinition ) {

        return paletteDefinition.getItems().get( index );

    }

    private int _getIconSize() {

        return (int) getIconSize();

    }

    private int _getPadding() {

        return (int) getPadding();

    }

}
