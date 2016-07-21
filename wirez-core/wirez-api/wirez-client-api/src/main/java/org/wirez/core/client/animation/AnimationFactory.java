package org.wirez.core.client.animation;

public interface AnimationFactory {

    ShapeAnimation newShapeSelectAnimation();

    ShapeDeSelectionAnimation newShapeDeselectAnimation();

    ShapeAnimation newShapeNotValidAnimation();

    ShapeAnimation newShapeHighlightAnimation();

}
