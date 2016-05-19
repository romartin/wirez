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

import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.ui.IsWidget;
import org.uberfire.client.annotations.*;
import org.uberfire.client.mvp.PlaceManager;
import org.uberfire.client.workbench.events.ChangeTitleWidgetEvent;
import org.uberfire.lifecycle.*;
import org.uberfire.mvp.Command;
import org.uberfire.mvp.PlaceRequest;
import org.uberfire.workbench.model.menu.Menus;
import org.wirez.client.widgets.session.presenter.impl.DefaultFullSessionPresenter;
import org.wirez.core.diagram.Diagram;
import org.wirez.core.util.UUID;
import org.wirez.core.client.session.impl.DefaultCanvasFullSession;
import org.wirez.core.client.session.impl.DefaultCanvasSessionManager;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

@Dependent
@WorkbenchScreen(identifier = CanvasScreen.SCREEN_ID )
public class CanvasScreen {

    private static Logger LOGGER = Logger.getLogger(CanvasScreen.class.getName());
    
    public static final String SCREEN_ID = "CanvasScreen";

    @Inject
    DefaultCanvasSessionManager canvasSessionManager;

    @Inject
    DefaultFullSessionPresenter canvasSessionPresenter;
    
    @Inject
    CanvasScreenTestingMenus testingMenus;

    @Inject
    PlaceManager placeManager;
    
    @Inject
    Event<ChangeTitleWidgetEvent> changeTitleNotificationEvent;

    private PlaceRequest placeRequest;
    private String title = "Canvas Screen";
    private DefaultCanvasFullSession session;
    
    @PostConstruct
    public void init() {

        // Create a new full control session.
        session = canvasSessionManager.newFullSession();
        
        // Initialize the session presenter.
        canvasSessionPresenter.initialize( session, 1000, 1000 );
        
        // Menus for testing things while coding...
        testingMenus.init( session, canvasSessionPresenter );
        
    }

    @OnStartup
    public void onStartup(final PlaceRequest placeRequest) {

        this.placeRequest = placeRequest;
        final String _uuid = placeRequest.getParameter( "uuid", "" );

        final boolean isCreate = _uuid == null || _uuid.trim().length() == 0;
        
        final Command callback = () -> {
            
            final Diagram diagram = canvasSessionPresenter.getCanvasHandler().getDiagram();
            
            if ( null != diagram ) {
                
                // Update screen title.
                updateTitle( diagram.getSettings().getTitle() );

            }
            
        };
        
        if (isCreate) {

            final String defSetId = placeRequest.getParameter( "defSetId", "" );
            final String shapeSetd = placeRequest.getParameter( "shapeSetId", "" );
            final String title = placeRequest.getParameter( "title", "" );
            
            // Create a new diagram.
            canvasSessionPresenter.newDiagram( UUID.uuid(), title, defSetId, shapeSetd, callback );

        } else {

            // Load an existing diagram.
            canvasSessionPresenter.load( _uuid, callback );
            
        }

    }
    
    private void updateTitle( final String title ) {
        
        // Change screen title.
        CanvasScreen.this.title = title;
        changeTitleNotificationEvent.fire( new ChangeTitleWidgetEvent(placeRequest, this.title) );
        
    }

    @OnOpen
    public void onOpen() {
        resume();
    }

    @OnFocus
    public void onFocus() {
        resume();
    }

    @OnClose
    public void onClose() {
        disposeSession();
    }

    @OnLostFocus
    public void OnLostFocus() {
        pauseSession();
    }

    @WorkbenchMenu
    public Menus getMenu() {
        return testingMenus.getMenu();
    }

    private void resume() {
        // TODO: canvasSessionManager.resume( session );
    }

    private void pauseSession() {
        canvasSessionManager.pause();
    }

    private void disposeSession() {
        canvasSessionManager.dispose();
        this.testingMenus.destroy();
        this.session = null;
    }

    @WorkbenchPartTitle
    public String getTitle() {
        return title;
    }

    @WorkbenchPartView
    public IsWidget getWidget() {
        return canvasSessionPresenter.asWidget();
    }

    @WorkbenchContextId
    public String getMyContextRef() {
        return "wirezCanvasScreenContext";
    }

    private void log(final Level level, final String message) {
        if (LogConfiguration.loggingIsEnabled()) {
            LOGGER.log(level, message);
        }
    }
    
}
