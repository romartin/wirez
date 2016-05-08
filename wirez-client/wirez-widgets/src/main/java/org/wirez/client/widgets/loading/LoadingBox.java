package org.wirez.client.widgets.loading;

import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.Timer;
import org.wirez.client.widgets.event.WidgetProcessingCompletedEvent;
import org.wirez.client.widgets.event.WidgetProcessingStartedEvent;
import org.wirez.core.client.canvas.event.CanvasProcessingCompletedEvent;
import org.wirez.core.client.canvas.event.CanvasProcessingStartedEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;

@ApplicationScoped
public class LoadingBox {

    private static Logger LOGGER = Logger.getLogger(LoadingBox.class.getName());
    private static final int TIMEOUT = 30000; // 30s.
    
    public interface View {

        View show();
        
        View hide();
        
    }

    View view;
    
    private Timer timer;
    
    @Inject
    public LoadingBox(final View view) {
        this.view = view;
    }

    public void show() {
        
        if ( null != this.timer 
                && this.timer.isRunning() ) {

            return;
            
        }

        startTimer();

        view.show();
        
    }

    public void hide() {
        
        stopTimer();
        
        view.hide();
        
    }
    
    private void startTimer() {
        
        stopTimer();
        
        this.timer = new Timer() {
            @Override
            public void run() {
                log( Level.WARNING, "Loading box - Timeout exceeded!");
                hide();
            }
        };

        timer.schedule(TIMEOUT);
        
    }

    private void stopTimer() {
        
        if ( null != this.timer ) {
            
            if ( this.timer.isRunning() ) {
                this.timer.cancel();
            }
            
            this.timer = null;
        }
        
    }
    
    public void onCanvasProcessingStarted(@Observes CanvasProcessingStartedEvent canvasProcessingStartedEvent) {
        checkNotNull("canvasProcessingStartedEvent", canvasProcessingStartedEvent);
        show();
    }

    public void onCanvasProcessingCompleted(@Observes CanvasProcessingCompletedEvent canvasProcessingCompletedEvent) {
        checkNotNull("canvasProcessingCompletedEvent", canvasProcessingCompletedEvent);
        hide();
    }

    public void onWidgetProcessingStarted(@Observes WidgetProcessingStartedEvent widgetProcessingStartedEvent) {
        checkNotNull("widgetProcessingStartedEvent", widgetProcessingStartedEvent);
        show();
    }

    public void onWidgetProcessingCompleted(@Observes WidgetProcessingCompletedEvent widgetProcessingCompletedEvent) {
        checkNotNull("widgetProcessingCompletedEvent", widgetProcessingCompletedEvent);
        hide();
    }

    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }

}
