package org.wirez.client.lienzo.components.palette.glyph;

import com.ait.lienzo.client.core.animation.*;
import com.ait.lienzo.client.core.shape.IPrimitive;
import org.wirez.client.lienzo.LienzoLayer;
import org.wirez.core.client.canvas.Layer;
import org.wirez.core.client.components.palette.Palette;
import org.wirez.lienzo.palette.AbstractPalette;
import org.wirez.lienzo.palette.HoverPalette;

public class SimpleGlyphPaletteView implements SimpleGlyphPalette.View<SimpleGlyphPaletteView> {

    private final static int PALETTE_TIMEOUT = 1000;
    private final HoverPalette miniPalette = new HoverPalette().setTimeout( PALETTE_TIMEOUT );

    private Palette.CloseCallback closeCallback;
    private int rows = -1;
    private int cols = -1;
    private int iconSize;
    private int padding;
    private double animationDuration = 500;

    public SimpleGlyphPaletteView() {

        final Palette.CloseCallback thisCloseCallback = () -> {

            SimpleGlyphPaletteView.this.clear();

            if (null != closeCallback) {

                closeCallback.onClose();

            }

        };

        miniPalette.setCloseCallback(thisCloseCallback::onClose);
    }

    @Override
    public SimpleGlyphPalette.View<SimpleGlyphPaletteView> setCloseCallback(final Palette.CloseCallback callback) {
        this.closeCallback = callback;
        return this;
    }

    @Override
    public SimpleGlyphPalette.View<SimpleGlyphPaletteView> setItemCallbacks(final Palette.ItemHoverCallback itemCallbacks,
                                                                            final Palette.ItemOutCallback itemOutCallback,
                                                                            final Palette.ItemMouseDownCallback itemMouseDownCallback,
                                                                            final Palette.ItemClickCallback itemClickCallback ) {
        miniPalette.setItemCallback(new AbstractPalette.Callback() {

            @Override
            public void onItemHover(final int index,
                                    final double x,
                                    final double y) {

                if ( null != itemCallbacks ) {

                    itemCallbacks.onItemHover( index, x, y );

                }

            }

            @Override
            public void onItemOut(final int index) {

                if ( null != itemOutCallback ) {

                    itemOutCallback.onItemOut(index);

                }

            }

            @Override
            public void onItemMouseDown(final int index,
                                        final int x,
                                        final int y) {

                if ( null != itemMouseDownCallback ) {

                    itemMouseDownCallback.onItemMouseDown(index, x, y);

                }

            }

            @Override
            public void onItemClick(final int index,
                                    final int x,
                                    final int y) {

                if ( null != itemClickCallback ) {

                    itemClickCallback.onItemClick(index, x, y);

                }

            }

        } );

        return this;
    }

    @Override
    public SimpleGlyphPalette.View<SimpleGlyphPaletteView> setAnimationDuration(final double millis) {
        this.animationDuration = millis;
        return this;
    }

    @Override
    public SimpleGlyphPalette.View<SimpleGlyphPaletteView> setX(final double x) {
        miniPalette.setX( x );
        return this;
    }

    @Override
    public SimpleGlyphPalette.View<SimpleGlyphPaletteView> setY(final double y) {
        miniPalette.setY( y );
        return this;
    }

    @Override
    public SimpleGlyphPalette.View<SimpleGlyphPaletteView> setRows(final int rows) {
        this.rows = rows;
        return this;
    }

    @Override
    public SimpleGlyphPalette.View<SimpleGlyphPaletteView> setColumns(final int cols) {
        this.cols = cols;
        return this;
    }

    @Override
    public SimpleGlyphPalette.View<SimpleGlyphPaletteView> setIconSize(final int iconSize) {
        this.iconSize = iconSize;
        return this;
    }

    @Override
    public SimpleGlyphPalette.View<SimpleGlyphPaletteView> setPadding(final int padding) {
        this.padding = padding;
        return this;
    }

    public SimpleGlyphPalette.View<SimpleGlyphPaletteView> show(final Layer layer, final IPrimitive<?>[] items ) {


        ( (LienzoLayer) layer).getLienzoLayer().add( miniPalette );

        miniPalette
                .setRows( rows )
                .setColumns( cols)
                .setIconSize( iconSize )
                .setPadding( padding )
                .build( items )
                .setAlpha( 0 )
                .animate(AnimationTweener.LINEAR,
                        AnimationProperties.toPropertyList(AnimationProperty.Properties.ALPHA(1)),
                        animationDuration, new AnimationCallback());


        return this;
    }



    @Override
    public SimpleGlyphPalette.View<SimpleGlyphPaletteView> clear() {

        miniPalette
                .animate(AnimationTweener.LINEAR,
                        AnimationProperties.toPropertyList(AnimationProperty.Properties.ALPHA(0)),
                        animationDuration, new AnimationCallback() {

                            @Override
                            public void onClose(final IAnimation animation,
                                                final IAnimationHandle handle) {

                                super.onClose(animation, handle);
                                miniPalette.clear();

                            }

                        });

        return this;
    }

    @Override
    public void destroy() {
        this.miniPalette.removeFromParent();
    }

}
