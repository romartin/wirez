package org.wirez.client.widgets.navigation.navigator.shapesets;

import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.uberfire.mvp.Command;
import org.wirez.client.widgets.navigation.navigator.NavigatorItem;
import org.wirez.client.widgets.navigation.navigator.NavigatorItemView;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.ShapeSet;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

@Dependent
public class ShapeSetNavigatorItemImpl implements IsWidget, ShapeSetNavigatorItem {

    private static Logger LOGGER = Logger.getLogger( ShapeSetNavigatorItemImpl.class.getName() );

    ShapeManager shapeManager;
    DefinitionManager definitionManager;
    NavigatorItemView<NavigatorItem> view;

    private String uuid;
    private Command callback;

    @Inject
    public ShapeSetNavigatorItemImpl( final ShapeManager shapeManager,
                                      final DefinitionManager definitionManager,
                                      final NavigatorItemView<NavigatorItem> view ) {
        this.shapeManager = shapeManager;
        this.definitionManager = definitionManager;
        this.view = view;
    }

    @PostConstruct
    public void init() {
        view.init( this );
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }

    public void show( final ShapeSet shapeSet,
                      final int widthInPx,
                      final int heightInPx,
                      final Command callback ) {
        this.callback = callback;

        this.uuid = shapeSet.getId();

        final String name = shapeSet.getName();
        final String defSetId = shapeSet.getDefinitionSetId();
        final SafeUri thumbUri = shapeManager.getThumbnail( defSetId );
        final Object defSet = definitionManager.getDefinitionSet( defSetId );
        final String description = definitionManager.getDefinitionSetAdapter( defSet.getClass() ).getDescription( defSet );
        view
            .setUUID( uuid )
            .setItemTitle( description )
            .setThumbUri( thumbUri );

        view.setItemPxSize( widthInPx, heightInPx );

    }

    @Override
    public String getUUID() {
        return uuid;
    }

    @Override
    public NavigatorItemView getView() {
        return view;
    }

    @Override
    public void onItemSelected() {
        if ( null != callback ) {
            callback.execute();
        }
    }

    private void log( final Level level, final String message ) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log( level, message );
        }
    }

}
