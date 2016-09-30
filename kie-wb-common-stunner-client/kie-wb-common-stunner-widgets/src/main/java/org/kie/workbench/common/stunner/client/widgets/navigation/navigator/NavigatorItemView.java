package org.kie.workbench.common.stunner.client.widgets.navigation.navigator;

import com.google.gwt.safehtml.shared.SafeUri;
import org.uberfire.client.mvp.UberView;

public interface NavigatorItemView<P extends NavigatorItem> extends UberView<P> {

    NavigatorItemView setUUID( String uuid );

    NavigatorItemView setItemTitle( String title );

    NavigatorItemView setThumbData( String thumbData );

    NavigatorItemView setThumbUri( SafeUri safeUri );

    NavigatorItemView setItemPxSize( int width, int height );

}
