package org.wirez.client.lienzo.animation;

import org.wirez.core.client.animation.AnimationFactory;
import org.wirez.core.client.animation.ShapeAnimation;
import org.wirez.core.client.animation.ShapeDeSelectionAnimation;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LienzoAnimationFactory implements AnimationFactory {

    @Override
    public ShapeAnimation newShapeSelectAnimation() {
        return new LienzoShapeSelectionAnimation();
    }

    @Override
    public ShapeDeSelectionAnimation newShapeDeselectAnimation() {
        return new LienzoShapeDeSelectionAnimation();
    }

    @Override
    public ShapeAnimation newShapeNotValidAnimation() {
        return new LienzoShapeNotValidAnimation();
    }

    @Override
    public ShapeAnimation newShapeHighlightAnimation() {
        return new LienzoShapeHighlightAnimation();
    }

}
