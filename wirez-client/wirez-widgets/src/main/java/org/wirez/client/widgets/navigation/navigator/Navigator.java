
package org.wirez.client.widgets.navigation.navigator;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.IsWidget;

import java.util.List;

public interface Navigator<T> extends IsWidget {

    Navigator<T> setItemSize( int width, int height, Style.Unit unit );

    Navigator<T> show();

    Navigator<T> clear();

    List<NavigatorItem<T>> getItems();

}
