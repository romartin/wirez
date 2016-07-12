package org.wirez.client.widgets.composite.navigator;

import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.wirez.client.widgets.canvas.preview.CanvasPreview;
import org.wirez.client.widgets.card.Card;
import org.wirez.client.widgets.card.CardTriggerBuilder;
import org.wirez.client.widgets.explorer.tree.TreeExplorer;
import org.wirez.core.client.canvas.CanvasHandler;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO: unused for now - improve performance by only processing and adding into the DOM tree the current card side.
@Dependent
public class Navigator implements IsWidget {

    private static Logger LOGGER = Logger.getLogger(Navigator.class.getName());

    Card card;
    CanvasPreview canvasPreview;
    TreeExplorer treeExplorer;

    @Inject
    public Navigator(final Card card,
                     final CanvasPreview canvasPreview,
                     final TreeExplorer treeExplorer) {
        this.card = card;
        this.canvasPreview = canvasPreview;
        this.treeExplorer = treeExplorer;
    }
    
    @AfterInitialization
    public void init() {
        card.add( CardTriggerBuilder.buildExchangeTrigger( "Switch view" , () -> card.flip()) );
    }

    @Override
    public Widget asWidget() {
        return card.asWidget();
    }
    
    public void show(final CanvasHandler canvasHandler) {
        
        // Initialize the widgets.
        canvasPreview.show( canvasHandler.getCanvas(), 400, 400 );
        treeExplorer.show( canvasHandler );
        
        // Show the view.
        card.show(canvasPreview.asWidget(), treeExplorer.asWidget() );
        
    }
    
    public void clear() {
        canvasPreview.clear();
        treeExplorer.clear();
    }
    
    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }

}
