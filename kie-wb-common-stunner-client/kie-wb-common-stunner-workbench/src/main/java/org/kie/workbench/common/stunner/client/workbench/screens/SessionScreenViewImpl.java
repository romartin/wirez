package org.kie.workbench.common.stunner.client.workbench.screens;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;

public class SessionScreenViewImpl extends FlowPanel implements SessionScreenView {

    private final FlowPanel emptyViewPanel = new FlowPanel(  );
    private final FlowPanel screenViewPanel = new FlowPanel(  );

    public SessionScreenViewImpl() {
        init();
    }

    public SessionScreenViewImpl( final String tag ) {
        super( tag );
        init();
    }

    @Override
    public void showEmptySession() {
        emptyViewPanel.setVisible( true );
        screenViewPanel.setVisible( false );
    }

    @Override
    public void showScreenView( final IsWidget viewWidget ) {
        emptyViewPanel.setVisible( false );
        setScreenView( viewWidget );
        screenViewPanel.setVisible( true );
    }

    @Override
    public void setScreenViewBgColor( final String color ) {
        screenViewPanel.getElement().getStyle().setBackgroundColor( color );
    }

    @Override
    public void setMarginTop( final int px ) {
        this.emptyViewPanel.getElement().getStyle().setMarginTop( px, Style.Unit.PX );
        this.screenViewPanel.getElement().getStyle().setMarginTop( px, Style.Unit.PX );
    }

    @Override
    public void setPaddingTop( final int px ) {
        this.emptyViewPanel.getElement().getStyle().setPaddingTop( px, Style.Unit.PX );
        this.screenViewPanel.getElement().getStyle().setPaddingTop( px, Style.Unit.PX );
    }

    @Override
    public IsWidget getView() {
        return this;
    }

    private void init() {

        this.add( emptyViewPanel );
        this.add( screenViewPanel );

        this.setHeight( "100%" );
        this.emptyViewPanel.setHeight( "100%" );
        this.screenViewPanel.setHeight( "100%" );

        showEmptySession();
    }

    private SessionScreenView setScreenView( final IsWidget view ) {
        this.screenViewPanel.clear();
        this.screenViewPanel.add( view );
        return this;
    }

}
