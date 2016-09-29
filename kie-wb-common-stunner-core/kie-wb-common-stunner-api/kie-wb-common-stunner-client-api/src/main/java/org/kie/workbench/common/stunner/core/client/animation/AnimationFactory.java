package org.kie.workbench.common.stunner.core.client.animation;

public interface AnimationFactory<T, A extends Animation> {

    A newAnimation( T type );

}
