package org.wirez.lienzo.toolbox;

import com.ait.lienzo.client.core.event.NodeMouseEnterEvent;
import com.ait.lienzo.client.core.event.NodeMouseExitEvent;
import com.google.gwt.user.client.Timer;
import com.google.gwtmockito.GwtMockitoTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.wirez.lienzo.toolbox.HoverTimer.Actions;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class HoverTimerTest {
    @Mock
    private Actions actions;
    @Mock
    Timer timer;
    @Mock
    private NodeMouseExitEvent exitEvent;
    @Mock
    NodeMouseEnterEvent enterEvent;

    private HoverTimer hoverTimer;

    @Before
    public void setUp() {
        when(actions.isReadyToHide()).thenReturn(true);
        hoverTimer = spy(new HoverTimer(actions));
        when(hoverTimer.createTimer()).thenReturn(timer);
    }

    @Test
    public void testFirstMouseExit() {
        hoverTimer.onNodeMouseExit(exitEvent);
        verify(actions).isReadyToHide();
        verify(timer).schedule(200);
    }

    @Test
    public void testNotReadyMouseExit() {
        when(actions.isReadyToHide()).thenReturn(false);
        hoverTimer.onNodeMouseExit(exitEvent);
        verify(actions).isReadyToHide();
        verify(timer, times(0)).schedule(anyInt());
    }

    @Test
    public void testTwiceMouseExit() {
        hoverTimer.onNodeMouseExit(exitEvent);
        hoverTimer.onNodeMouseExit(exitEvent);
        verify(actions, times(2)).isReadyToHide();
        verify(timer, times(1)).schedule(200);
    }

    @Test
    public void testMouseEnter() {
        hoverTimer.onNodeMouseExit(exitEvent);
        hoverTimer.onNodeMouseEnter(enterEvent);
        verify(timer).cancel();
        verify(actions).onMouseEnter();
        assertNull(hoverTimer.getTimer());
    }

    @Test
    public void testMouseEnterWithoutExit() {
        hoverTimer.onNodeMouseEnter(enterEvent);
        verify(timer, times(0)).cancel();
        verify(actions).onMouseEnter();
        assertNull(hoverTimer.getTimer());
    }

    @Test
    public void testTimer() {
        Timer t = new HoverTimer(actions).createTimer();
        t.run();
        verify(actions).onMouseExit();
    }
}
