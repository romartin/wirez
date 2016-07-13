package org.wirez.client.widgets.palette.bs3.factory;

import com.google.gwt.user.client.ui.IsWidget;

public interface BS3PaletteViewFactory {

    boolean accepts( String id );

    IsWidget getCategoryView( String categoryId, final int width, final int height );

    IsWidget getDefinitionView( String defId, final int width, final int height );

}
