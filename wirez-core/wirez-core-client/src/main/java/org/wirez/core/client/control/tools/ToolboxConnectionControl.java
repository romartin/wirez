package org.wirez.core.client.control.tools;

import com.ait.lienzo.shared.core.types.ColorName;
import com.google.gwt.core.client.GWT;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.processing.GraphBoundsIndexer;
import org.wirez.core.client.HasDecorators;
import org.wirez.core.client.Shape;
import org.wirez.core.client.animation.ShapeDeSelectionAnimation;
import org.wirez.core.client.animation.ShapeSelectionAnimation;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.ShapeState;
import org.wirez.core.client.impl.BaseConnector;
import org.wirez.core.client.mutation.HasCanvasStateMutation;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class ToolboxConnectionControl {
    
    public interface Callback {
        
        void onNodeClick(final Node node);
        
    }
    
    public interface View {
        
        View init(ToolboxConnectionControl presenter);
        
        View show(Canvas canvas, double x, double y);

        View highlight(Canvas canvas, Shape shape);

        View unhighlight(Canvas canvas, Shape shape);
                
        View clear();
        
    }

    View view;

    @Inject
    public ToolboxConnectionControl(final View view) {
        this.view = view;
    }
    
    @PostConstruct
    public void  init() {
        view.init(this);
    }

    private CanvasHandler canvasHandler;
    private Callback callback;
    private GraphBoundsIndexer boundsIndexer;
    private Shape shape;

    public void show(final CanvasHandler canvasHandler,
                     final double startX,
                     final double startY) {
        show(canvasHandler, startX, startY, null);

    }
    
    public void show(final CanvasHandler canvasHandler,
                     final double startX,
                     final double startY,
                     final Callback callback) {
        this.canvasHandler = canvasHandler;
        this.callback = callback;
        this.boundsIndexer = new GraphBoundsIndexer(canvasHandler.getGraph());

        view.show(canvasHandler.getSettings().getCanvas(),
                startX, startY);

    }
    
    public void hide() {
        clear();
    }
    
    private void clear() {
        canvasHandler = null;
        shape = null;
        view.clear();
    }
    
    private void highLightShape(final Node node) {
        if ( null == this.shape ) {
            final String uuid = node.getUUID();
            final Shape shape = getShape(uuid);
            if ( null != shape ) {
                GWT.log("Highlight shape for " + uuid);
                this.shape = shape;

                if (shape instanceof HasCanvasStateMutation) {
                    final HasCanvasStateMutation canvasStateMutation = (HasCanvasStateMutation) shape;
                    canvasStateMutation.applyState(ShapeState.HIGHLIGHT);
                } else if (shape instanceof HasDecorators) {
                    view.highlight(canvasHandler.getSettings().getCanvas(), shape);
                    
                }
                
            }
        }
    }

    private void unhighLightShape() {
        if ( null != this.shape ) {

            if (shape instanceof HasCanvasStateMutation) {
                final HasCanvasStateMutation canvasStateMutation = (HasCanvasStateMutation) shape;
                canvasStateMutation.applyState(ShapeState.UNHIGHLIGHT);
            } else if (shape instanceof HasDecorators) {
                view.unhighlight(canvasHandler.getSettings().getCanvas(), shape);
            }

            this.shape = null;
            
        }
    }

   
    private Shape getShape(final String uuid) {
        return canvasHandler.getSettings().getCanvas().getShape(uuid);
    }
    
    void onMouseMove(final double x, final double y) {
        final Node node = boundsIndexer.getNodeAt(x, y);
        if ( null != node ) {
            highLightShape(node);
        } else {
            unhighLightShape();
        }
    }

    void onMouseClick(final double x, final double y) {
        if ( null != callback ) {
            final Node node = boundsIndexer.getNodeAt(x, y);
            if ( null != node ) {
                callback.onNodeClick(node);
            }
            view.clear();
        }
    }
    
}
