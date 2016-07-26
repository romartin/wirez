package org.wirez.client.widgets.navigation.navigator;

import com.google.gwt.dom.client.Style;
import org.uberfire.mvp.Command;

public interface NavigatorItem<T> {

    void show( T item, int width, int height, Style.Unit unit, Command callback );

    String getUUID();

    NavigatorItemView getView();

    void onItemSelected();

}
