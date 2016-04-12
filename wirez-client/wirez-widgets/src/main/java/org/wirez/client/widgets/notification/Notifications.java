/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wirez.client.widgets.notification;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import org.gwtbootstrap3.extras.notify.client.constants.NotifyType;
import org.gwtbootstrap3.extras.notify.client.ui.Notify;
import org.gwtbootstrap3.extras.notify.client.ui.NotifySettings;
import org.uberfire.client.mvp.UberView;
import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.command.batch.BatchCommandResult;
import org.wirez.core.api.event.command.AbstractGraphCommandEvent;
import org.wirez.core.api.event.local.CommandExecutedEvent;
import org.wirez.core.api.event.local.IsCommandAllowedEvent;
import org.wirez.core.api.rule.RuleViolation;
import org.wirez.core.api.util.UUID;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@Dependent
public class Notifications implements IsWidget {

    public interface View extends UberView<Notifications> {
        
        View setColumnSortHandler(ColumnSortEvent.ListHandler<Notification> sortHandler);
        
        View addColumn(com.google.gwt.user.cellview.client.Column<Notification, String> column,
                       String name);
        
        View removeColumn(int index);
        
        int getColumnCount();
        
        View redraw();
        
        View clear();
        
    }
    
    View view;
    final ListDataProvider<Notification> logsProvider = new ListDataProvider<Notification>();
    private boolean notifyErrors = true;

    @Inject
    public Notifications(final View view) {
        this.view = view;
    }

    @PostConstruct
    public void init() {
        view.init(this);
        buildViewColumns();
    }

    public void setNotifyErrors(boolean notifyErrors) {
        this.notifyErrors = notifyErrors;
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }
    
    public void add(final Notification notification) {
        showNotificationPopup(notification);
        if ( null != notification ) {
            addLogEntry(notification);
            view.redraw();
        }
    }
    
    private void showNotificationPopup(final Notification notification) {
        if ( notifyErrors && Notification.Type.ERROR.equals(notification.getType()) ) {
            NotifySettings settings = NotifySettings.newSettings();
            settings.makeDefault();
            settings.setType(NotifyType.DANGER);
            settings.setDelay(5000);
            settings.setTimer(100);
            settings.setAllowDismiss(true);
            final String text = getNotificationText(notification);
            Notify.notify(text, settings);
        }
    }
    
    public void clear() {
        logsProvider.getList().clear();
        view.clear();
    }
    
    /*  ******************************************************************************************************
                                 VIEW CALLBACKS 
     ****************************************************************************************************** */
    
    final ProvidesKey<Notification> KEY_PROVIDER = new ProvidesKey<Notification>() {
        @Override
        public Object getKey(final Notification item) {
            return item == null ? null : item.getNotificationUUID();
        }
    };
    
    /**
     * View callback for getting the list.
     */
    void addDataDisplay(final HasData<Notification> display) {
        logsProvider.addDataDisplay(display);
    }

    protected void buildViewColumns() {
        int columnCount = view.getColumnCount();
        while (columnCount > 0) {
            view.removeColumn(0);
            columnCount = view.getColumnCount();
        }

        // Attach a column sort handler to the ListDataProvider to sort the list.
        ColumnSortEvent.ListHandler<Notification> sortHandler = new ColumnSortEvent.ListHandler<Notification>(logsProvider.getList());
        view.setColumnSortHandler(sortHandler);

        // Log's type.
        final com.google.gwt.user.cellview.client.Column<Notification, String> typeColumn = createTypeColumn(sortHandler);
        if (typeColumn != null) {
            view.addColumn(typeColumn, "Type");
        }

        // Log element's UUID.
        final com.google.gwt.user.cellview.client.Column<Notification, String> contextColumn = createContextColumn(sortHandler);
        if (contextColumn != null) {
            view.addColumn(contextColumn, "Context");
        }

        // Log's message.
        final com.google.gwt.user.cellview.client.Column<Notification, String> messageColumn = createMessageColumn(sortHandler);
        if (messageColumn != null) {
            view.addColumn(messageColumn, "Message");
        }
        
    }
    
    
    private void addLogEntry(final Notification entry) {
        List<Notification> logs = logsProvider.getList();
        logs.remove(entry);
        logs.add(entry);
    }


