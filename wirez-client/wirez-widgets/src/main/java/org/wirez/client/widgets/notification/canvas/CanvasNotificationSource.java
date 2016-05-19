package org.wirez.client.widgets.notification.canvas;

public final class CanvasNotificationSource {
    
    private final String commandRaw;
    private final String resultRaw;

    CanvasNotificationSource(final String commandRaw,
                                    final String resultRaw) {
        this.commandRaw = commandRaw;
        this.resultRaw = resultRaw;
    }

    public String getCommandRaw() {
        return commandRaw;
    }

    public String getResultRaw() {
        return resultRaw;
    }

    @Override
    public String toString() {
        return "[Command=" + commandRaw + ", Result=" + resultRaw +"] ";
    }
}
