package org.wirez.client.widgets.palette.view;

import com.google.gwt.user.client.ui.IsWidget;
import org.wirez.core.client.components.palette.view.PaletteView;

public interface PaletteWidgetView extends IsWidget {

    void showEmptyView( boolean visible );

    void showDragProxy( String itemId, double x, double y );

    void setPaletteSize( int width, int height );

    void setBackgroundColor( String color );

    void setMarginTop( int mTop );

    void show( PaletteView<?, ?, ?> paletteView );

    void show( PaletteView<?, ?, ?> paletteView, int width, int height );

    int getAbsoluteTop();

    int getAbsoluteLeft();

    void clear();

    void destroy();

}
