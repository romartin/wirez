package org.kie.workbench.common.stunner.client.widgets.navigation.navigator.diagrams;

import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.uberfire.mvp.Command;
import org.kie.workbench.common.stunner.client.widgets.navigation.navigator.NavigatorItem;
import org.kie.workbench.common.stunner.client.widgets.navigation.navigator.NavigatorItemView;
import org.kie.workbench.common.stunner.core.client.ShapeManager;
import org.kie.workbench.common.stunner.core.client.ShapeSet;
import org.kie.workbench.common.stunner.core.client.util.ShapeUtils;
import org.kie.workbench.common.stunner.core.lookup.diagram.DiagramRepresentation;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

@Dependent
public class DiagramNavigatorItemImpl implements IsWidget, DiagramNavigatorItem {

    private static Logger LOGGER = Logger.getLogger( DiagramNavigatorItemImpl.class.getName() );

    ShapeManager shapeManager;
    NavigatorItemView<NavigatorItem> view;

    private String uuid;
    private Command callback;

    @Inject
    public DiagramNavigatorItemImpl( final ShapeManager shapeManager,
                                     final NavigatorItemView<NavigatorItem> view ) {
        this.shapeManager = shapeManager;
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

    public void show( final DiagramRepresentation diagramRepresentation,
                      final int widthInPx,
                      final int heightInPx,
                      final Command callback ) {
        this.callback = callback;

        this.uuid = diagramRepresentation.getUUID();

        view
            .setUUID( uuid )
            .setItemTitle( diagramRepresentation.getTitle() );

        final String thumbData = diagramRepresentation.getThumbImageData();

        if ( isEmpty( thumbData ) ) {

            final Collection<ShapeSet> shapeSets = shapeManager.getShapeSets();
            final ShapeSet shapeSet = ShapeUtils.getShapeSet( shapeSets, diagramRepresentation.getShapeSetId() );
            final String defSetId = shapeSet.getDefinitionSetId();
            final SafeUri thumbUri = shapeManager.getThumbnail( defSetId );

            view.setThumbUri( thumbUri );

        } else {

            view.setThumbData( thumbData );
        }

        view.setItemPxSize( widthInPx, heightInPx  );

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

    private boolean isEmpty( final String s ) {
        return s == null || s.trim().length() == 0;
    }

    private void log( final Level level, final String message ) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log( level, message );
        }
    }

}
