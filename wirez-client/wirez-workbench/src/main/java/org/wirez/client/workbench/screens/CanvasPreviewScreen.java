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

import com.google.gwt.user.client.ui.IsWidget;
import org.uberfire.client.annotations.*;
import org.uberfire.client.workbench.widgets.common.ErrorPopupPresenter;
import org.uberfire.lifecycle.OnClose;
import org.uberfire.lifecycle.OnOpen;
import org.uberfire.lifecycle.OnStartup;
import org.uberfire.mvp.PlaceRequest;
import org.uberfire.workbench.model.menu.Menus;
import org.wirez.client.widgets.canvas.preview.CanvasPreview;
import org.wirez.client.workbench.perspectives.WirezPerspective;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.session.event.SessionDisposedEvent;
import org.wirez.core.client.session.event.SessionOpenedEvent;
import org.wirez.core.client.session.event.SessionPausedEvent;
import org.wirez.core.client.session.event.SessionResumedEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;

@Dependent
@WorkbenchScreen(identifier = CanvasPreviewScreen.SCREEN_ID )
public class CanvasPreviewScreen {

    public static final String SCREEN_ID = "CanvasPreviewScreen";
    public static final int WIDTH = WirezPerspective.EAST_PANEL_WIDTH;
    public static final int HEIGHT = 200;

    @Inject
    CanvasPreview canvasPreview;
    
    private PlaceRequest placeRequest;
    
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
        clear();
    }


    public void show( final Canvas canvas ) {

        canvasPreview.show( canvas, WIDTH, HEIGHT );

    }

    public void clear() {

        canvasPreview.clear();

    }

    @WorkbenchMenu
    public Menus getMenu() {
        return null;
    }

    @WorkbenchPartTitle
    public String getTitle() {
        return "Preview";
    }

    @WorkbenchPartView
    public IsWidget getWidget() {
        return canvasPreview.asWidget();
    }
    
    @WorkbenchContextId
    public String getMyContextRef() {
        return "CanvasPreviewScreenContext";
    }

    void onCanvasSessionOpened(@Observes SessionOpenedEvent sessionOpenedEvent) {
        checkNotNull("sessionOpenedEvent", sessionOpenedEvent);
        show( sessionOpenedEvent.getSession().getCanvas() );
    }

    void onCanvasSessionResumed(@Observes SessionResumedEvent sessionResumedEvent) {
        checkNotNull("sessionResumedEvent", sessionResumedEvent);
        show( sessionResumedEvent.getSession().getCanvas() );
    }

    void onCanvasSessionDisposed(@Observes SessionDisposedEvent sessionDisposedEvent) {
        checkNotNull("sessionDisposedEvent", sessionDisposedEvent);
        clear();
    }

    void onCanvasSessionPaused(@Observes SessionPausedEvent sessionPausedEvent) {
        checkNotNull("sessionPausedEvent", sessionPausedEvent);
        clear();
    }

}
