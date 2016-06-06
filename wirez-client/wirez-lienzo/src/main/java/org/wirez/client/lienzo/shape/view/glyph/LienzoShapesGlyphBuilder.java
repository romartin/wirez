package org.wirez.client.lienzo.shape.view.glyph;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.util.Geometry;
import com.google.gwt.core.client.GWT;
import org.wirez.client.lienzo.shape.view.AbstractConnectorView;
import org.wirez.client.lienzo.shape.view.AbstractShapeView;
import org.wirez.core.api.FactoryManager;
import org.wirez.core.client.shape.view.HasRadius;
import org.wirez.core.client.shape.view.HasTitle;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.view.*;
import org.wirez.core.client.util.ShapeUtils;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class LienzoShapesGlyphBuilder extends AbstractShapeGlyphBuilder<Group> {

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

        final WiresShape wiresShape = (WiresShape) view;

        Group group = null;

        if ( view instanceof AbstractShapeView) {

            group = ((AbstractShapeView) view).getGroup();

        } else if ( view instanceof AbstractConnectorView) {

            group = ((AbstractConnectorView) view).getGroup();

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
        final BoundingBox bb = wiresShape.getPath().getBoundingBox();

        // Scale, if necessary, to the given glyph size.
        final double w = bb.getWidth();
        final double h = bb.getHeight();
        final double sw = w > 0 ?  ( width / w) : 1;
        final double sh = h > 0 ? ( height / h ) : 1;
        group.setScale( sw, sh );


        // Apply positions.
        final double x = view instanceof HasRadius ? width / 2 : 0;
        final double y = view instanceof HasRadius ? height / 2 : 0;
        
        return new LienzoShapeGlyph( group.setX( x ).setY( y ), width, height );
        
    }

   
}
