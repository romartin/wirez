package org.kie.workbench.common.stunner.client.widgets.palette.bs3.factory;

import com.google.gwt.user.client.ui.IsWidget;
import org.kie.workbench.common.stunner.core.client.ShapeManager;

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
