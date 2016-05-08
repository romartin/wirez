package org.wirez.core.client.canvas.util;

import org.wirez.core.api.graph.Node;
import org.wirez.core.client.animation.ShapeAnimation;
import org.wirez.core.client.animation.ShapeDeSelectionAnimation;
import org.wirez.core.client.canvas.AbstractCanvas;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.ShapeState;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.view.HasCanvasState;
import org.wirez.core.client.shape.view.HasDecorators;

public class CanvasHighlight {

    private final AbstractCanvasHandler canvasHandler;
    private Shape shape;
    private long duration = 200;
    private ShapeAnimation selectionAnimation;
    private ShapeDeSelectionAnimation deSelectionAnimation;
    
    public CanvasHighlight(final AbstractCanvasHandler canvasHandler,
                           final ShapeAnimation selectionAnimation,
                           final ShapeDeSelectionAnimation deSelectionAnimation ) {
        this.canvasHandler = canvasHandler;
        this.selectionAnimation = selectionAnimation;
        this.deSelectionAnimation = deSelectionAnimation;
    }

    public CanvasHighlight setDuration(final long duration) {
        this.duration = duration;
        return this;
    }

    public void highLight(final Node node) {
        if ( null != this.shape && !node.getUUID().equals(shape.getUUID()) ) {
            unhighLight();
        }
        if ( null == this.shape ) {
            final String uuid = node.getUUID();
            final Shape shape = getShape(uuid);
            if ( null != shape ) {
                this.shape = shape;

                if (shape.getShapeView() instanceof HasCanvasState) {
                    
                    final HasCanvasState canvasStateMutation = (HasCanvasState) shape.getShapeView();
                    canvasStateMutation.applyState(ShapeState.HIGHLIGHT);
                    
                } else if (shape.getShapeView() instanceof HasDecorators) {
                    
                    selectionAnimation.forShape( shape )
                            .forCanvas( getCanvas() )
                            .setDuration( duration )
                            .run();

                }

            }
        }
    }

    public void unhighLight() {
        if ( null != this.shape ) {

            if (shape instanceof HasCanvasState) {
                
                final HasCanvasState canvasStateMutation = (HasCanvasState) shape.getShapeView();
                canvasStateMutation.applyState(ShapeState.UNHIGHLIGHT);
                
            } else if (shape.getShapeView() instanceof HasDecorators) {
                
                deSelectionAnimation
                        .setStrokeWidth( 0 )
                        .setStrokeAlpha( 0 )
                        .setColor( "#000000" )
                        .forShape( shape )
                        .forCanvas( getCanvas() )
                        .setDuration( duration )
                        .run();
                
            }

            this.shape = null;

        }
    }


    private Shape getShape(final String uuid) {
        return getCanvas().getShape(uuid);
    }
    
    private AbstractCanvas getCanvas() {
        return canvasHandler.getCanvas();
    }
}
