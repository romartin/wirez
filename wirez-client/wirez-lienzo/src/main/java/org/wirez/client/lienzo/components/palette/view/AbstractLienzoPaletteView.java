package org.wirez.client.lienzo.components.palette.view;

import com.ait.lienzo.client.core.animation.AnimationProperties;
import com.ait.lienzo.client.core.animation.AnimationProperty;
import com.ait.lienzo.client.core.animation.AnimationTweener;
import com.ait.lienzo.client.core.event.NodeMouseEnterEvent;
import com.ait.lienzo.client.core.event.NodeMouseEnterHandler;
import com.ait.lienzo.client.core.event.NodeMouseExitEvent;
import com.ait.lienzo.client.core.event.NodeMouseExitHandler;
import com.ait.lienzo.client.core.shape.*;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.shared.core.types.ArrowType;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.tooling.nativetools.client.event.HandlerRegistrationManager;
import org.wirez.client.lienzo.components.palette.AbstractLienzoPalette;
import org.wirez.client.lienzo.components.palette.view.element.LienzoGlyphPaletteItemView;
import org.wirez.client.lienzo.components.palette.view.element.LienzoPaletteElementView;
import org.wirez.core.client.components.palette.view.AbstractPaletteView;
import org.wirez.core.client.components.palette.view.PaletteGrid;
import org.wirez.lienzo.palette.AbstractPalette;
import org.wirez.lienzo.palette.HoverPalette;

