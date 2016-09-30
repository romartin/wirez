/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
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

package org.kie.workbench.common.stunner.shapes.client;

import org.kie.workbench.common.stunner.core.client.animation.Animation;
import org.kie.workbench.common.stunner.core.client.shape.view.HasFillGradient;
import org.kie.workbench.common.stunner.core.client.shape.view.HasRadius;
import org.kie.workbench.common.stunner.core.client.shape.view.HasSize;
import org.kie.workbench.common.stunner.core.client.shape.view.HasTitle;
import org.kie.workbench.common.stunner.core.client.shape.AbstractCompositeShape;
import org.kie.workbench.common.stunner.core.client.shape.MutationContext;
import org.kie.workbench.common.stunner.core.client.shape.ShapeState;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.view.View;
import org.kie.workbench.common.stunner.shapes.client.view.BasicShapeView;
import org.kie.workbench.common.stunner.shapes.client.view.animatiion.BasicShapeAnimation;
import org.kie.workbench.common.stunner.shapes.client.view.animatiion.BasicShapeDecoratorAnimation;

public abstract class BasicShape<W, V extends BasicShapeView>
    extends AbstractCompositeShape<W, Node<View<W>, Edge>, V> {

    private ShapeState state = ShapeState.NONE;
    private BasicShapeAnimation animation = null;
    private Double _strokeWidth = null;
    private Double _strokeAlpha = null;
    private String _strokeColor = null;

    public BasicShape( final V shapeView) {
        super(shapeView);
    }

    protected W getDefinition( final Node<View<W>, Edge> element ) {
        return element.getContent().getDefinition();
    }

    @Override
    public void beforeDraw() {
        super.beforeDraw();

        if ( hasAnimation() ) {

            getAnimation()
                    .setCallback( new Animation.AnimationCallback() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onFrame() {

                        }

                        @Override
                        public void onComplete() {
                            BasicShape.this.animation = null;
                        }
                    } )
                    .run();

        }

    }

    @Override
    public void applyState( final ShapeState shapeState ) {

        if ( !this.state.equals( shapeState ) ) {

            this.state = shapeState;

            if ( ShapeState.SELECTED.equals(shapeState) ) {
                applySelectedState();
            } else if ( ShapeState.HIGHLIGHT.equals(shapeState) ) {
                applyHighlightState();
            } else if ( ShapeState.INVALID.equals(shapeState) ) {
                applyInvalidState();
            } else {
                applyNoneState();
            }


        }

    }

    private void applySelectedState() {
        applyActiveState(ShapeState.SELECTED.getColor());
    }

    private void applyInvalidState() {
        applyActiveState(ShapeState.INVALID.getColor());
    }

    private void applyHighlightState() {
        applyActiveState(ShapeState.HIGHLIGHT.getColor());
    }

    private void applyActiveState( final String color ) {
        if ( null == this._strokeWidth ) {
            this._strokeWidth = getShapeView().getStrokeWidth();
        }

        if ( null == this._strokeColor) {
            this._strokeColor = getShapeView().getStrokeColor();
        }

        if ( null == this._strokeAlpha) {
            this._strokeAlpha = getShapeView().getStrokeAlpha();
        }


        new BasicShapeDecoratorAnimation( color, 5, 1 ).forShape( this ).run();

    }

    private void applyNoneState() {

        new BasicShapeDecoratorAnimation( this._strokeColor,
                            null != this._strokeWidth ? this._strokeWidth : 0,
                            null != this._strokeAlpha ? this._strokeAlpha : 0 )
                .forShape( this )
                .setCallback( new Animation.AnimationCallback() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFrame() {

                    }

                    @Override
                    public void onComplete() {
                        BasicShape.this._strokeWidth = null;
                        BasicShape.this._strokeColor = null;
                        BasicShape.this._strokeAlpha = null;
                    }
                } )
                .run();
    }

    @Override
    protected void _applyFillColor( final String color,
                                    final MutationContext mutationContext ) {

        final boolean hasGradient = view instanceof HasFillGradient;

        // Fill gradient cannot be animated for now in lienzo.
        if ( color != null && color.trim().length() > 0
                && !hasGradient
                && isAnimationMutation(mutationContext) ) {

            getAnimation().animateFillColor( color );

        } else {

            super._applyFillColor( color, mutationContext );

        }


    }

    @Override
    protected void _applyFontAlpha( final HasTitle hasTitle,
                                    final double alpha,
                                    final MutationContext mutationContext ) {

        final boolean isAnimation = isAnimationMutation( mutationContext );

        if ( isAnimation ) {

            getAnimation().animateFontAlpha( alpha );

        } else {

            super._applyFontAlpha( hasTitle, alpha, mutationContext );

        }

    }

    @Override
    protected void applySize( final HasSize hasSize,
                              final double width,
                              final double height,
                              final MutationContext mutationContext ) {

        // TODO: Shape (multipath) resize animations.
        if ( false && isAnimationMutation(mutationContext) ) {

            getAnimation().animateSize( width, height  );

        } else {

            super.applySize( hasSize, width, height, mutationContext );

        }


    }

    @Override
    protected void applyRadius( final HasRadius hasRadius,
                                final double radius,
                                final MutationContext mutationContext) {

        if (radius > 0) {

            // TODO: Shape (multipath) resize animations.
            if ( false && isAnimationMutation(mutationContext) ) {

                getAnimation().animateRadius( radius );

            } else {

                super.applyRadius( hasRadius, radius, mutationContext );

            }

        }

    }

    private boolean hasAnimation() {
        return null != animation;
    }

    private BasicShapeAnimation getAnimation() {

        if ( !hasAnimation() ) {

            this.animation = new BasicShapeAnimation();
            this.animation.forShape( this );
        }

        return animation;
    }
}
