package org.kie.workbench.common.stunner.client.widgets.palette;

import com.google.gwt.user.client.ui.IsWidget;

public interface PaletteWidgetView<V> extends IsWidget {

    // TODO: Remove emtpy view method here -> See SessionScreenView
    void showEmptyView( boolean visible );

    // TODO: Remove from view. It can be directly used in the presenter.
    void showDragProxy( String itemId, double x, double y );

    void setBackgroundColor( String color );

    void setMarginTop( int mTop );

    void show( V paletteView );

    void show( V paletteView, int width, int height );

    int getAbsoluteTop();

    int getAbsoluteLeft();

    void clear();

    void destroy();

}