    private com.google.gwt.user.cellview.client.Column<Notification, String> createTypeColumn(ColumnSortEvent.ListHandler<Notification> sortHandler) {
        // Log type..
        final Cell<String> typeCell = new TextCell();
        final com.google.gwt.user.cellview.client.Column<Notification, String> typeColumn = new com.google.gwt.user.cellview.client.Column<Notification, String>(
                typeCell) {
            @Override
            public String getValue(final Notification object) {
                return object.getType() != null ? object.getType().name() : "-- No type --";
            }
        };
        typeColumn.setSortable(true);
        sortHandler.setComparator(typeColumn, new Comparator<Notification>() {
            @Override
            public int compare(Notification o1, Notification o2) {
                return o1.getType().compareTo(o2.getType());
            }
        });

        return typeColumn;
    }

    private com.google.gwt.user.cellview.client.Column<Notification, String> createContextColumn(ColumnSortEvent.ListHandler<Notification> sortHandler) {
        // Log element's context.
        final Cell<String> contextCell = new TextCell();
        final com.google.gwt.user.cellview.client.Column<Notification, String> contextColumn = new com.google.gwt.user.cellview.client.Column<Notification, String>(
                contextCell) {
            @Override
            public String getValue(final Notification object) {
                return object.getSource() != null ? object.getSource().toString() : "-- No Context --";
            }
        };
        contextColumn.setSortable(false);
        
        return contextColumn;
    }

    private com.google.gwt.user.cellview.client.Column<Notification, String> createMessageColumn(ColumnSortEvent.ListHandler<Notification> sortHandler) {
        // Log message.
        final Cell<String> messageCell = new TextCell();
        final com.google.gwt.user.cellview.client.Column<Notification, String> messageColumn = new com.google.gwt.user.cellview.client.Column<Notification, String>(
                messageCell) {
            @Override
            public String getValue(final Notification object) {
                return getNotificationText(object);
            }
        };
        messageColumn.setSortable(false);
        
        return messageColumn;
    }
    
    private String getNotificationText(final Notification object) {
        if ( null != object.getContext() && object.getContext() instanceof BatchCommandResult) {
            return getMessage((BatchCommandResult<RuleViolation>) object.getContext());
        } else  if ( null != object.getContext() && object.getContext() instanceof CommandResult) {
            return getMessage((CommandResult<RuleViolation>) object.getContext());
        }
        return object.getContext() != null ? object.getContext().toString(): "-- No Message --";
    }

    private String getMessage(final CommandResult<RuleViolation> results) {
        return results.getMessage();
    }
    
    private String getMessage(final BatchCommandResult<RuleViolation> results) {
        boolean hasError = false;
        boolean hasWarn = false;
        final Iterator<CommandResult<RuleViolation>> iterator = results.iterator();
        int c = 0;
        String message = null;
        while (iterator.hasNext()) {
            final CommandResult<RuleViolation> result = iterator.next();
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

    void onGraphCommandAllowed(@Observes IsCommandAllowedEvent isCommandAllowedEvent) {
        Notification notification = translate( isCommandAllowedEvent );
        add( notification );
    }

    void onGraphCommandExecued(@Observes CommandExecutedEvent commandExecutedEvent) {
        Notification notification = translate( commandExecutedEvent );
        add( notification );
    }
    
    private Notification translate(final AbstractGraphCommandEvent commandExecutedEvent) {

        if ( null != commandExecutedEvent ) {

            final Command<?, ?> command = commandExecutedEvent.getCommand();
            final CommandResult<?> result = commandExecutedEvent.getResult();
            Notification.Type type = CommandResult.Type.ERROR.equals( result.getType() )
                    ? Notification.Type.ERROR : Notification.Type.INFO;
            return new NotificationImpl<>( UUID.uuid(), type, command, result );

        }

        return  null;
    }
    
}
