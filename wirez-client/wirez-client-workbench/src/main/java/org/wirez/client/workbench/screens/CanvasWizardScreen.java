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
import org.uberfire.client.annotations.*;
import org.uberfire.client.mvp.PlaceManager;
import org.uberfire.client.workbench.widgets.common.ErrorPopupPresenter;
import org.uberfire.lifecycle.OnClose;
import org.uberfire.lifecycle.OnOpen;
import org.uberfire.lifecycle.OnStartup;
import org.uberfire.mvp.Command;
import org.uberfire.mvp.PlaceRequest;
import org.uberfire.mvp.impl.DefaultPlaceRequest;
import org.uberfire.workbench.model.menu.MenuFactory;
import org.uberfire.workbench.model.menu.Menus;
import org.wirez.client.widgets.event.CreateEmptyDiagramEvent;
import org.wirez.client.widgets.event.LoadDiagramEvent;
import org.wirez.client.widgets.wizard.CanvasWizard;
import org.wirez.client.workbench.perspectives.WirezPerspective;
import org.wirez.core.api.util.UUID;
import org.wirez.core.client.ShapeSet;
import org.wirez.core.client.ShapeManager;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;

@Dependent
@WorkbenchScreen(identifier = CanvasWizardScreen.SCREEN_ID )
public class CanvasWizardScreen {

    public static final String SCREEN_ID = "CanvasWizardScreen";

    @Inject
    CanvasWizard wizard;
    
    @Inject
    ErrorPopupPresenter errorPopupPresenter;
    
    @Inject
    PlaceManager placeManager;
    
    @Inject
    WirezPerspective wirezPerspective;
    
    @Inject
    ShapeManager wirezClientManager;

    private Menus menu = null;
    
    @PostConstruct
    public void init() {
    }

    @OnStartup
    public void onStartup(final PlaceRequest placeRequest) {
        this.menu = makeMenuBar();
    }

    private Menus makeMenuBar() {
        return null;
    }

    @OnOpen
    public void onOpen() {

    }

    @OnClose
    public void onClose() {
        wizard.clear();
    }


    @WorkbenchMenu
    public Menus getMenu() {
        return menu;
    }

    private void showError(final String message) {
        errorPopupPresenter.showMessage(message);
    }

    @WorkbenchPartTitle
    public String getTitle() {
        return "Wizard";
    }

    @WorkbenchPartView
    public IsWidget getWidget() {
        return wizard.asWidget();
    }
    
    @WorkbenchContextId
    public String getMyContextRef() {
        return "canvasWizardScreenContext";
    }

    void onCreateEmptyDiagramEvent(@Observes CreateEmptyDiagramEvent createEmptyDiagramEvent) {
        checkNotNull("createEmptyDiagramEvent", createEmptyDiagramEvent);

        final String shapeSetId = createEmptyDiagramEvent.getShapeSetId();
        final ShapeSet shapeSet = getWirezShapeSet(shapeSetId);
        final String shapSetName = shapeSet.getName();
        final String wirezSetId = shapeSet.getDefinitionSetId();
        Map<String, String> params = new HashMap<String, String>();
        params.put( "defSetId", wirezSetId );
        params.put( "shapeSetId", shapeSetId );
        params.put( "title", "New " + shapSetName + " diagram" );

        PlaceRequest placeRequest = new DefaultPlaceRequest( CanvasScreen.SCREEN_ID , params );
        placeManager.goTo(placeRequest);
    }

    void onLoadDiagramEvent(@Observes LoadDiagramEvent loadDiagramEvent) {
        checkNotNull("loadDiagramEvent", loadDiagramEvent);

        final String path = loadDiagramEvent.getPath();
        Map<String, String> params = new HashMap<String, String>();
        params.put( "path", path );

        PlaceRequest placeRequest = new DefaultPlaceRequest( CanvasScreen.SCREEN_ID , params );
        placeManager.goTo(placeRequest);
    }

    private ShapeSet getWirezShapeSet(final String id) {
        for (final ShapeSet set : wirezClientManager.getShapeSets()) {
            if (set.getId().equals(id)) {
                return set;
            }
        }
        return null;
    }

    
    
}
