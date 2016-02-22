package org.wirez.bpmn.client.factory;

import org.wirez.bpmn.api.BPMNDefinition;
import org.wirez.bpmn.client.factory.control.BPMNToolboxControlFactory;
import org.wirez.client.views.ShapeViewFactory;
import org.wirez.core.client.Shape;
import org.wirez.core.client.canvas.control.ShapeGlyphDragHandler;
import org.wirez.core.client.factory.BaseShapeFactory;
import org.wirez.core.client.factory.control.DefaultShapeControlFactories;
import org.wirez.core.client.factory.control.ShapeControlFactory;

import javax.inject.Inject;

public abstract class BaseBPMNShapeFactory<W extends BPMNDefinition, S extends Shape<W>> extends BaseShapeFactory<W, S> {
    
    BPMNToolboxControlFactory bpmnToolboxControlFactory;
    ShapeViewFactory shapeViewFactory;

    public BaseBPMNShapeFactory() {
    }

    @Inject
    public BaseBPMNShapeFactory(final ShapeViewFactory shapeViewFactory,
                                final DefaultShapeControlFactories defaultShapeControlFactories, 
                                final ShapeGlyphDragHandler shapeGlyphDragHandler, 
                                final BPMNToolboxControlFactory bpmnToolboxControlFactory) {
        super(defaultShapeControlFactories, shapeGlyphDragHandler);
        this.shapeViewFactory = shapeViewFactory;
        this.bpmnToolboxControlFactory = bpmnToolboxControlFactory;
    }

    @Override
    protected ShapeControlFactory<?, ?> getToolboxControlFactory() {
        return bpmnToolboxControlFactory;
    }
}