public abstract class AbstractLienzoPaletteView<V extends LienzoPaletteView>
        extends AbstractPaletteView<V, Layer, LienzoPaletteElementView>
        implements LienzoPaletteView<V, LienzoPaletteElementView> {

    protected double animationDuration = 500;
    protected PaletteGrid grid;
    protected AbstractLienzoPalette presenter;
    protected AbstractPalette<? extends AbstractPalette> palette;
    protected IPrimitive<?> colExpButton;
    protected final HandlerRegistrationManager handlerRegistrationManager = new HandlerRegistrationManager();

    protected abstract AbstractPalette<? extends AbstractPalette> buildPalette();

    protected AbstractPalette<? extends AbstractPalette> getPalette() {

        if ( null == palette ) {

            this.palette = buildPalette();
            initPaletteCallbacks();

        }

        return palette;
    }

    public void setPresenter( final AbstractLienzoPalette presenter ) {
        this.presenter = presenter;
    }

    @Override
    public void setGrid( final PaletteGrid grid) {
        this.grid = grid;
    }

    @Override
    protected void doClear() {

        if ( getPalette().isVisible() && getPalette().getAlpha() > 0 ) {

            getPalette().setAlpha( 0 );

            draw();

        }

    }

    protected boolean isExpandable() {
        return presenter.isExpandable();
    }

    @Override
    @SuppressWarnings("unchecked")
    public V attach( final Layer layer) {

        if ( null == colExpButton && isExpandable() ) {

            colExpButton = createExpandCollapseButton();

            layer.add( colExpButton );

        }

        layer.add( getPalette() );

        return (V) this;

    }

    public void draw() {

        getPalette().redraw();

    }

    @SuppressWarnings("unchecked")
    public V show() {

        if ( null == getPalette().getParent() ) {

            throw new IllegalStateException( "Palette must be attached to a layer before calling #show." );

        }

        if ( !items.isEmpty() ) {

            final AbstractPalette.Item[] primitives = new AbstractPalette.Item[ items.size() ];

            int _x = 0;
            for ( final LienzoPaletteElementView paletteItemView : items ) {

                final AbstractPalette.Item i = buildLienzoPaletteItem( paletteItemView );

                primitives[ _x ] = i;
                _x++;

            }

            double paletteStartY = 0;

            if ( null != colExpButton && isExpandable() ) {

                colExpButton.setX( x + grid.getPadding() );
                colExpButton.setY( y );

                paletteStartY = colExpButton.getBoundingBox().getHeight() + grid.getPadding();

            }

            getPalette().setX( x );

            getPalette().setY( paletteStartY + y );

            getPalette().setRows( grid.getRows() );
            getPalette().setColumns( grid.getColumns() );
            getPalette().setIconSize( grid.getIconSize() );
            getPalette().setPadding( grid.getPadding() );
            getPalette().build( primitives );
            getPalette().setAlpha( 0 );
            getPalette().animate(   AnimationTweener.LINEAR,
                                    AnimationProperties.toPropertyList(AnimationProperty.Properties.ALPHA(1)),
                                    animationDuration );

            draw();

        } else {

            clear();

        }

        return (V) this;

    }

    protected AbstractPalette.Item buildLienzoPaletteItem( final LienzoPaletteElementView paletteItemView ) {

        AbstractPalette.ItemDecorator decorator = null;

        if ( paletteItemView instanceof LienzoGlyphPaletteItemView ) {

            final LienzoGlyphPaletteItemView.Decorator d = ((LienzoGlyphPaletteItemView) paletteItemView).getDecorator();

            if ( null != d ) {

                decorator = AbstractPalette.ItemDecorator.DEFAULT;
            }
        }

        return new AbstractPalette.Item( paletteItemView.getView(), decorator );

    }


    @Override
    public double getWidth() {
        return presenter.computePaletteSize()[0];
    }

    @Override
    public double getHeight() {
        return presenter.computePaletteSize()[1];
    }

    @Override
    public V clear() {

        removeExpandCollapseButton();

        return super.clear();

    }

    @Override
    public void destroy() {

        removeExpandCollapseButton();

        if ( null != palette ) {

            palette.setItemCallback( null );
            palette.clear();
            palette.removeFromParent();
            palette = null;

        }

    }

    protected IPrimitive<?> createExpandCollapseButton() {

        final boolean isExpanded = presenter.isExpanded();

        final double w = grid.getIconSize();
        final double h = grid.getIconSize() / 1.5;

        final Rectangle rectangle = new Rectangle( w, h )
                .setFillAlpha( 0.01 )
                .setStrokeWidth( 0 )
                .setStrokeAlpha( 0 );

        final Arrow expandArrow =
                new Arrow( new Point2D( 0, h / 2), new Point2D( w, h / 2), h / 2, h, 45, 90, ArrowType.AT_END_TAPERED )
                .setFillColor( ColorName.LIGHTGREY )
                .setFillAlpha( 0.5 )
                .setVisible( !isExpanded );

        final Arrow collapseArrow =
                new Arrow( new Point2D( w, h / 2), new Point2D( 0, h / 2), h / 2, h, 45, 90, ArrowType.AT_END_TAPERED )
                        .setFillColor( ColorName.LIGHTGREY )
                        .setFillAlpha( 0.5 )
                        .setVisible( isExpanded );


        handlerRegistrationManager.register(

                rectangle.addNodeMouseClickHandler(nodeMouseClickEvent -> {

                    if ( presenter.isExpanded() ) {

                        expandArrow.setVisible( true );
                        collapseArrow.setVisible( false );

                        presenter.collapse();

                    } else {

                        expandArrow.setVisible( false );
                        collapseArrow.setVisible( true );

                        presenter.expand();

                    }
                })

        );



        handlerRegistrationManager.register(

                rectangle.addNodeMouseEnterHandler( nodeMouseEnterEvent -> {

                    stopHoverTimeoutPalette();

                    if ( presenter.isExpanded() ) {

                        animate( collapseArrow, ColorName.DARKGREY.getColorString(), 1, 1 );

                    } else {

                        animate( expandArrow, ColorName.DARKGREY.getColorString(), 1, 1 );


                    }
                } )

        );

        handlerRegistrationManager.register(

                rectangle.addNodeMouseExitHandler( nodeMouseExitEvent -> {

                    startHoverTimeoutPalette();

                    if ( presenter.isExpanded() ) {

                        animate( collapseArrow, ColorName.LIGHTGREY.getColorString(), 0.5, 0.5 );

                    } else {

                        animate( expandArrow, ColorName.LIGHTGREY.getColorString(), 0.5, 0.5 );


                    }

                } )

        );

        return new Group()
                .add( expandArrow )
                .add( collapseArrow )
                .add( rectangle.moveToTop() );
    }

    private void animate( final IPrimitive<?> primitive,
                          final String fillColor,
                          final double fillAlpha,
                          final double strokeAlpha) {

        primitive.animate(
                AnimationTweener.LINEAR,
                AnimationProperties.toPropertyList(
                        AnimationProperty.Properties.FILL_COLOR( fillColor ),
                        AnimationProperty.Properties.FILL_ALPHA( fillAlpha ),
                        AnimationProperty.Properties.STROKE_ALPHA( strokeAlpha )
                ),
                200
        );

    }

    protected void removeExpandCollapseButton() {

        handlerRegistrationManager.removeHandler();

        if ( null != colExpButton ) {

            colExpButton.removeFromParent();
            colExpButton = null;

        }

    }

    protected void initPaletteCallbacks() {

        getPalette().setItemCallback( new AbstractPalette.Callback() {

            @Override
            public void onItemHover(final int index,
                                    final double mouseX,
                                    final double mouseY,
                                    final double itemX,
                                    final double itemY) {

                if ( null != presenter ) {

                    presenter.onItemHover( index, mouseX, mouseY, itemX, itemY  );

                }

            }

            @Override
            public void onItemOut(final int index) {

                if ( null != presenter ) {

                    presenter.onItemOut( index );

                }

            }

            @Override
            public void onItemMouseDown(final int index,
                                        final double mouseX,
                                        final double mouseY,
                                        final double itemX,
                                        final double itemY) {

                if ( null != presenter ) {

                    presenter.onItemMouseDown( index, mouseX, mouseY, itemX, itemY  );

                }

            }

            @Override
            public void onItemClick(final int index,
                                    final double mouseX,
                                    final double mouseY,
                                    final double itemX,
                                    final double itemY) {

                if ( null != presenter ) {

                    presenter.onItemClick( index, mouseX, mouseY, itemX, itemY  );

                }

            }

        } );

    }

    protected void stopHoverTimeoutPalette() {

        if ( palette instanceof HoverPalette ) {

            final HoverPalette hoverPalette = (HoverPalette) palette;
            hoverPalette.stopTimeout();

        }

    }

    protected void startHoverTimeoutPalette() {

        if ( palette instanceof HoverPalette ) {

            final HoverPalette hoverPalette = (HoverPalette) palette;
            hoverPalette.startTimeout();

        }

    }

}
