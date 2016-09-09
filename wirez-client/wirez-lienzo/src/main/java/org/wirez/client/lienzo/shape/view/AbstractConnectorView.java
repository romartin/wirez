package org.wirez.client.lienzo.shape.view;

import com.ait.lienzo.client.core.shape.AbstractDirectionalMultiPointShape;
import com.ait.lienzo.client.core.shape.MultiPathDecorator;
import com.ait.lienzo.client.core.shape.OrthogonalPolyLine;
import com.ait.lienzo.client.core.shape.wires.*;
import com.ait.lienzo.client.core.types.Point2DArray;
import com.ait.lienzo.shared.core.types.ColorName;
import org.wirez.core.client.shape.view.IsConnector;
import org.wirez.core.client.shape.view.ShapeView;

public abstract class AbstractConnectorView<T> extends WiresConnector
    implements 
        ShapeView<T>,
        IsConnector<T> {

    protected String uuid;
    private int zindex;

    public AbstractConnectorView(AbstractDirectionalMultiPointShape<?> line, MultiPathDecorator headDecorator, MultiPathDecorator tailDecorator ) {
        super(line, headDecorator, tailDecorator );
        init();
    }

    public AbstractConnectorView(WiresMagnet headMagnet, WiresMagnet tailMagnet, AbstractDirectionalMultiPointShape<?> line,
                                 MultiPathDecorator headDecorator, MultiPathDecorator tailDecorator) {
        super(headMagnet, tailMagnet, line, headDecorator, tailDecorator );
        init();
    }

    protected abstract void doDestroy();
    
    protected void init() {
        getLine().setFillColor(ColorName.WHITE).setStrokeWidth(0);
    }
    
    @Override
    public T setUUID(final String uuid) {
        this.uuid = uuid;
        this.getGroup().setUserData( UUID_PREFFIX + uuid );
        return (T) this;
    }

    @Override
    public String getUUID() {
        return uuid;
    }

    @Override
    public T setZIndex(final int zindez) {
        this.zindex = zindez;
        return (T) this;
    }

    @Override
    public int getZIndex() {
        return zindex;
    }
    
    public T connect(final ShapeView headShapeView, 
                           final int _headMagnetsIndex, 
                           final ShapeView tailShapeView, 
                           final int _tailMagnetsIndex,
                           final boolean tailArrow,
                           final boolean headArrow)
    {
        final WiresShape headWiresShape = (WiresShape) headShapeView;
        final WiresShape tailWiresShape = (WiresShape) tailShapeView;
        final MagnetManager.Magnets headMagnets = headWiresShape.getMagnets();
        final MagnetManager.Magnets tailMagnets = tailWiresShape.getMagnets();

        int headMagnetsIndex = _headMagnetsIndex;
        int tailMagnetsIndex = _tailMagnetsIndex;
        
        if (headMagnetsIndex < 0) {
            headMagnetsIndex = 0;
        }

        if (tailMagnetsIndex < 0) {
            tailMagnetsIndex = 0;
        }


        // Obtain the magnets.
        WiresMagnet m0_1 = headMagnets.getMagnet(headMagnetsIndex);
        WiresMagnet m1_1 = tailMagnets.getMagnet(tailMagnetsIndex);

        // Update the magnets.
        this.setHeadMagnet(m0_1);
        this.setTailMagnet(m1_1);

        double x0 = m0_1.getControl().getX();
        double y0 = m0_1.getControl().getY();
        double x1 = m1_1.getControl().getX();
        double y1 = m1_1.getControl().getY();

        // TODO: Update the connector decorator in order to modify head & tail decorators (connector direction)
       /* OrthogonalPolyLine line = createLine(x0, y0, (x0 + ((x1 - x0) / 2)), (y0 + ((y1 - y0) / 2)), x1, y1);
        this.setDecorator(
                line, 
                headArrow ? new SimpleArrow(20, 0.75) : null,
                tailArrow ? new SimpleArrow(20, 0.75) : null);*/

        return (T) this;
    }

    private OrthogonalPolyLine createLine(final double... points)
    {
        return new OrthogonalPolyLine(Point2DArray.fromArrayOfDouble(points)).setCornerRadius(5).setDraggable(true);
    }

    @Override
    public T setDragEnabled(final boolean isDraggable) {
        this.setDraggable();
        return (T) this;
    }

    @Override
    public void removeFromParent() {
        // Remove the main line.
        super.removeFromLayer();
    }

    @Override
    public double getShapeX() {
        return getGroup().getX();
    }

    @Override
    public double getShapeY() {
        return getGroup().getY();
    }

    @Override
    public T setShapeX(final double x) {
        getGroup().setX(x);
        return (T) this;
    }

    @Override
    public T setShapeY(final double y) {
        getGroup().setY(y);
        return (T) this;
    }

    @Override
    public String getFillColor() {
        return getLine().getFillColor();
    }

    @Override
    public T setFillColor(final String color) {
        getLine().setFillColor(color);
        return (T) this;
    }

    @Override
    public double getFillAlpha() {
        return getLine().getFillAlpha();
    }

    @Override
    public T setFillAlpha(final double alpha) {
        getLine().setFillAlpha(alpha);
        return (T) this;
    }

    @Override
    public String getStrokeColor() {
        return getLine().getStrokeColor();
    }

    @Override
    public T setStrokeColor(final String color) {
        getLine().setStrokeColor(color);
        return (T) this;
    }

    @Override
    public double getStrokeAlpha() {
        return getLine().getStrokeAlpha();
    }

    @Override
    public T setStrokeAlpha(final double alpha) {
        getLine().setStrokeAlpha(alpha);
        return (T) this;
    }

    @Override
    public double getStrokeWidth() {
        return getLine().getStrokeWidth();
    }

    @Override
    public T setStrokeWidth(final double width) {
        getLine().setStrokeWidth(width);
        return (T) this;
    }
    
    @Override
    public T moveToTop() {
        getGroup().moveToTop();
        return (T) this;
    }

    @Override
    public T moveToBottom() {
        getGroup().moveToBottom();
        return (T) this;
    }

    @Override
    public T moveUp() {
        getGroup().moveUp();
        return (T) this;
    }

    @Override
    public T moveDown() {
        getGroup().moveDown();
        return (T) this;
    }

    @Override
    public void destroy() {
        
        // Implementations can clear its state here.
        this.doDestroy();

        // Remove me.    
        this.removeFromParent();

    }
    
}
