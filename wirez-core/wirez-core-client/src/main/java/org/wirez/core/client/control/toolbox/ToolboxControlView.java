package org.wirez.core.client.control.toolbox;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

import javax.enterprise.context.Dependent;

@Dependent
public class ToolboxControlView  implements ToolboxControl.View {

    private final FlowPanel panel = new FlowPanel();
    
    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public ToolboxControl.View addWidget(final IsWidget widget) {
        panel.add(widget);
        return this;
    }

    @Override
    public ToolboxControl.View clear() {
        panel.clear();;
        return this;
    }
}
