package org.wirez.client.widgets.notification;

public final class NotificationImpl<S, C> implements Notification<S, C> {
    
    private final String uuid;
    private final Type type;
    private final S source;
    private final C context;

    public NotificationImpl(final String uuid, 
                            final Type type, 
                            final S source, 
                            final C context) {
        this.uuid = uuid;
        this.type = type;
        this.source = source;
        this.context = context;
    }

    @Override
    public String getNotificationUUID() {
        return uuid;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public S getSource() {
        return source;
    }

    @Override
    public C getContext() {
        return context;
    }
}
