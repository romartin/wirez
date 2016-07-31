package org.wirez.client.widgets.navigation.navigator;

import com.google.gwt.user.client.ui.IsWidget;

public interface NavigatorView<T extends NavigatorItem> extends IsWidget {

    NavigatorView<T> add( NavigatorItemView<T> itemView );

    NavigatorView<T> clear();

    NavigatorView<T> setLoading( boolean loading );

}
