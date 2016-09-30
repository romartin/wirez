package org.kie.workbench.common.stunner.core.client.canvas.util;

import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.Timer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CanvasLoadingObserver {

    private static Logger LOGGER = Logger.getLogger( CanvasLoadingObserver.class.getName() );

    private static final int TIMEOUT_DURATION = 5000;

    public interface Callback {

        void onLoadingStarted();

        void onLoadingCompleted();

    }

    private Callback callback;
    private Timer timeout;
    private int duration = TIMEOUT_DURATION;

    public void setLoadingObserverCallback( Callback callback ) {
        this.callback = callback;
    }

    public void setTimeoutDuration( final int duration ) {
        this.duration = duration;
    }

    public void loadingStarted() {

        if ( null != callback ) {

            callback.onLoadingStarted();

            log( "Starting timeout..." );
            this.timeout = new Timer() {
                @Override
                public void run() {
                    logWarn( "Loading timeout timer fired after " + duration + "sec... something went wrong?" );
                    loadingCompleted();
                }
            };

            timeout.schedule( duration );
        }

    }

    public void loadingCompleted() {

        clearTimeout();

        if ( null != callback ) {

            callback.onLoadingCompleted();

        }

    }

    private void clearTimeout() {

        if ( null != this.timeout ) {

            log( "Clearing timeout..." );
            if ( this.timeout.isRunning() ) {
                this.timeout.cancel();
            }

            this.timeout = null;
        }

    }

    private static void log( final String message ) {
        log( Level.FINE, message );
    }

    private static void logWarn( final String message ) {
        log( Level.WARNING, message );
    }

    private static void log( final Level level,
                             final String message ) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log( level, message );
        }
    }

}
