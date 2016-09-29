
package org.kie.workbench.common.stunner.client.widgets.navigation.navigator;

import com.google.gwt.user.client.ui.IsWidget;

import java.util.List;

public interface Navigator<T> extends IsWidget {

    Navigator<T> setItemPxSize( int width, int height );

    Navigator<T> show();

    Navigator<T> clear();

    List<NavigatorItem<T>> getItems();

    NavigatorView<?> getView();

}
