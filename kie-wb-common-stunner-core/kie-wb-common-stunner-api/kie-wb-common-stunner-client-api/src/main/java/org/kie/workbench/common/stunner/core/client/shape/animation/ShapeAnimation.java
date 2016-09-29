package org.kie.workbench.common.stunner.core.client.shape.animation;

import org.kie.workbench.common.stunner.core.client.animation.Animation;
import org.kie.workbench.common.stunner.core.client.shape.Shape;

public interface ShapeAnimation<S extends Shape> extends Animation<S> {

    ShapeAnimation<S> forShape( S shape );

}
