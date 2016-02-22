package org.wirez.bpmn.client;

import com.ait.lienzo.client.core.shape.wires.WiresManager;
import org.wirez.bpmn.client.view.ParallelGatewayShapeView;
import org.wirez.bpmn.client.view.TaskShapeView;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BPMNViewFactory {
    
    public TaskShapeView task(final double width, 
                              final double height, 
                              final WiresManager manager) {
        return new TaskShapeView(width, height, manager);
    }
    
    public ParallelGatewayShapeView parallelGateway(final double radius,
                                                    final String fillColor,
                                                    final WiresManager manager) {
        return new ParallelGatewayShapeView(radius, fillColor, manager);
    }
 
}
