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

package org.kie.workbench.common.stunner.client.lienzo.canvas.wires;

import com.ait.lienzo.client.core.shape.wires.IConnectionAcceptor;
import com.ait.lienzo.client.core.shape.wires.IContainmentAcceptor;
import com.ait.lienzo.client.core.shape.wires.IDockingAcceptor;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.google.gwt.logging.client.LogConfiguration;
import org.kie.workbench.common.stunner.client.lienzo.Lienzo;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvas;
import org.kie.workbench.common.stunner.core.client.canvas.Layer;
import org.kie.workbench.common.stunner.core.client.canvas.event.CanvasClearEvent;
import org.kie.workbench.common.stunner.core.client.canvas.event.CanvasDrawnEvent;
import org.kie.workbench.common.stunner.core.client.canvas.event.CanvasFocusedEvent;
import org.kie.workbench.common.stunner.core.client.canvas.event.registration.CanvasShapeAddedEvent;
import org.kie.workbench.common.stunner.core.client.canvas.event.registration.CanvasShapeRemovedEvent;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Lienzo "Wires" based Canvas .
 */
public abstract class WiresCanvas extends AbstractCanvas<WiresCanvas.View> {

    public static final String WIRES_CANVAS_GROUP_ID = "stnner.wiresCanvas";

    private static Logger LOGGER = Logger.getLogger(WiresCanvas.class.getName());

    public interface View extends AbstractCanvas.View<LienzoPanel> {

        View setConnectionAcceptor(IConnectionAcceptor connectionAcceptor);
        
        View setContainmentAcceptor(IContainmentAcceptor containmentAcceptor);
        
        View setDockingAcceptor(IDockingAcceptor dockingAcceptor);
        
        WiresManager getWiresManager();
        
    }
    
    @Inject
    public WiresCanvas(final Event<CanvasClearEvent> canvasClearEvent,
                       final Event<CanvasShapeAddedEvent> canvasShapeAddedEvent,
                       final Event<CanvasShapeRemovedEvent> canvasShapeRemovedEvent,
                       final Event<CanvasDrawnEvent> canvasDrawnEvent,
                       final Event<CanvasFocusedEvent> canvasFocusedEvent,
                       final @Lienzo Layer layer,
                       final View view) {
        super( canvasClearEvent, canvasShapeAddedEvent, canvasShapeRemovedEvent, 
                canvasDrawnEvent, canvasFocusedEvent, layer, view );
    }

    public WiresManager getWiresManager() {
        return view.getWiresManager();
    }

    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }
    
}
