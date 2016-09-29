package org.kie.workbench.common.stunner.core.client.shape;

import org.kie.workbench.common.stunner.core.graph.Element;
import org.kie.workbench.common.stunner.core.graph.content.view.View;
import org.kie.workbench.common.stunner.core.client.shape.view.ShapeView;

/**
 * A shape mutates according to a graph element.
 */
public interface GraphShape<W, C extends View<W>, E extends Element<C>, V extends ShapeView> extends MutableShape<E, V> {

    void applyPosition(E element, MutationContext mutationContext);

}
