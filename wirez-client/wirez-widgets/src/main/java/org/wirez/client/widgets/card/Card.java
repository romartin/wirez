package org.wirez.client.widgets.card;

import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.uberfire.client.mvp.UberView;
import org.uberfire.mvp.Command;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO Load and add into DOM the widget only used in current side .
@Dependent
public class Card implements IsWidget {

    private static Logger LOGGER = Logger.getLogger(Card.class.getName());
    
    public interface View extends UberView<Card> {

        View show( IsWidget front, IsWidget back, Command switchCallback );

        View add( IsWidget widget );
        
        View flip();
        
    }

    View view;
    private boolean isFront = true;
    
    @Inject
    public Card(final View view) {
        this.view = view;
    }

    @PostConstruct
    public void init() {
        view.init(this);
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }
   
    public void show( final IsWidget front, 
                      final IsWidget back, 
                      final Command switchCallback ) {
        
        final Command c = () -> {
            
            Card.this.isFront = !Card.this.isFront;
            
            if ( null != switchCallback ) {
                switchCallback.execute();
            }
            
        };
        
        view.show( front, back, c );
        
    }
    
    public void flip() {
        view.flip();
    }
    
    public void add( IsWidget widget ) {
        view.add( widget );
    }

    public boolean isFront() {
        return isFront;
    }

    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }

}
