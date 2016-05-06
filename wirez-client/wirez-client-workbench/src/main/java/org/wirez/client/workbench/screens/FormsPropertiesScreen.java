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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import org.jboss.errai.databinding.client.HasProperties;
import org.jboss.errai.databinding.client.api.DataBinder;
import org.livespark.formmodeler.renderer.client.DynamicFormRenderer;
import org.uberfire.client.annotations.*;
import org.uberfire.client.mvp.PlaceManager;
import org.uberfire.client.workbench.events.ChangeTitleWidgetEvent;
import org.uberfire.client.workbench.widgets.common.ErrorPopupPresenter;
import org.uberfire.lifecycle.OnClose;
import org.uberfire.lifecycle.OnOpen;
import org.uberfire.lifecycle.OnStartup;
import org.uberfire.mvp.PlaceRequest;
import org.uberfire.workbench.model.menu.Menus;
import org.wirez.core.api.graph.Element;
import org.wirez.core.client.ClientDefinitionManager;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.ShapeState;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.event.ShapeStateModifiedEvent;
import org.wirez.core.client.session.CanvasSession;
import org.wirez.core.client.session.event.SessionDisposedEvent;
import org.wirez.core.client.session.event.SessionOpenedEvent;
import org.wirez.core.client.session.event.SessionPausedEvent;
import org.wirez.core.client.session.event.SessionResumedEvent;
import org.wirez.core.client.session.impl.DefaultCanvasFullSession;
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
    CanvasCommandFactory commandFactory;

    @Inject
    private DynamicFormRenderer formRenderer;

    @Inject
    ErrorPopupPresenter errorPopupPresenter;

    @Inject
    PlaceManager placeManager;

    @Inject
    Event<ChangeTitleWidgetEvent> changeTitleNotification;

    private PlaceRequest placeRequest;
    private DefaultCanvasFullSession canvasSession;

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

    private AbstractCanvasHandler getCanvasHandler() {
        return canvasSession != null ? canvasSession.getCanvasHandler() : null;
    }

    void onCanvasShapeStateModifiedEvent(@Observes ShapeStateModifiedEvent event) {
        checkNotNull("event", event);
        final ShapeState state = event.getState();
        final Shape shape = event.getShape();
        if ( shape != null ) {
            // If shape exist, show the properties for the underlying model element.
            final String shapeUUID = shape.getUUID();
            final Element<? extends org.wirez.core.api.graph.content.view.View<?>> element = getCanvasHandler().getGraphIndex().get(shapeUUID);
            if (element != null && ShapeState.SELECTED.equals(state)) {
                final Object definition = element.getContent().getDefinition();
                formRenderer.renderDefaultForm( definition, () -> {
                    formRenderer.addFieldChangeHandler((fieldName, newValue) -> {

                        try {
                            // TODO - Pere: We have to review this. Meanwhile, note that this is working only for properties
                            // that are direct members of the definitions ( ex: Task#width or StartEvent#radius ).
                            // But it's not working for the properties that are inside property sets, for example an error
                            // occurs when updating "documentation", as thisl callback "fieldName" = "documentation", but
                            // in order to obtain the property it should be "general.documentation".
                            final HasProperties hasProperties = (HasProperties) DataBinder.forModel( definition ).getModel();

                            String pId = getModifiedPropertyId( hasProperties, fieldName );

                            FormsPropertiesScreen.this.executeUpdateProperty( element, pId, newValue );

                        } catch ( Exception ex ) {
                            GWT.log( "Something wrong happened refreshing the canvas for field '" + fieldName + "': " + ex.getCause() );
                        }
                    });
                } );

            } else if (ShapeState.DESELECTED.equals(state)) {
                doClear();
            }
        } else {
            // If shape is null means no shape selected, so show the properties for the underlying graph.
            doClear();
        }

    }

    private String getModifiedPropertyId( HasProperties model, String fieldName ) {

        int separatorIndex = fieldName.indexOf( "." );

        // Check if it is a nested property, if it is we must obtain the nested property instead of the root one.
        if ( separatorIndex != -1 ) {
            String rootProperty = fieldName.substring( 0, separatorIndex );
            fieldName = fieldName.substring( separatorIndex + 1);

            Object property = model.get( rootProperty );

            model = (HasProperties) DataBinder.forModel( property ).getModel();

            return getModifiedPropertyId( model, fieldName );
        }

        Object property = model.get( fieldName );

        return clientDefinitionManager.getPropertyAdapter( property.getClass() ).getId( property );
    }



    void onCanvasSessionOpened(@Observes SessionOpenedEvent sessionOpenedEvent) {
        checkNotNull("sessionOpenedEvent", sessionOpenedEvent);
        doOpenSession( sessionOpenedEvent.getSession() );
    }

    void onCanvasSessionResumed(@Observes SessionResumedEvent sessionResumedEvent) {
        checkNotNull("sessionResumedEvent", sessionResumedEvent);
        doOpenSession( sessionResumedEvent.getSession() );
    }

    void onCanvasSessionDisposed(@Observes SessionDisposedEvent sessionDisposedEvent) {
        checkNotNull("sessionDisposedEvent", sessionDisposedEvent);
        doCloseSession();
    }

    void onCanvasSessionPaused(@Observes SessionPausedEvent sessionPausedEvent) {
        checkNotNull("sessionPausedEvent", sessionPausedEvent);
        doCloseSession();
    }

    private void doOpenSession(final CanvasSession session) {
        this.canvasSession = (DefaultCanvasFullSession) session;
    }

    private void doCloseSession() {
        this.canvasSession = null;
        doClear();
    }

    private void doClear() {

    }

    private void executeUpdateProperty(final Element<? extends org.wirez.core.api.graph.content.view.View<?>> element,
                                       final String propertyId,
                                       final Object value) {

        final CanvasCommandManager<AbstractCanvasHandler> commandManager = canvasSession.getCanvasCommandManager();
        commandManager.execute( getCanvasHandler(), commandFactory.UPDATE_PROPERTY(element, propertyId, value) );

    }

    private void executeMove(final Element<? extends org.wirez.core.api.graph.content.view.View<?>> element,
                             final double x,
                             final double y) {

        final CanvasCommandManager<AbstractCanvasHandler> commandManager = canvasSession.getCanvasCommandManager();
        commandManager.execute( getCanvasHandler(), commandFactory.UPDATE_POSITION(element, x, y) );

    }

}