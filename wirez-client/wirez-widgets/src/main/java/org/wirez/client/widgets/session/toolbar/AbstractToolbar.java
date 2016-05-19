package org.wirez.client.widgets.session.toolbar;

import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.uberfire.client.mvp.UberView;
import org.uberfire.mvp.Command;
import org.wirez.client.widgets.session.toolbar.command.AbstractToolbarCommand;
import org.wirez.client.widgets.session.toolbar.event.DisableToolbarCommandEvent;
import org.wirez.client.widgets.session.toolbar.event.EnableToolbarCommandEvent;
import org.wirez.client.widgets.session.toolbar.item.AbstractToolbarItem;
import org.wirez.core.client.session.CanvasSession;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;

public abstract class AbstractToolbar<S extends CanvasSession> implements IsWidget {

    private static Logger LOGGER = Logger.getLogger(AbstractToolbar.class.getName());
    
    protected final List<ToolbarCommand<S>> commands = new LinkedList<>();
    protected final List<AbstractToolbarItem<S>> items = new LinkedList<>();
    protected S session;
    
    public interface View<S extends CanvasSession> extends UberView<AbstractToolbar> {

        View addItem( IsWidget toolbarItemView );

        View show();

        View hide();
        
        View clear();
        
        void destroy();
        
    }

    Instance<AbstractToolbarItem<S>> toolbarItems;
    View<S> view;
    
    @Inject
    public AbstractToolbar(final Instance<AbstractToolbarItem<S>> toolbarItems,
                           final View<S> view) {
        this.toolbarItems = toolbarItems;
        this.view = view;
    }

    public void doInit() {
        view.init(this);
    }

    public void addCommand( final ToolbarCommand<S> item) {
        commands.add( item );
    }
    
    public void configure(final S session, final ToolbarCommandCallback<?> callback  ) {
        this.session = session;
        
        for ( final ToolbarCommand<S> command : commands ) {

            final Command clickHandler = () -> command.execute( callback );
            final AbstractToolbarItem<S> toolbarItem = toolbarItems.get();
            toolbarItem.setUUID( ( (AbstractToolbarCommand) command).getUuid() );
            toolbarItem.show( session, command, clickHandler );
            view.addItem( toolbarItem.asWidget() );
            items.add( toolbarItem );
            
        }
        
        afterDraw();
        
        show();
     
    }

    private void afterDraw() {
        for ( final ToolbarCommand<S> command : commands ) {
            ( (AbstractToolbarCommand) command ).afterDraw();
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
    public Widget asWidget() {
        return view.asWidget();
    }

    void onEnableCommandEvent(@Observes EnableToolbarCommandEvent enableToolbarCommandEvent) {
        checkNotNull("enableToolbarCommandEvent", enableToolbarCommandEvent);

        final ToolbarCommand<?> command = enableToolbarCommandEvent.getCommand();
        final AbstractToolbarItem<S> item = getItem( command );
        
        if ( null != item ) {
            item.enable();
        }
        
    }

    void onDisableCommandEvent(@Observes DisableToolbarCommandEvent disableToolbarCommandEvent) {
        checkNotNull("disableToolbarCommandEvent", disableToolbarCommandEvent);

        final ToolbarCommand<?> command = disableToolbarCommandEvent.getCommand();
        final AbstractToolbarItem<S> item = getItem( command );

        if ( null != item ) {
            item.disable();
        }

    }
    
    protected AbstractToolbarItem<S> getItem( final ToolbarCommand<?> command ) {
        final String uuid = ( (AbstractToolbarCommand) command).getUuid();
        for ( final AbstractToolbarItem<S> item : items ) {
            if ( uuid.equals( item.getUUID() ) ) {
                return item;
            }
        }
        return null;
    }
    
    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }

}
