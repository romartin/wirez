package org.wirez.client.widgets.navigation.home.item;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.gwtbootstrap3.client.ui.*;
import org.gwtbootstrap3.client.ui.constants.IconPosition;
import org.gwtbootstrap3.client.ui.constants.IconSize;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.wirez.core.util.UUID;

import javax.enterprise.context.Dependent;

@Dependent
public class HomeNavigationItemView extends Composite implements HomeNavigationItem.View {

    interface ViewBinder extends UiBinder<Widget, HomeNavigationItemView> {

    }

    private static ViewBinder uiBinder = GWT.create( ViewBinder.class );
    
    private HomeNavigationItem presenter;

    @UiField
    Panel panel;

    @UiField
    Heading heading;

    @UiField
    PanelHeader panelHeader;

    @UiField
    Anchor headingAnchor;

    @UiField
    PanelCollapse collapsePanel;

    @UiField
    PanelBody panelBody;

    @Override
    public void init(final HomeNavigationItem presenter) {
        this.presenter = presenter;
        initWidget( uiBinder.createAndBindUi( this ) );
        final String uuid = UUID.uuid();
        collapsePanel.setId( uuid );
        headingAnchor.setDataTarget( "#" + uuid );
        panel.getElement().getStyle().setBorderStyle( Style.BorderStyle.NONE );
        panel.getElement().getStyle().setMargin( 15, Style.Unit.PX );
    }

    @Override
    public HomeNavigationItem.View setCollapsed( boolean collapsed ) {
        collapsePanel.setIn( !collapsed );
        return this;
    }

    @Override
    public HomeNavigationItem.View setPanelTitle( final String title ) {
        heading.setText( title );
        panelHeader.getElement().getStyle().setFloat( Style.Float.NONE );
        return this;
    }

    @Override
    public HomeNavigationItem.View setPanelIcon( final IconType icon ) {
        headingAnchor.setIcon( icon );
        headingAnchor.setIconSize( IconSize.LARGE );
        headingAnchor.setIconPosition( IconPosition.RIGHT );
        panelHeader.getElement().getStyle().setFloat( Style.Float.LEFT );
        return this;
    }

    @Override
    public HomeNavigationItem.View setTooltip( final String tooltip ) {
        heading.setTitle( tooltip );
        return this;
    }

    @Override
    public HomeNavigationItem.View setPanelVisible( final boolean visible ) {
        panel.setVisible( visible );
        return this;
    }

    @Override
    public boolean isPanelVisible() {
        return panel.isVisible();
    }

    @Override
    public boolean isPanelCollapsed() {
        return collapsePanel.isIn();
    }

    @Override
    public HomeNavigationItem.View add( final IsWidget widget ) {
        panelBody.add( widget );
        return this;
    }

    @Override
    public HomeNavigationItem.View clear() {
        panelBody.clear();
        return this;
    }

}
