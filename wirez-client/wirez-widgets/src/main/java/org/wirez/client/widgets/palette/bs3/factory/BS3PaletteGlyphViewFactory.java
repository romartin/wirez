package org.wirez.client.widgets.palette.bs3.factory;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.validation.client.impl.Group;
import org.wirez.client.lienzo.util.LienzoPanelUtils;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.shape.view.ShapeGlyph;

class BS3PaletteGlyphViewFactory implements BS3PaletteViewFactory {

    private final ShapeManager shapeManager;

    BS3PaletteGlyphViewFactory( final ShapeManager shapeManager ) {
        this.shapeManager = shapeManager;
    }

    @Override
    public boolean accepts( final String id ) {
        return true;
    }

    @Override
    public IsWidget getCategoryView( final String categoryId, final int width, final int height ) {
        return null;
    }

    @Override
    public IsWidget getDefinitionView( final String defId, final int width, final int height ) {
        final ShapeGlyph<Group> glyph = getGlyph( defId, width, height );
        return LienzoPanelUtils.newPanel( glyph, width, height );
    }

    @SuppressWarnings( "unchecked" )
    private ShapeGlyph<Group> getGlyph( final String id, final int width, final int height ) {
        return shapeManager.getFactory( id ).glyph( id, width, height );
    }

}
