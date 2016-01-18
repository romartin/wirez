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
import org.uberfire.client.workbench.events.ChangeTitleWidgetEvent;
import org.uberfire.client.workbench.widgets.common.ErrorPopupPresenter;
import org.uberfire.lifecycle.OnClose;
import org.uberfire.lifecycle.OnOpen;
import org.uberfire.lifecycle.OnStartup;
import org.uberfire.mvp.PlaceRequest;
import org.uberfire.workbench.model.menu.Menus;
import org.wirez.client.widgets.property.PropertiesEditor;
import org.wirez.client.workbench.event.CanvasScreenStateChangedEvent;
import org.wirez.core.api.definition.property.defaultset.NameBuilder;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.util.PropertyUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;

@Dependent
@WorkbenchScreen(identifier = PropertiesScreen.SCREEN_ID )
public class PropertiesScreen {

    public static final String SCREEN_ID = "PropertiesScreen";

    @Inject
    PropertiesEditor propertiesEditor;
    
    @Inject
    ErrorPopupPresenter errorPopupPresenter;
    
    @Inject
    PlaceManager placeManager;

    @Inject
    Event<ChangeTitleWidgetEvent> changeTitleNotification;
    
    private PlaceRequest placeRequest;
    
    @PostConstruct
    public void init() {
        propertiesEditor.setEditorCallback(new PropertiesEditor.EditorCallback() {
            @Override
            public void onShowElement(Element<? extends ViewContent<?>> element) {
                if ( null != element ) {
                    String name = PropertyUtils.getValue(element.getProperties(), NameBuilder.PROPERTY_ID);
                    name = name != null ? name : element.getContent().getDefinition().getDefinitionContent().getTitle();
                    changeTitleNotification.fire(new ChangeTitleWidgetEvent(placeRequest, name + " Properties"));
                } else {
                    changeTitleNotification.fire(new ChangeTitleWidgetEvent(placeRequest, "Properties"));
                }
            }
        });
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
        propertiesEditor.clear();
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
        return propertiesEditor.asWidget();
    }
    
    @WorkbenchContextId
    public String getMyContextRef() {
        return "wirezPropertiesScreenContext";
    }

    void onWirezCanvasOpened(@Observes CanvasScreenStateChangedEvent stateChangedEvent) {
        checkNotNull("stateChangedEvent", stateChangedEvent);
        
        if (CanvasScreen.CanvasScreenState.ACTIVE.equals(stateChangedEvent.getState())) {
            propertiesEditor.show(stateChangedEvent.getCanvasHandler());
        } else {
            propertiesEditor.clear();
            changeTitleNotification.fire(new ChangeTitleWidgetEvent(placeRequest, "Properties"));
        }
    }
    
}
