package org.kie.workbench.common.stunner.core.client.canvas.controls.toolbox;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

import javax.enterprise.context.Dependent;

@Dependent
public class CanvasToolboxControlView implements CanvasToolboxControl.View {

    private final FlowPanel panel = new FlowPanel();
    
    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public CanvasToolboxControl.View addWidget(final IsWidget widget) {
        panel.add(widget);
        return this;
    }

    @Override
    public CanvasToolboxControl.View clear() {
        panel.clear();;
        return this;
    }
}
