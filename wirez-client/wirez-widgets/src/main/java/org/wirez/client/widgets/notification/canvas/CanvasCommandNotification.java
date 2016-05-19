package org.wirez.client.widgets.notification.canvas;

import org.wirez.client.widgets.notification.AbstractNotification;
import org.wirez.client.widgets.notification.Notification;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.command.CanvasViolation;
import org.wirez.core.command.Command;
import org.wirez.core.command.CommandResult;
import org.wirez.core.command.batch.BatchCommandResult;
import org.wirez.core.diagram.Diagram;
import org.wirez.core.util.UUID;

import java.util.Iterator;

public final class CanvasCommandNotification 
        extends AbstractNotification<CanvasNotificationSource, CanvasNotificationContext>{

    CanvasCommandNotification(final String uuid, 
                                     final Type type, 
                                     final CanvasNotificationSource source, 
                                     final CanvasNotificationContext context) {
        super(uuid, type, source, context);
    }
    
    
    
    /*
    private final Command<H, CanvasViolation> command;
    private final CommandResult<CanvasViolation> result;
     */
    
    public static class CanvasCommandNotificationBuilder<H extends CanvasHandler> {

        H canvasHander;
        Command<H, CanvasViolation> command;
        CommandResult<CanvasViolation> result;

        public CanvasCommandNotificationBuilder<H> canvasHander(H canvasHander) {
            this.canvasHander = canvasHander;
            return this;
        }

        public CanvasCommandNotificationBuilder<H> command(final Command<H, CanvasViolation> command) {
            this.command = command;
            return this;
        }

        public CanvasCommandNotificationBuilder<H> result(final CommandResult<CanvasViolation> result) {
            this.result = result;
            return this;
        }

        public CanvasCommandNotification build() {
            
            if ( null == command ) {
                throw new IllegalArgumentException( "Missing notification's command." );
            }
            
            final String resultMsg = getResultMessage( result );
            final CanvasNotificationSource source = new CanvasNotificationSource( command.toString(),  resultMsg );
            
            final Diagram diagram = canvasHander.getDiagram();
            final String diagramUUID = diagram.getUUID();
            final String title = diagram.getSettings().getTitle();
            final CanvasNotificationContext context = 
                    new CanvasNotificationContext( canvasHander.toString(),  diagramUUID, title );
            
            final Notification.Type type = getNotificationType( result );
            
            return new CanvasCommandNotification( UUID.uuid(), type, source, context );
            
        }

        private Notification.Type getNotificationType( final CommandResult<CanvasViolation> result ) {
            return CommandResult.Type.ERROR.equals( result.getType() )
                    ? Notification.Type.ERROR : Notification.Type.INFO;
        }

        @SuppressWarnings("unchecked")
        private String getResultMessage( final CommandResult<CanvasViolation> result ) {
            
            if ( null != result && result instanceof BatchCommandResult ) {
                
                return getBatchCommandResultMessage( (BatchCommandResult<CanvasViolation>) result );
                
            } else  if ( null != result ) {
                
                return getCommandResultMessage( result );
            }
            
            return "-- No Message --";
        }

        private String getCommandResultMessage(final CommandResult<CanvasViolation> results) {
            return results.getMessage();
        }

        private String getBatchCommandResultMessage(final BatchCommandResult<CanvasViolation> results) {
            boolean hasError = false;
            boolean hasWarn = false;
            final Iterator<CommandResult<CanvasViolation>> iterator = results.iterator();
            int c = 0;
            String message = null;
            while (iterator.hasNext()) {
                final CommandResult<CanvasViolation> result = iterator.next();
                if (CommandResult.Type.ERROR.equals(result.getType())) {
                    hasError = true;
                    message = result.getMessage();
                    c++;
                } else if (CommandResult.Type.WARNING.equals(result.getType())) {
                    hasWarn = true;
                    if ( !hasError ) {
                        message = result.getMessage();
                    }
                } else {
                    if ( !hasError && !hasWarn ) {
                        message = result.getMessage();
                    }
                }
            }

            if ( c > 1 ) {
                message = "Found " + c + " violations";
            }

            return message;

        }
        
    }
    
}
