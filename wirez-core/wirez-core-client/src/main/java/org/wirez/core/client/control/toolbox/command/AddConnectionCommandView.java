package org.wirez.core.client.control.toolbox.command;

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.event.NodeMouseMoveEvent;
import com.ait.lienzo.client.core.event.NodeMouseMoveHandler;
import com.ait.lienzo.client.core.shape.Arrow;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.OrthogonalPolyLine;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.client.core.types.Point2DArray;
import com.ait.lienzo.shared.core.types.ArrowType;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.tooling.nativetools.client.event.HandlerRegistrationManager;
import com.google.gwt.core.client.GWT;
import org.wirez.core.client.Shape;
import org.wirez.core.client.animation.ShapeDeSelectionAnimation;
import org.wirez.core.client.animation.ShapeSelectionAnimation;
import org.wirez.core.client.canvas.Canvas;

public class AddConnectionCommandView implements AddConnectionCommand.View {
    
    private AddConnectionCommand presenter;

    private Canvas canvas;
    private IPrimitive<?> connector;
    private Point2D lineStart;
    private final HandlerRegistrationManager h_manager = new HandlerRegistrationManager();

    @Override
    public AddConnectionCommand.View init(final AddConnectionCommand presenter) {
        this.presenter = presenter;
        return this;
    }

    @Override
    public AddConnectionCommand.View show(final Canvas canvas, final double x, final double y) {
        this.canvas = canvas;
        doShow(x, y);
        return this;
    }

    @Override
    public AddConnectionCommand.View highlight(final Canvas canvas, final Shape shape) {
        new ShapeSelectionAnimation(shape)
                .setCanvas(canvas)
                .setDuration(200)
                .run();
        return this;
    }

    @Override
    public AddConnectionCommand.View unhighlight(final Canvas canvas, final Shape shape) {
        new ShapeDeSelectionAnimation(shape, 0, 0, ColorName.BLACK)
                .setCanvas(canvas)
                .setDuration(200)
                .run();

        return this;
    }


    @Override
    public AddConnectionCommand.View clear() {
        h_manager.removeHandler();
        if ( null != connector ) {
            connector.removeFromParent();
        }
        connector = null;
        lineStart = null;
        return this;
    }

    private void doShow(double x, double y) {
        GWT.log("Creating toolbox connection control...");

        final Layer layer = canvas.getLayer();
        
        lineStart = new Point2D(x, y);
        // connector = createLine(x, y,  x + 1, y + 1);
        connector = createArrow(x, y,  x + 1, y + 1);
        layer.add(connector);

        h_manager.register(layer.addNodeMouseMoveHandler(new NodeMouseMoveHandler() {
            @Override
            public void onNodeMouseMove(NodeMouseMoveEvent event) {
                int x = event.getX();
                int y = event.getY();
                // updateLineTo((OrthogonalPolyLine) connector, x, y);
                updateArrowTo((Arrow) connector, x, y);
                connector.moveToTop();
                presenter.onMouseMove(x, y);
                layer.batch();
            }
        }));

        h_manager.register(layer.addNodeMouseClickHandler(new NodeMouseClickHandler() {
            @Override
            public void onNodeMouseClick(NodeMouseClickEvent event) {
                int x = event.getX();
                int y = event.getY();
                presenter.onMouseClick(x, y);
            }
        }));

        layer.batch();
    }

    private OrthogonalPolyLine createLineEnd2End(final double x0, final double y0, final double x1, final double y1)
    {
        return createLine(x0, y0, (x0 + ((x1 - x0) / 2)), (y0 + ((y1 - y0) / 2)), x1, y1);
    }

    private OrthogonalPolyLine createLine(final double... points)
    {
        return new OrthogonalPolyLine(Point2DArray.fromArrayOfDouble(points)).setCornerRadius(5).setDraggable(true);
    }

    private void updateLineTo(final OrthogonalPolyLine _line, final double x, final double y)
    {
        Point2D p = new Point2D(x, y);
        _line.setPoint2DArray(new Point2DArray(lineStart, p));
    }
    
    private Arrow createArrow(final double x0, final double y0, final double x1, final double y1 ) {
        return new Arrow(new Point2D(x0, y0), new Point2D(x1, y1), 5d, 25d, 45d, 0d, ArrowType.AT_END)
                .setStrokeColor(ColorName.BLACK)
                .setFillColor(ColorName.BLACK);
    }

    private void updateArrowTo(final Arrow _arrow, final double x, final double y)
    {
        Point2D p = new Point2D(x, y);
        _arrow.setEnd(p);
    }
}
