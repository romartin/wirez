package org.wirez.client.widgets.navigation.home;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import org.gwtbootstrap3.client.ui.Icon;
import org.gwtbootstrap3.client.ui.PanelGroup;
import org.gwtbootstrap3.client.ui.constants.IconType;
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
    Icon addIcon;

    @UiField
    PanelGroup mainPanel;

    @Override
    public void init(final HomeNavigationWidget presenter) {
        this.presenter = presenter;
        initWidget( uiBinder.createAndBindUi( this ) );
        addIcon.addClickHandler( event -> presenter.onButtonClick() );
    }

    @Override
    public HomeNavigationWidget.View setIcon( final IconType iconType ) {
        addIcon.setType( iconType );
        return this;
    }

    @Override
    public HomeNavigationWidget.View setIconTitle( final String text ) {
        addIcon.setTitle( text );
        return this;
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
