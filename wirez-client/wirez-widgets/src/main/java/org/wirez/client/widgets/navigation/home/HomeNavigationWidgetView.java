package org.wirez.client.widgets.navigation.home;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import org.gwtbootstrap3.client.ui.PanelGroup;
import org.wirez.client.widgets.navigation.home.item.HomeNavigationItem;

import javax.enterprise.context.Dependent;

@Dependent
public class HomeNavigationWidgetView extends Composite implements HomeNavigationWidget.View {

    interface ViewBinder extends UiBinder<Widget, HomeNavigationWidgetView> {

    }

    private static ViewBinder uiBinder = GWT.create( ViewBinder.class );
    
    private HomeNavigationWidget presenter;

    @UiField
    FlowPanel rootPanel;

    @UiField
    PanelGroup mainPanel;

    @Override
    public void init(final HomeNavigationWidget presenter) {
        this.presenter = presenter;
        initWidget( uiBinder.createAndBindUi( this ) );
    }

    @Override
    public HomeNavigationWidget.View add( final HomeNavigationItem.View view ) {
        mainPanel.add( view );
        return this;
    }

    @Override
    public HomeNavigationWidget.View clear() {
        mainPanel.clear();
        return this;
    }

}
