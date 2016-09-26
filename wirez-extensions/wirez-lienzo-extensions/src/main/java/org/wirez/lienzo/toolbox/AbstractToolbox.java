package org.wirez.lienzo.toolbox;

import com.ait.lienzo.client.core.Attribute;
import com.ait.lienzo.client.core.animation.*;
import com.ait.lienzo.client.core.event.AnimationFrameAttributesChangedBatcher;
import com.ait.lienzo.client.core.event.AttributesChangedEvent;
import com.ait.lienzo.client.core.event.AttributesChangedHandler;
import com.ait.lienzo.client.core.event.IAttributesChangedBatcher;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Node;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeEvent;
import com.ait.lienzo.client.core.shape.wires.event.WiresResizeHandler;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.shared.core.types.Direction;
import com.ait.tooling.common.api.flow.Flows;
import com.ait.tooling.nativetools.client.event.HandlerRegistrationManager;
import org.wirez.lienzo.grid.Grid;
import org.wirez.lienzo.toolbox.grid.GridToolbox;

import java.util.Iterator;
import java.util.List;

import static com.ait.lienzo.client.core.AttributeOp.any;

public abstract class AbstractToolbox implements GridToolbox {

    private static final Flows.BooleanOp XYWH_OP = any(Attribute.X, Attribute.Y, Attribute.WIDTH, Attribute.HEIGHT);

    protected final WiresShape shape;
    protected final Direction anchor;
    protected final Direction towards;
    protected final List<ToolboxButton> buttons;
    protected final HandlerRegistrationManager handlerRegistrationManager = new HandlerRegistrationManager();
    protected IAttributesChangedBatcher attributesChangedBatcher = new AnimationFrameAttributesChangedBatcher();

    protected final Grid grid;
    protected Group group;

    protected void initialize() {

        if (grid.size() < buttons.size()) {
            throw new IllegalStateException("added more buttons than the grid can contain");
        }

        this.group = new Group().setDraggable( false );

        Iterator<Grid.Point> gridIterator = grid.iterator();
        for ( ToolboxButton button : buttons ) {
            Grid.Point point = gridIterator.next();
            button.getShape().setX( point.getX()).setY(point.getY() );
            button.getShape().getGroup().setAlpha(0);
            registerButton( button );
            group.add( button.getShape().getGroup() );
        }

        this.shape.getGroup().getLayer().add( group );

        reposition();
        initHandlers();

        this.shape.getGroup().getLayer().batch();
    }

    protected void registerButton( final ToolboxButton button ) {

    }

    protected void initHandlers()
    {
        shape.getGroup().setAttributesChangedBatcher(attributesChangedBatcher);

        final AttributesChangedHandler handler = new AttributesChangedHandler()
        {
            @Override
            public void onAttributesChanged(AttributesChangedEvent event)
            {
                if (event.evaluate(XYWH_OP))
                {
                    reposition();
                }
            }
        };

        // Attribute change handlers.
        handlerRegistrationManager.register(

            shape.getGroup().addAttributesChangedHandler( Attribute.X, handler )

        );

        handlerRegistrationManager.register(

            shape.getGroup().addAttributesChangedHandler(Attribute.Y, handler)

        );

        handlerRegistrationManager.register(

            shape.getPath().addAttributesChangedHandler(Attribute.WIDTH, handler)

        );

        handlerRegistrationManager.register(

            shape.getPath().addAttributesChangedHandler(Attribute.HEIGHT, handler)

        );

        // Shape resize handler.
        handlerRegistrationManager.register(
                shape.addWiresResizeHandler( new WiresResizeHandler() {
                    @Override
                    public void onShapeResized( final WiresResizeEvent resizeEvent ) {
                        reposition();
                    }
                } )
        );

    }

    protected void reposition() {
        final double gx = shape.getGroup().getAbsoluteLocation().getX();
        final double gy = shape.getGroup().getAbsoluteLocation().getY();
        final Point2D anchorPoint = Positioning.anchorFor(this.shape.getPath().getBoundingPoints().getBoundingBox(), this.anchor);
        final Grid.Point toolboxPosition = this.grid.findPosition(new Grid.Point((int) anchorPoint.getX(), (int) anchorPoint.getY()), this.towards);
        group.setX(gx + toolboxPosition.getX());
        group.setY(gy + toolboxPosition.getY());
    }

    @Override
    public void show() {

        for (ToolboxButton button : buttons) {

            button.getShape().getGroup().animate(AnimationTweener.LINEAR, AnimationProperties.toPropertyList(AnimationProperty.Properties.ALPHA(1)), 500, new AnimationCallback());

        }

    }

    @Override
    public void remove() {

        attributesChangedBatcher.cancelAttributesChangedBatcher();
        handlerRegistrationManager.removeHandler();

        for (ToolboxButton button : buttons) {
            button.remove();
        }

        group.removeFromParent();

    }

    @Override
    public void hide() {

        for (final ToolboxButton button : buttons) {

            button.getShape().getGroup().animate(
                    AnimationTweener.LINEAR,
                    AnimationProperties.toPropertyList( AnimationProperty.Properties.ALPHA( 0 ) ),
                    500,
                    new AnimationCallback() {

                        @Override
                        public void onClose(final IAnimation animation,
                                            final IAnimationHandle handle) {
                        }

            });

        }


    }

    Layer getLayer() {
        return shape.getGroup().getLayer();
    }

    protected AbstractToolbox( final WiresShape shape,
                             final Shape<?> attachTo,
                             final Direction anchor,
                             final Direction towards,
                             final int rows,
                             final int cols,
                             final int padding,
                             final int iconSize,
                             final List<ToolboxButton> buttons) {
        this.shape = shape;
        this.anchor = anchor;
        this.towards = towards;
        this.buttons = buttons;
        this.grid = new Grid(padding, iconSize, rows, cols);
        initialize();
        registerHandlers( attachTo );
    }

    private void registerHandlers( final Shape<?> attachTo ) {
        Node<?> node = attachTo != null ? attachTo : shape.getPath();
        registerHandlers( node );
    }

    protected void registerHandlers( final Node<?> node ) {

    }

}
