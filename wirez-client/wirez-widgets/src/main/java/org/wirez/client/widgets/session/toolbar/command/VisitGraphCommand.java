package org.wirez.client.widgets.session.toolbar.command;

import org.gwtbootstrap3.client.ui.constants.IconType;
import org.wirez.client.widgets.session.toolbar.ToolbarCommandCallback;
import org.wirez.client.widgets.session.toolbar.event.DisableToolbarCommandEvent;
import org.wirez.client.widgets.session.toolbar.event.EnableToolbarCommandEvent;
import org.wirez.core.client.animation.Highlight;
import org.wirez.core.client.animation.ShapeAnimation;
import org.wirez.core.client.session.impl.DefaultCanvasReadOnlySession;
import org.wirez.core.client.util.CanvasHighlightVisitor;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Dependent
public class VisitGraphCommand extends AbstractToolbarCommand<DefaultCanvasReadOnlySession> {

    ShapeAnimation highlightAnimation;
    
    @Inject
    public VisitGraphCommand(final Event<EnableToolbarCommandEvent> enableToolbarCommandEvent,
                             final Event<DisableToolbarCommandEvent> disableToolbarCommandEvent,
                             final @Highlight ShapeAnimation highlightAnimation) {
        super( enableToolbarCommandEvent, disableToolbarCommandEvent );
        this.highlightAnimation = highlightAnimation;
    }

    @Override
    public IconType getIcon() {
        return IconType.EYE;
    }

    @Override
    public String getCaption() {
        return null;
    }

    @Override
    public String getTooltip() {
        return "Visit the graph";
    }

    @Override
    public <T> void execute(final ToolbarCommandCallback<T> callback) {

        new CanvasHighlightVisitor(session.getCanvasHandler(), highlightAnimation)
                .run(() -> {
                    if (null != callback) {
                        callback.onSuccess(null);
                    }
                });
    }

}
