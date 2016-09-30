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

package org.kie.workbench.common.stunner.client.workbench.screens;

import com.google.gwt.user.client.ui.IsWidget;
import org.uberfire.client.annotations.*;
import org.uberfire.client.mvp.PlaceManager;
import org.uberfire.client.workbench.widgets.common.ErrorPopupPresenter;
import org.uberfire.lifecycle.OnClose;
import org.uberfire.lifecycle.OnOpen;
import org.uberfire.lifecycle.OnStartup;
import org.uberfire.mvp.Command;
import org.uberfire.mvp.PlaceRequest;
import org.uberfire.workbench.model.menu.MenuFactory;
import org.uberfire.workbench.model.menu.Menus;
import org.kie.workbench.common.stunner.client.widgets.notification.Notifications;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
@WorkbenchScreen(identifier = NotificationsScreen.SCREEN_ID )
public class NotificationsScreen {

    public static final String SCREEN_ID = "NotificationsScreen";

    @Inject
    Notifications notifications;
    
    @Inject
    ErrorPopupPresenter errorPopupPresenter;
    
    @Inject
    PlaceManager placeManager;

    private Menus menu = null;
    
    @PostConstruct
    public void init() {
        
    }

    @OnStartup
    public void onStartup(final PlaceRequest placeRequest) {
        this.menu = makeMenuBar();
    }

    private Menus makeMenuBar() {
        return MenuFactory
                .newTopLevelMenu("Clear")
                .respondsWith(getClearCommand())
                .endMenu()
                .build();
    }

    private Command getClearCommand() {
        return new Command() {
            public void execute() {
                notifications.clear();
            }
        };
    }

    @OnOpen
    public void onOpen() {

    }

    @OnClose
    public void onClose() {
        notifications.clear();
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
        return "Notifications";
    }

    @WorkbenchPartView
    public IsWidget getWidget() {
        return notifications.asWidget();
    }
    
    @WorkbenchContextId
    public String getMyContextRef() {
        return "stunnerNotificationsScreenContext";
    }
    
}
