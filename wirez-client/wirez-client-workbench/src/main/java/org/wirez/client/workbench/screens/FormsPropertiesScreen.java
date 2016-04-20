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

package org.wirez.client.workbench.screens;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import org.livespark.formmodeler.renderer.client.DynamicFormRenderer;
import org.uberfire.client.annotations.*;
import org.uberfire.client.mvp.PlaceManager;
import org.uberfire.client.workbench.events.ChangeTitleWidgetEvent;
import org.uberfire.client.workbench.widgets.common.ErrorPopupPresenter;
import org.uberfire.lifecycle.OnClose;
import org.uberfire.lifecycle.OnOpen;
import org.uberfire.lifecycle.OnStartup;
import org.uberfire.mvp.Command;
import org.uberfire.mvp.PlaceRequest;
import org.uberfire.workbench.model.menu.Menus;
import org.wirez.client.workbench.event.CanvasScreenStateChangedEvent;
import org.wirez.core.api.graph.Element;
import org.wirez.core.client.ClientDefinitionManager;
import org.wirez.core.client.canvas.ShapeState;
import org.wirez.core.client.canvas.event.ShapeStateModifiedEvent;
import org.wirez.core.client.canvas.wires.WiresCanvasHandler;
import org.wirez.core.client.shape.Shape;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;

@Dependent
@WorkbenchScreen(identifier = FormsPropertiesScreen.SCREEN_ID )
public class FormsPropertiesScreen {

    public static final String SCREEN_ID = "FormsPropertiesScreen";

    @Inject
    ClientDefinitionManager clientDefinitionManager;

    @Inject
    private DynamicFormRenderer formRenderer;

    @Inject
    ErrorPopupPresenter errorPopupPresenter;

    @Inject
    PlaceManager placeManager;

    @Inject
    Event<ChangeTitleWidgetEvent> changeTitleNotification;

    private PlaceRequest placeRequest;
    private WiresCanvasHandler canvasHandler;

    @PostConstruct
    public void init() {

    }

    @OnStartup
    public void onStartup(final PlaceRequest placeRequest) {
        this.placeRequest = placeRequest;
    }

    @OnOpen
    public void onOpen() {

    }

    @OnClose
    public void onClose() {

    }


    @WorkbenchMenu
    public Menus getMenu() {
        return null;
    }

    private void showError(final String message) {
        errorPopupPresenter.showMessage(message);
    }

    @WorkbenchPartTitle
    public String getTitle() {
        return "Properties";
    }

    @WorkbenchPartView
    public IsWidget getWidget() {
        return formRenderer.asWidget();
    }

    @WorkbenchContextId
    public String getMyContextRef() {
        return "wirezPropertiesScreenContext";
    }


    void onWirezCanvasOpened(@Observes CanvasScreenStateChangedEvent stateChangedEvent) {
        checkNotNull("stateChangedEvent", stateChangedEvent);

        if (CanvasScreen.CanvasScreenState.ACTIVE.equals(stateChangedEvent.getState())) {
            this.canvasHandler = (WiresCanvasHandler) stateChangedEvent.getCanvasHandler();
        } else {
            doClear();
        }
    }

    void onCanvasShapeStateModifiedEvent(@Observes ShapeStateModifiedEvent event) {
        checkNotNull("event", event);
        final ShapeState state = event.getState();
        final Shape shape = event.getShape();
        if ( shape != null ) {
            // If shape exist, show the properties for the underlying model element.
            final String shapeUUID = shape.getUUID();
            final Element<? extends org.wirez.core.api.graph.content.view.View<?>> element = this.canvasHandler.getGraphIndex().get(shapeUUID);
            if (element != null && ShapeState.SELECTED.equals(state)) {
                formRenderer.renderDefaultForm( element.getContent().getDefinition(), new Command() {
                    @Override
                    public void execute() {
                        formRenderer.addFieldChangeHandler( ( fieldName, newValue ) ->
                                FormsPropertiesScreen.this.executeUpdateProperty( element, fieldName, newValue ));
                    }
                } );

            } else if (ShapeState.DESELECTED.equals(state)) {
                doClear();
            }
        } else {
            // If shape is null means no shape selected, so show the properties for the underlying graph.
            doClear();
        }

    }

    private void doClear() {

    }

    private void executeUpdateProperty(final Element<? extends org.wirez.core.api.graph.content.view.View<?>> element,
                                       final String propertyId,
                                       final Object value) {

        canvasHandler.execute( canvasHandler.getCommandFactory().UPDATE_PROPERTY(element, propertyId, value) );

    }

    private void executeMove(final Element<? extends org.wirez.core.api.graph.content.view.View<?>> element,
                             final double x,
                             final double y) {

        canvasHandler.execute( canvasHandler.getCommandFactory().UPDATE_POSITION(element, x, y) );

    }

}
