package org.wirez.client.widgets.palette.bs3.factory;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.validation.client.impl.Group;
import org.wirez.client.lienzo.util.LienzoPanelUtils;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.shape.view.ShapeGlyph;
import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;

import java.util.Map;

public abstract class BindableBS3PaletteGlyphViewFactory<V extends IsWidget> extends BindableBS3PaletteViewFactory<V> {

    private final BS3PaletteGlyphViewFactory glyphViewFactory;

    public BindableBS3PaletteGlyphViewFactory( final ShapeManager shapeManager ) {
        this.glyphViewFactory = new BS3PaletteGlyphViewFactory( shapeManager );
    }

    @Override
    public IsWidget getDefinitionView( final String defId,
                                       final int width,
                                       final int height ) {

        final IsWidget view = super.getDefinitionView( defId, width, height );

        if ( null != view ) {

            return view;

        } else {

            return glyphViewFactory.getDefinitionView( defId, width, height );
        }

    }

}
