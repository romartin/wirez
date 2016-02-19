package org.wirez.core.client.control.toolbox.command;

import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.processing.util.GraphBoundsIndexer;
import org.wirez.core.client.HasDecorators;
import org.wirez.core.client.Shape;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.ShapeState;
import org.wirez.core.client.mutation.HasCanvasStateMutation;
import org.wirez.core.client.util.SVGUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class AddConnectionCommand implements ToolboxCommand {

    private final com.ait.lienzo.client.core.shape.Shape<?> icon;
    
    public interface Callback {
        
        void init(Element source);
        
        boolean isAllowed(Context context, Node target);
        
        void accept(Context context, Node target);
        
    }
    
    public interface View {
        
        View init(AddConnectionCommand presenter);
        
        View show(Canvas canvas, double x, double y);

        View highlight(Canvas canvas, Shape shape);

        View unhighlight(Canvas canvas, Shape shape);
                
        View clear();
        
    }

    View view;

    @Inject
    public AddConnectionCommand(final View view) {
        this.view = view;
        this.icon = SVGUtils.createSVGIcon(SVGUtils.getCreateConnection());
    }
    
    @PostConstruct
    public void  init() {
        view.init(this);
    }

    private Callback callback;
    private GraphBoundsIndexer boundsIndexer;
    private Context context;
    private Element element;
    private Shape shape;


    @Override
    public com.ait.lienzo.client.core.shape.Shape<?> getIcon() {
        return icon;
    }

    public AddConnectionCommand setCallback(final Callback callback) {
        this.callback = callback;
        return this;
    }

    @Override
    public void execute(final Context context, final Element element) {
        this.element = element;
        this.context = context;
        this.boundsIndexer = new GraphBoundsIndexer(context.getCanvasHandler().getDiagram().getGraph());
        callback.init(element);
        view.show(context.getCanvasHandler().getCanvas(),
                context.getX(), context.getY());
    }
    
    private void clear() {
        unhighLightShape();
        view.clear();
        context = null;
        element = null;
        shape = null;
    }
    
    private void highLightShape(final Node node) {
        if ( null != this.shape && !node.getUUID().equals(shape.getId()) ) {
            unhighLightShape();
        }
        if ( null == this.shape ) {
            final String uuid = node.getUUID();
            final Shape shape = getShape(uuid);
            if ( null != shape ) {
                this.shape = shape;

                if (shape instanceof HasCanvasStateMutation) {
                    final HasCanvasStateMutation canvasStateMutation = (HasCanvasStateMutation) shape;
                    canvasStateMutation.applyState(ShapeState.HIGHLIGHT);
                } else if (shape instanceof HasDecorators) {
                    view.highlight(context.getCanvasHandler().getCanvas(), shape);
                    
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
                view.unhighlight(context.getCanvasHandler().getCanvas(), shape);
            }

            this.shape = null;
            
        }
    }

   
    private Shape getShape(final String uuid) {
        return context.getCanvasHandler().getCanvas().getShape(uuid);
    }
    
    void onMouseMove(final double x, final double y) {
        final Node node = boundsIndexer.getNodeAt(x, y);
        if ( null != node 
                && !node.getUUID().equals(element.getUUID())
                && callback.isAllowed( context, node ) ) {
            highLightShape(node);
        } else {
            unhighLightShape();
        }
    }

    void onMouseClick(final double x, final double y) {
        if ( null != callback  ) {
            final Node node = boundsIndexer.getNodeAt(x, y);
            if ( null != node && !node.getUUID().equals(element.getUUID()) ) {
                callback.accept(context,node);
            }
        }
        clear();
    }
    
}
