package org.kie.workbench.common.stunner.client.workbench.screens;

import com.google.gwt.user.client.ui.IsWidget;

public interface SessionScreenView {

    void showEmptySession();

    void showScreenView( IsWidget view );

    void setScreenViewBgColor( String color );

    void setMarginTop( int px );

    void setPaddingTop( int px );

    IsWidget getView();

}
