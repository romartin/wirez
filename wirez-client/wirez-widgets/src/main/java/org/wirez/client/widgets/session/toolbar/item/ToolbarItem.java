package org.wirez.client.widgets.session.toolbar.item;

import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.ui.Widget;
import org.wirez.core.client.session.impl.DefaultCanvasFullSession;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

@Dependent
public class ToolbarItem extends AbstractToolbarItem<DefaultCanvasFullSession> {

    private static Logger LOGGER = Logger.getLogger(ToolbarItem.class.getName());
    
    @Inject
    public ToolbarItem(final View view) {
        super( view );
    }

    @PostConstruct
    public void init() {
        super.doInit();
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }

    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }

}
