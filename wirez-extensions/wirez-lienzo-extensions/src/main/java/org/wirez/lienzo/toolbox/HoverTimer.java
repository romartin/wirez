package org.wirez.lienzo.toolbox;


import com.ait.lienzo.client.core.event.NodeMouseEnterEvent;
import com.ait.lienzo.client.core.event.NodeMouseEnterHandler;
import com.ait.lienzo.client.core.event.NodeMouseExitEvent;
import com.ait.lienzo.client.core.event.NodeMouseExitHandler;
import com.google.gwt.user.client.Timer;

public class HoverTimer implements NodeMouseEnterHandler, NodeMouseExitHandler {

    private final Actions actions;

    public HoverTimer(Actions actions) {
        this.actions = actions;
    }

    public interface Actions {
        void onMouseEnter();

        void onMouseExit();

        boolean isReadyToHide();
    }

    private Timer m_timer;

    protected Timer getTimer() {
        return m_timer;
    }

    @Override
    public void onNodeMouseEnter(NodeMouseEnterEvent event) {
        cancel();
        actions.onMouseEnter();
    }

    private void cancel() {
        if (m_timer != null) {
            m_timer.cancel();
            m_timer = null;
        }
    }

    @Override
    public void onNodeMouseExit(NodeMouseExitEvent event) {
        if (actions.isReadyToHide()) {
            createHideTimer();
        }
    }

    private void createHideTimer() {
        if (m_timer == null) {
            m_timer = createTimer();
            m_timer.schedule(200);
        }
    }

    protected Timer createTimer() {
        return new Timer() {
            @Override
            public void run() {
                actions.onMouseExit();
            }
        };
    }
}
