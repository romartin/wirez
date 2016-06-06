package org.wirez.client.lienzo.components.palette.glyph;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import org.wirez.core.client.canvas.Layer;
import org.wirez.core.client.components.palette.AbstractPalette;
import org.wirez.core.client.components.palette.PaletteGrid;
import org.wirez.core.client.components.palette.SimplePalette;
import org.wirez.core.client.shape.view.ShapeGlyph;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class SimpleGlyphPalette extends AbstractPalette<SimpleGlyphPalette, ShapeGlyph<Group>>
        implements org.wirez.core.client.components.palette.glyph.SimpleGlyphPalette<SimpleGlyphPalette, Group> {

    public interface View<T> {

        View<T> setCloseCallback( CloseCallback callback );

        View<T> setItemCallbacks( ItemHoverCallback itemCallbacks,
                                  ItemOutCallback itemOutCallback,
                                  ItemMouseDownCallback itemMouseDownCallback,
                                  ItemClickCallback itemClickCallback );

        View<T> setAnimationDuration( double millis );

        View<T> setX( double x );
        
        View<T> setY( double y );

        View<T> setRows( int rows );

        View<T> setColumns( int cols );

        View<T> setIconSize( int iconSize );

        View<T> setPadding( int padding);

        View<T> show( Layer layer, IPrimitive<?>[] items );

        View<T> clear();

        void destroy();

    }

    View view;

    @Inject
    public SimpleGlyphPalette(final View view ) {
        this.view = view;
    }

    @PostConstruct
    public void init() {
        view.setAnimationDuration( 300 );
    }

    @Override
    public SimplePalette<SimpleGlyphPalette, ShapeGlyph<Group>> setGrid(final PaletteGrid grid ) {
        view.setRows( grid.getRows() );
        view.setColumns(grid.getColumns());
        view.setIconSize(grid.getIconSize());
        view.setPadding(grid.getPadding());
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public SimpleGlyphPalette show(final Layer layer,
                                   final ShapeGlyph<Group>[] glyphs) {

        view
            .setX( x )
            .setY( y )
            .setCloseCallback( closeCallback )
            .setItemCallbacks( itemHoverCallback, itemOutCallback, itemMouseDownCallback, itemClickCallback );

        if ( null != glyphs && glyphs.length > 0 ) {

            final IPrimitive<?>[] items = new IPrimitive[glyphs.length];
            
            for ( int c = 0; c < glyphs.length; c++ ) {
                
                items[c] = glyphs[c].getGroup();
                
            }

            view.show( layer, items );

        }


        return this;
    }

    @Override
    protected void doClear() {
        view.clear();
    }

    @Override
    protected void doDestroy() {
        view.destroy();
        this.view = null;
    }

}
