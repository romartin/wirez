package org.kie.workbench.common.stunner.client.widgets.session.toolbar.impl;

import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.kie.workbench.common.stunner.client.widgets.session.toolbar.item.AbstractToolbarItem;
import org.uberfire.mvp.Command;
import org.kie.workbench.common.stunner.client.widgets.session.toolbar.Toolbar;
import org.kie.workbench.common.stunner.client.widgets.session.toolbar.ToolbarCommand;
import org.kie.workbench.common.stunner.client.widgets.session.toolbar.ToolbarCommandCallback;
import org.kie.workbench.common.stunner.client.widgets.session.toolbar.ToolbarView;
import org.kie.workbench.common.stunner.client.widgets.session.toolbar.command.AbstractToolbarCommand;
import org.kie.workbench.common.stunner.core.client.session.CanvasSession;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractToolbar<S extends CanvasSession> implements Toolbar<S>, IsWidget {

    private static Logger LOGGER = Logger.getLogger( AbstractToolbar.class.getName() );

    protected final List<ToolbarCommand<S>> commands = new LinkedList<>();
    protected final List<AbstractToolbarItem<S>> items = new LinkedList<>();
    protected S session;

    Instance<AbstractToolbarItem<S>> toolbarItems;
    ToolbarView<S> view;

    @Inject
    public AbstractToolbar( final Instance<AbstractToolbarItem<S>> toolbarItems,
                            final ToolbarView<S> view ) {
        this.toolbarItems = toolbarItems;
        this.view = view;
    }

    public void doInit() {
        view.init( this );
    }

    public void addCommand( final ToolbarCommand<S> item ) {
        commands.add( item );
    }

    public void initialize( final S session, final ToolbarCommandCallback<?> callback ) {
        this.session = session;

        for ( final ToolbarCommand<S> command : commands ) {

            final Command clickHandler = () -> command.execute( callback );
            final AbstractToolbarItem<S> toolbarItem = toolbarItems.get();
            toolbarItem.setUUID( ( ( AbstractToolbarCommand ) command ).getUuid() );

            view.addItem( toolbarItem.asWidget() );
            items.add( toolbarItem );

            toolbarItem.show( this, session, command, clickHandler );

        }

        afterDraw();

        show();

    }

    private void afterDraw() {
        for ( final ToolbarCommand<S> command : commands ) {
            ( ( AbstractToolbarCommand ) command ).afterDraw();
        }
    }

    public void show() {

        view.show();

    }

    public void hide() {

        view.hide();

    }

    public void destroy() {

        for ( final ToolbarCommand<S> c : commands ) {
            c.destroy();
        }
        commands.clear();


        for ( final AbstractToolbarItem<S> item : items ) {
            item.destroy();
        }
        items.clear();

        view.destroy();

        this.view = null;
        this.session = null;
    }

    @Override
    public ToolbarView<S> getView() {
        return view;
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }

    public void disable( final ToolbarCommand<S> command ) {

        final AbstractToolbarItem<S> item = getItem( command );

        if ( null != item ) {
            item.disable();
        }

    }

    public void enable( final ToolbarCommand<S> command ) {

        final AbstractToolbarItem<S> item = getItem( command );

        if ( null != item ) {
            item.enable();
        }

    }

    protected AbstractToolbarItem<S> getItem( final ToolbarCommand<?> command ) {
        final String uuid = ( ( AbstractToolbarCommand ) command ).getUuid();
        for ( final AbstractToolbarItem<S> item : items ) {
            if ( uuid.equals( item.getUUID() ) ) {
                return item;
            }
        }
        return null;
    }

    private void log( final Level level, final String message ) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log( level, message );
        }
    }

}
