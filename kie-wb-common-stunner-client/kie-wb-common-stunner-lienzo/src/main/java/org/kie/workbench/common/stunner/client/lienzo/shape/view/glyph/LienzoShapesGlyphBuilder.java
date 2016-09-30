package org.kie.workbench.common.stunner.client.lienzo.shape.view.glyph;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.wires.WiresConnector;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.client.core.types.BoundingBox;
import org.kie.workbench.common.stunner.core.client.shape.view.*;
import org.kie.workbench.common.stunner.client.lienzo.util.LienzoUtils;
import org.kie.workbench.common.stunner.core.api.FactoryManager;
import org.kie.workbench.common.stunner.core.client.shape.Shape;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class LienzoShapesGlyphBuilder extends AbstractBindableShapeGlyphBuilder<Group> {

    FactoryManager factoryManager;

    @Inject
    public LienzoShapesGlyphBuilder(final FactoryManager factoryManager) {
        this.factoryManager = factoryManager;
    }

    @Override
    protected FactoryManager getFactoryManager() {
        return factoryManager;
    }

    @Override
    protected ShapeGlyph<Group> doBuild( final Shape<?> shape ) {
        
        final ShapeView<?> view = shape.getShapeView();

        Group group = null;
        BoundingBox bb = null;

        if ( view instanceof WiresShape) {

            group = ((WiresShape) view).getGroup();
            bb = ((WiresShape) view).getPath().getBoundingBox();

        } else if ( view instanceof WiresConnector) {

            final WiresConnector wiresConnector = (WiresConnector) view;
            group = wiresConnector.getGroup();
            bb = wiresConnector.getGroup().getBoundingBox();

        }

        if ( null == group ) {

            throw new RuntimeException( "Shape view [" + view.toString() + "] not supported for " +
                    "this shape glyph builder [" + this.getClass().getName() );

        }

        if ( view instanceof HasTitle ) {

            final HasTitle hasTitle = (HasTitle) view;
            hasTitle.setTitle( null );

        }

        // Create a copy of this view.
        group = group.copy();

        // Group's bounding box top left point must be at 0,0 for all glyphs.
        final BoundingBox gbb = group.getBoundingBox();
        final double gx = group.getX() - ( gbb.getX() );
        final double gy = group.getY() - ( gbb.getY() );
        group.setX( gx / 2 ).setY( gy / 2 );

        // Scale, if necessary, to the given glyph size.
        final double[] scale = LienzoUtils.getScaleFactor( bb.getWidth(), bb.getHeight(), width, height );
        group.setScale( scale[0], scale[1] );

        return new LienzoShapeGlyph( group, width, height );
        
    }

}
