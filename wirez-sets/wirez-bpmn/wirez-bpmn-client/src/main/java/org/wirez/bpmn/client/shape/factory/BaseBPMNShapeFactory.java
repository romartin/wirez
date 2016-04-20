package org.wirez.bpmn.client.shape.factory;

import org.wirez.bpmn.api.BPMNDefinition;
import org.wirez.client.shapes.ShapeViewFactory;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.factory.AbstractBindableShapeFactory;

import javax.inject.Inject;

public abstract class BaseBPMNShapeFactory<W extends BPMNDefinition, S extends Shape> extends AbstractBindableShapeFactory<W, S> {

    ShapeViewFactory shapeViewFactory;

    public BaseBPMNShapeFactory() {
    }

    @Inject
    public BaseBPMNShapeFactory(final ShapeViewFactory shapeViewFactory) {
        this.shapeViewFactory = shapeViewFactory;
    }
    
}
