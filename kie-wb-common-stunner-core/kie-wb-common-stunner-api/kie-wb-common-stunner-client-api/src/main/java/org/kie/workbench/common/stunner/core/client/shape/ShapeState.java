package org.kie.workbench.common.stunner.core.client.shape;

/**
 * The available shape view's states on the canvas.
 */
public enum ShapeState {

    NONE,
    SELECTED( "#0000FF" ),
    HIGHLIGHT( "#3366CC" ),
    INVALID( "#FF0000" );

    private String color;

    ShapeState() {
        this( null );
    }

    ShapeState( final String color ) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

}
