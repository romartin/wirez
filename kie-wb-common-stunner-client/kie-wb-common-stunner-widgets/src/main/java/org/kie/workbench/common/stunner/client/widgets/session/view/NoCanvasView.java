package org.kie.workbench.common.stunner.client.widgets.session.view;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.constants.HeadingSize;

import javax.enterprise.context.Dependent;

@Dependent
public class NoCanvasView implements IsWidget {
    
    private final FlowPanel mainPanel = new FlowPanel();
    
    public void show() {
        mainPanel.add( new Heading(HeadingSize.H6, "No diagram in use"));
    }
    
    public void clear() {
        mainPanel.clear();
    }
    
    @Override
    public Widget asWidget() {
        return mainPanel;
    }
}
