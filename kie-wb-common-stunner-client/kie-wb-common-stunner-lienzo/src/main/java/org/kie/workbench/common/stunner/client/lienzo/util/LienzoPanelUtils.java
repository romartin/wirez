package org.kie.workbench.common.stunner.client.lienzo.util;

import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.google.gwt.validation.client.impl.Group;
import org.kie.workbench.common.stunner.core.client.shape.view.ShapeGlyph;

public class LienzoPanelUtils {

    public static LienzoPanel newPanel( final ShapeGlyph<Group> glyph, final int width, final int height ) {
        final com.ait.lienzo.client.widget.LienzoPanel panel = new LienzoPanel( width, height );
        final Layer layer = new Layer();

        panel.add( layer.setTransformable( true ) );

        layer.add( ( IPrimitive<?> ) glyph.copy() );

        return panel;
    }

}
