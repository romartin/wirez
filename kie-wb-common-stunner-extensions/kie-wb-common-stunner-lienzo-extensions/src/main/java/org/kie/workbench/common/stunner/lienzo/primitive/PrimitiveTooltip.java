/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.workbench.common.stunner.lienzo.primitive;

import com.ait.lienzo.client.core.shape.*;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.shared.core.types.ColorName;

public class PrimitiveTooltip extends PrimitivePopup {

    private static final double PADDING = 10;
    private static final double TRIANGLE_SIZE = 10;
    private static final double ALPHA = 1;
    private static final String BG_COLOR = ColorName.DARKSLATEGREY.getColorString();

    public enum Direction {

        NORTH, WEST;

    }

    public PrimitiveTooltip() {
        setzIndex( 100 );
    }

    public PrimitiveTooltip show(final IPrimitive<?> _glyph,
                                 final String text,
                                 final double width,
                                 final double height,
                                 final double x,
                                 final double y,
                                 final Direction direction) {

        final IPrimitive<?> glyph = null != _glyph ? (IPrimitive<?>) _glyph.copy() : null;

        final Text descText = new Text( text )
                .setFontSize( 8 )
                .setFontFamily( "Open Sans" )
                .setStrokeWidth( 1 )
                .setStrokeColor( ColorName.WHITE );

        final BoundingBox descTextBB = descText.getBoundingBox();
        final double descTextBbW = descTextBB.getWidth();
        final double descTextBbH = descTextBB.getHeight();
        final double dw = ( descTextBbW >  width ? ( descTextBbW + PADDING ) : ( width + PADDING ) );
        final double dh = height + descTextBbH + PADDING;
        final IPrimitive<?> decorator = buildDecorator( dw, dh, direction );
        final double w = dw + ( isWest( direction ) ? TRIANGLE_SIZE * 2 : 0);
        final double h = dh + ( isNorth( direction ) ? TRIANGLE_SIZE * 2 : 0);;

        final Group g = new Group();

        g.add(decorator);

        if ( null != glyph ) {
            g.add(glyph);
        }

        g.add(descText);

        super.show( g, w, h, x , y );

        double _x = ( w / 2 ) + ( isWest( direction ) ? PADDING / 2 : 0 );

        double _y = PADDING / 2 + ( isNorth( direction ) ? TRIANGLE_SIZE : 0 );

        if ( null != glyph ) {

            glyph.setX( _x - (width / 2) );
            glyph.setY( _y );
            _x += width;
            _y += height;

        }

        descText.setX( _x - ( descTextBbW / 2 ) );
        descText.setY( _y + descTextBbH );

        canvasLayer.draw();
        
        return this;
    }

    private IPrimitive<?> buildDecorator(final double width, final double height,final Direction direction) {

        final boolean isWest = isWest( direction );
        final boolean isNorth = isNorth( direction );

        final double h2 = height / 2;
        final double w2 = width / 2;
        final double s2 = TRIANGLE_SIZE / 2;

        final Point2D a = isWest ? new Point2D( 0 , h2 ) : new Point2D( w2 , 0 );
        final Point2D b = isWest ? new Point2D( TRIANGLE_SIZE , h2 + s2 ) : new Point2D( w2 + s2 , TRIANGLE_SIZE );
        final Point2D c = isWest ? new Point2D( TRIANGLE_SIZE , h2 - s2 ) : new Point2D( w2 - s2 , TRIANGLE_SIZE );

        final Triangle triangle = new Triangle( a, b, c )
                .setFillColor( BG_COLOR )
                .setFillAlpha( ALPHA )
                .setStrokeWidth( 0 );

        final Rectangle rectangle =
                new Rectangle(
                        width + ( isWest ? TRIANGLE_SIZE : 0 ),
                        height + ( isNorth ? TRIANGLE_SIZE : 0 ) )
                .setX( isWest ? TRIANGLE_SIZE : 0 )
                .setY( isWest ? 0 : TRIANGLE_SIZE)
                .setCornerRadius( 10 )
                .setFillColor( BG_COLOR )
                .setFillAlpha( ALPHA )
                .setStrokeAlpha( 0 )
                .setCornerRadius( 5 );

        final Group decorator = new Group();
        decorator.add(rectangle);
        decorator.add(triangle);

        return decorator;
    }

    private boolean isWest( final Direction direction ) {
        return Direction.WEST.equals( direction );
    }

    private boolean isNorth( final Direction direction ) {
        return Direction.NORTH.equals( direction );
    }

}
