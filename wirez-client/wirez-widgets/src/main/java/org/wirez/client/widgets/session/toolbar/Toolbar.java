package org.wirez.client.widgets.session.toolbar;

import com.google.gwt.logging.client.LogConfiguration;
import org.wirez.client.widgets.session.toolbar.item.AbstractToolbarItem;
import org.wirez.core.client.session.impl.DefaultCanvasFullSession;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO: Drop-down & complex tree buttons support.
@Dependent
public class Toolbar extends AbstractToolbar<DefaultCanvasFullSession> {

    private static Logger LOGGER = Logger.getLogger(Toolbar.class.getName());
    
    public interface View extends AbstractToolbar.View<DefaultCanvasFullSession> {
        
    }
    
    @Inject
    public Toolbar(final Instance<AbstractToolbarItem<DefaultCanvasFullSession>> toolbarItems,
                   final View view) {
        super( toolbarItems, view );
    }

    @PostConstruct
    public void init() {
        super.doInit();
    }

    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }

}
