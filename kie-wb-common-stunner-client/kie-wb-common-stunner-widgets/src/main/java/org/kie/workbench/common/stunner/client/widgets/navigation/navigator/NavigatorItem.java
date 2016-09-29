package org.kie.workbench.common.stunner.client.widgets.navigation.navigator;

import org.uberfire.mvp.Command;

public interface NavigatorItem<T> {

    /**
     * Shows the item.
     * @param item Item to show.
     * @param width Width in PX.
     * @param height Height in PX
     * @param callback Callback when item selected ( usually on click or touch )
     */
    void show( T item, int width, int height, Command callback );

    String getUUID();

    NavigatorItemView getView();

    void onItemSelected();

}
