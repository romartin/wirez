package org.kie.workbench.common.stunner.client.lienzo.components.palette.view.element;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Text;
import org.kie.workbench.common.stunner.client.lienzo.components.palette.view.LienzoPaletteView;
import org.kie.workbench.common.stunner.core.client.components.palette.ClientPaletteUtils;
import org.kie.workbench.common.stunner.core.client.components.palette.model.GlyphPaletteItem;
import org.kie.workbench.common.stunner.core.client.shape.view.ShapeGlyph;

public final class LienzoGlyphPaletteItemViewImpl
        extends AbstractLienzoGlyphPaletteItemView {

    private static final String FONT_FAMILY = "Open Sans";

    protected final ShapeGlyph<Group> glyph;
    private final Group view = new Group();
    private Text text;

    public LienzoGlyphPaletteItemViewImpl(final GlyphPaletteItem item,
                                          final LienzoPaletteView paletteView,
                                          final ShapeGlyph<Group> glyph ) {
        super( item, paletteView );
        this.glyph = glyph;
        init();
    }

    public void expand() {
        view.add( this.text );
    }

    public void collapse() {
        view.remove( this.text );
    }

    @Override
    public IPrimitive<?> getView() {
        return view;
    }

    private void init() {

        final String title = item.getTitle();

        final double glyphWidth = glyph.getWidth();
        final double glyphHeight = glyph.getHeight();

        final double fontSize = ClientPaletteUtils.computeFontSize( glyphWidth, glyphHeight, title.length() );

        text = new Text( title )
                .setX( glyphWidth + 10 )
                .setY( glyphWidth / 2 )
                .setFontFamily( FONT_FAMILY )
                .setFontSize( fontSize )
                .setStrokeWidth( 1 );


        view.add( glyph.getGroup() );


    }

}
