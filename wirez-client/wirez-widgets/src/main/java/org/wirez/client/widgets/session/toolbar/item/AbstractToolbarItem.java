package org.wirez.client.widgets.session.toolbar.item;

import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.uberfire.client.mvp.UberView;
import org.uberfire.mvp.Command;
import org.wirez.client.widgets.session.toolbar.Toolbar;
import org.wirez.client.widgets.session.toolbar.ToolbarCommand;
import org.wirez.core.client.session.CanvasSession;

import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractToolbarItem<S extends CanvasSession> implements IsWidget {

    private static Logger LOGGER = Logger.getLogger(AbstractToolbarItem.class.getName());
    
    public interface View extends UberView<AbstractToolbarItem> {

        View setIcon(IconType icon);

        View setCaption(String caption);
        
        View setTooltip(String tooltip);
        
        View setClickHandler(Command command);
        
        View setEnabled(boolean enabled);
        
        void destroy();
        
    }

    View view;
    
    private String uuid;
    
    @Inject
    public AbstractToolbarItem(final View view) {
        this.view = view;
    }

    public void doInit() {
        view.init(this);
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }

    public void setUUID(final String uuid) {
        this.uuid = uuid;
    }

    public String getUUID() {
        return uuid;
    }

    public void show( final Toolbar<S> toolbar,
                      final S session,
                      final ToolbarCommand<S> command,
                      final Command clickHandler ) {

        // Initialize the command with the current session.
        command.initialize( toolbar, session );

        final IconType icon = command.getIcon();
        final String caption = command.getCaption();
        
        if ( icon != null ) {

            view.setIcon( command.getIcon() );

        } else {

            view.setCaption( caption );

        }
        
        view.setTooltip( command.getTooltip() );
        
        view.setClickHandler( clickHandler );
        
    }
    
    public void enable() {
        
        view.setEnabled( true );
    }
    
    public void disable() {
        
        view.setEnabled( false );
        
    }
    
    public void destroy() {
        view.destroy();
    }

    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }

}
