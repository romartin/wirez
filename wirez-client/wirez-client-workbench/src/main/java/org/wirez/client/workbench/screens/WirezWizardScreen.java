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
import org.wirez.client.widgets.event.ShapeSetSelectedEvent;
import org.wirez.client.widgets.wizard.CanvasWizard;
import org.wirez.client.workbench.perspectives.WirezPerspective;
import org.wirez.core.api.WirezManager;
import org.wirez.core.api.util.UUID;
import org.wirez.core.client.ShapeSet;
import org.wirez.core.client.WirezClientManager;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;

@Dependent
@WorkbenchScreen(identifier = WirezWizardScreen.SCREEN_ID )
public class WirezWizardScreen {

    public static final String SCREEN_ID = "WirezWizardScreen";

    @Inject
    CanvasWizard wizard;
    
    @Inject
    ErrorPopupPresenter errorPopupPresenter;
    
    @Inject
    PlaceManager placeManager;
    
    @Inject
    WirezPerspective wirezPerspective;
    
    @Inject
    WirezClientManager wirezClientManager;

    private Menus menu = null;
    
    @PostConstruct
    public void init() {
    }

    @OnStartup
    public void onStartup(final PlaceRequest placeRequest) {
        wizard.show();
        this.menu = makeMenuBar();
    }

    private Menus makeMenuBar() {
        return MenuFactory
                .newTopLevelMenu("Load BPMN test process")
                .respondsWith(getLoadBPMNTestProcessCommand())
                .endMenu()
                .build();
    }

    private Command getLoadBPMNTestProcessCommand() {
        return new Command() {
            public void execute() {
                Map<String, String> params = new HashMap<String, String>();
                params.put( "bpmnTestMode", "true" );
                PlaceRequest placeRequest = new DefaultPlaceRequest( WirezCanvasScreen.SCREEN_ID , params );
                placeManager.goTo(placeRequest);
            }
        };
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
        return "Wirez Wizard Screen";
    }

    @WorkbenchPartView
    public IsWidget getWidget() {
        return wizard.asWidget();
    }
    
    @WorkbenchContextId
    public String getMyContextRef() {
        return "wirezWizardScreenContext";
    }

    void onShapeSetSelected(@Observes ShapeSetSelectedEvent shapeSetSelectedEvent) {
        checkNotNull("shapeSetSelectedEvent", shapeSetSelectedEvent);

        final String uuid = UUID.uuid();
        final String shapeSetUUID = shapeSetSelectedEvent.getUuid();
        final ShapeSet shapeSet = getWirezShapeSet(shapeSetUUID);
        final String shapSetName = shapeSet.getName();
        final String wirezSetId = shapeSet.getDefinitionSet().getId();
        Map<String, String> params = new HashMap<String, String>();
        params.put( "uuid", uuid );
        params.put( "wirezSetId", wirezSetId );
        params.put( "shapeSetUUID", shapeSetUUID );
        params.put( "title", "New " + shapSetName + " diagram" );

        PlaceRequest placeRequest = new DefaultPlaceRequest( WirezCanvasScreen.SCREEN_ID , params );
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
