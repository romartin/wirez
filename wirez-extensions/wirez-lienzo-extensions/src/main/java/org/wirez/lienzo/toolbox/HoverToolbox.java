package org.wirez.lienzo.toolbox;

import com.ait.lienzo.client.core.animation.AnimationCallback;
import com.ait.lienzo.client.core.animation.AnimationProperties;
import com.ait.lienzo.client.core.animation.AnimationProperty;
import com.ait.lienzo.client.core.animation.AnimationTweener;
import com.ait.lienzo.client.core.animation.IAnimation;
import com.ait.lienzo.client.core.animation.IAnimationHandle;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.client.core.types.Point2DArray;
import com.ait.lienzo.client.core.util.Geometry;
import com.ait.lienzo.shared.core.types.Direction;
import com.ait.tooling.nativetools.client.event.HandlerRegistrationManager;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import org.wirez.lienzo.HoverTimer;

import java.util.ArrayList;
import java.util.List;

public class HoverToolbox {

    private final WiresShape shape;
    private final Direction corner;
    private final Direction towards;
    private final List<HoverToolboxButton> buttons;
    private final HandlerRegistrationManager handlerRegistrationManager = new HandlerRegistrationManager();

    private final HoverTimer hoverTimer = new HoverTimer(new HoverTimer.Actions() {
        @Override
        public void onMouseEnter() {
            HoverToolbox.this.show();
        }

        @Override
        public void onMouseExit() {
            HoverToolbox.this.hide();
        }

        @Override
        public boolean isReadyToHide() {
            return HoverToolbox.this.showing;
        }
    });
    private boolean showing;

    public void show() {
        if (!showing) {
            for (int i = 0; i < buttons.size(); i++) {
                HoverToolboxButton hoverToolboxButton = buttons.get(i);
                Point2D position = findPosition(i);
                GWT.log(position.toJSONString());
                hoverToolboxButton.getShape().setX(position.getX());
                hoverToolboxButton.getShape().setY(position.getY());
                hoverToolboxButton.getShape().animate(AnimationTweener.LINEAR, AnimationProperties.toPropertyList(AnimationProperty.Properties.ALPHA(1)), 500, new AnimationCallback());
                HandlerRegistration hr1 = hoverToolboxButton.getShape().addNodeMouseEnterHandler(hoverTimer);
                HandlerRegistration hr2 = hoverToolboxButton.getShape().addNodeMouseExitHandler(hoverTimer);
                handlerRegistrationManager.register(hr1);
                handlerRegistrationManager.register(hr2);
                this.shape.addChild(hoverToolboxButton.getShape());
                this.shape.getWiresLayer().getLayer().batch();
            }

            showing = true;
        }
    }
    
    public void remove() {
        for (final HoverToolboxButton button : buttons) {
            button.getShape().removeFromParent();
        }
        handlerRegistrationManager.removeHandler();
    }

    private Point2D anchorFor(Direction direction) {
        Point2DArray cardinals = Geometry.getCardinals(this.shape.getPath().getBoundingPoints().getBoundingBox());
        Point2D anchor = null;
        switch (direction) {
            case NORTH_EAST:
                anchor = cardinals.get(2);
                break;
            case SOUTH_EAST:
                anchor = cardinals.get(4);
                break;
            case SOUTH_WEST:
                anchor = cardinals.get(6);
                break;
            case NORTH_WEST:
                anchor = cardinals.get(8);
                break;
            default:
                throw new RuntimeException("meh");
        }
        return anchor;
    }

    private Point2D findPosition(int i) {
        Point2D anchor = anchorFor(this.corner);
        double xFactor = 0, yFactor = 0, xOffset = 0, yOffset = 0;

        switch (corner) {
            case NORTH_WEST:
                xOffset = -HoverToolboxButton.BUTTON_SIZE -2;
                yOffset = -HoverToolboxButton.BUTTON_SIZE -2;
                break;
            case NORTH_EAST:
                xOffset = 2;
                yOffset = -HoverToolboxButton.BUTTON_SIZE -2;
                break;
            case SOUTH_WEST:
                xOffset = -HoverToolboxButton.BUTTON_SIZE -2;
                yOffset = 2;
                break;
            case SOUTH_EAST:
                xOffset = 2;
                yOffset = 2;
                break;
        }

        switch (towards) {
            case EAST:
                xFactor = 1;
                yFactor = 0;
                break;
            case NORTH:
                xFactor = 0;
                yFactor = -1;
                break;
            case SOUTH:
                xFactor = 0;
                yFactor = 1;
                break;
            case WEST:
                xFactor = -1;
                yFactor = 0;
                break;
            default:
                throw new UnsupportedOperationException();

        }

        double x = xFactor * (i * HoverToolboxButton.BUTTON_SIZE) + (xFactor * 2) + xOffset;
        double y = yFactor * (i * HoverToolboxButton.BUTTON_SIZE) + (yFactor * 2) + yOffset;
        return new Point2D(anchor.getX() + x, anchor.getY() + y);
    }

    public void hide() {
        if (showing) {
            for (final HoverToolboxButton button : buttons) {
                button.getShape().animate(AnimationTweener.LINEAR, AnimationProperties.toPropertyList(AnimationProperty.Properties.ALPHA(0)), 500, new AnimationCallback(){
                    @Override
                    public void onClose(IAnimation animation, IAnimationHandle handle) {
                        button.getShape().removeFromParent();
                        if ( null != HoverToolbox.this.shape)  {
                            HoverToolbox.this.shape.getWiresLayer().getLayer().batch();
                        }
                    }
                });
            }
            showing = false;
        }
    }

    private HoverToolbox(WiresShape shape, Direction anchor, Direction towards, List<HoverToolboxButton> buttons) {
        this.shape = shape;
        this.corner = anchor;
        this.towards = towards;
        this.buttons = buttons;
        registerHandlers();
    }

    private void registerHandlers() {
        this.shape.getPath().addNodeMouseEnterHandler(this.hoverTimer);
        this.shape.getPath().addNodeMouseExitHandler(this.hoverTimer);
    }

    private static class Builder implements On, Towards, ButtonsOrRegister {

        private WiresShape shape;
        private Direction anchor;
        private Direction towards;
        private List<HoverToolboxButton> buttons = new ArrayList<>();

        public Builder(WiresShape shape) {
            this.shape = shape;
        }

        @Override
        public Towards on(Direction anchor) {
            this.anchor = anchor;
            return this;
        }

        @Override
        public ButtonsOrRegister towards(Direction towards) {
            this.towards = towards;
            return this;
        }

        @Override
        public ButtonsOrRegister add(HoverToolboxButton button) {
            this.buttons.add(button);
            return this;
        }

        @Override
        public HoverToolbox register() {
            return new HoverToolbox(this.shape, this.anchor, this.towards, this.buttons);
        }
    }

    public static On toolboxFor(WiresShape shape) {
        return new Builder(shape);
    }
}
