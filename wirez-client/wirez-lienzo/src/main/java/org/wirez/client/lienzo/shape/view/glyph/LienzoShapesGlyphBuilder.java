package org.wirez.client.lienzo.shape.view.glyph;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.wires.WiresConnector;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.tooling.nativetools.client.collection.NFastArrayList;
import org.wirez.client.lienzo.util.LienzoUtils;
import org.wirez.core.api.FactoryManager;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.view.*;

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

        if ( view instanceof HasTitle) {

            final HasTitle hasTitle = (HasTitle) view;
            hasTitle.setTitle( null );

        }

        // Create a copy of this view.
        group = group.copy();
        
        // Scale, if necessary, to the given glyph size.
        final double[] scale = LienzoUtils.getScaleFactor( bb.getWidth(), bb.getHeight(), width, height );
        group.setScale( scale[0], scale[1] );

        // Apply positions.
        final double x = view instanceof HasRadius ? width / 2 : 0;
        final double y = view instanceof HasRadius ? height / 2 : 0;
        
        return new LienzoShapeGlyph( translate( group, x, y ), width, height );
        
    }

    private Group translate( final Group group,
                            final double x,
                            final double y ) {

        if ( x == 0 && y == 0 ) {

            return group;

        }

        if ( null != group ) {


            group.setX( x ).setY( y );

            /*final NFastArrayList<IPrimitive<?>> children = group.getChildNodes();

            if ( null != children && !children.isEmpty() ) {

                for ( final IPrimitive<?> primitive : children ) {

                    primitive.setX( primitive.getX() + x );
                    primitive.setY( primitive.getY() + y );

                }

            }*/

        }

        return group;
    }

   
}
