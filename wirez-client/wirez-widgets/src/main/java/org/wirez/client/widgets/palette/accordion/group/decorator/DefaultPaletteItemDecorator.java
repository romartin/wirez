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

package org.wirez.client.widgets.palette.accordion.group.decorator;

import com.ait.lienzo.client.core.animation.*;
import com.ait.lienzo.client.core.event.*;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.shared.core.types.ColorName;
import com.google.gwt.user.client.Timer;

public class DefaultPaletteItemDecorator extends Group implements PaletteItemDecorator {

    private static final int TIMER_DELAY = 200;
    private static final double ANIMATION_DURATION = 500;
    private double padding = 5;
    Rectangle decorator;
    Callback callback;

    public DefaultPaletteItemDecorator(final Callback callback) {
        this.callback = callback;
    }

    private Timer timer = new Timer() {
        @Override
        public void run() {
            hide();
        }
    };

    @Override
    public PaletteItemDecorator setPadding(final double padding) {
        this.padding = padding;
        return this;
    }

    @Override
    public PaletteItemDecorator setCallback(final Callback callback) {
        this.callback = callback;
        return this;
    }

    @Override
    public IPrimitive<?> build(final IPrimitive<?> item, final double width, final double height) {

        decorator = new Rectangle(width + padding, height + padding)
                .setCornerRadius(5)
                .setFillColor(ColorName.BLACK)
                .setFillAlpha(0.01)
                .setStrokeWidth(2)
                .setStrokeColor(ColorName.BLACK)
                .setStrokeAlpha(0);

        this.add(decorator);
        this.add(item);
        item.setX(padding / 2);
        item.setY(padding / 2);

        decorator.addNodeMouseEnterHandler(new NodeMouseEnterHandler() {
            @Override
            public void onNodeMouseEnter(NodeMouseEnterEvent nodeMouseEnterEvent) {
                show(nodeMouseEnterEvent.getMouseEvent().getClientX(), nodeMouseEnterEvent.getMouseEvent().getClientY());
            }
        });

        decorator.addNodeMouseExitHandler(new NodeMouseExitHandler() {
            @Override
            public void onNodeMouseExit(NodeMouseExitEvent nodeMouseExitEvent) {
                hide();
            }
        });


        decorator.addNodeMouseMoveHandler(new NodeMouseMoveHandler() {
            @Override
            public void onNodeMouseMove(NodeMouseMoveEvent nodeMouseMoveEvent) {
                timer.cancel();
            }
        });

        item.setDraggable(false);
        decorator.setDraggable(false).moveToTop();

        return this;
    }

    public PaletteItemDecorator show(final double x, final double y) {
        if (!timer.isRunning()) {
            decorator.animate(AnimationTweener.LINEAR,
                    AnimationProperties.toPropertyList(AnimationProperty.Properties.STROKE_ALPHA(1)),
                    ANIMATION_DURATION,
                    new AnimationCallback() {
                        @Override
                        public void onClose(IAnimation animation, IAnimationHandle handle) {
                            super.onClose(animation, handle);
                            fireShow(x, y);
                        }
                    });
            timer.schedule(TIMER_DELAY);
        }
        return this;
    }

    public PaletteItemDecorator hide() {
        if (!timer.isRunning()) {
            decorator.animate(AnimationTweener.LINEAR,
                    AnimationProperties.toPropertyList(AnimationProperty.Properties.STROKE_ALPHA(0)),
                    ANIMATION_DURATION,
                    new AnimationCallback() {
                        @Override
                        public void onClose(IAnimation animation, IAnimationHandle handle) {
                            super.onClose(animation, handle);
                            fireHide();
                        }
                    });
        }
        return this;
    }

    protected void fireShow(double x, double y) {
        if (null != callback) {
            callback.onShow(x, y);
        }
    }

    protected void fireHide() {
        if (null != callback) {
            callback.onHide();
        }
    }
}
