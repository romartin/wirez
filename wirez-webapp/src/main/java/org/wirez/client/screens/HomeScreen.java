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
package org.wirez.client.screens;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.uberfire.client.annotations.*;
import org.uberfire.mvp.Command;
import org.uberfire.workbench.model.menu.Menus;
import org.wirez.bpmn.api.Task;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.rule.Rule;
import org.wirez.core.api.util.ElementUtils;
import org.wirez.core.client.ClientDefinitionManager;
import org.wirez.core.client.service.ClientDefinitionServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.service.ServiceCallback;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Collection;

@Dependent
@WorkbenchScreen(identifier="HomeScreen")
public class HomeScreen {

    @WorkbenchPartTitle
    public String getScreenTitle() {
        return "Welcome to UberFire Wirez";
    }

    @WorkbenchPartView
    public IsWidget getWidget() {
        FlowPanel fp = new FlowPanel();
        HTML html = new HTML("Home Screen");
        fp.add(html);
        return fp;
    }

    @WorkbenchContextId
    public String getMyContextRef() {
        return "homeScreenContext";
    }

    @WorkbenchMenu
    public Menus getMenu() {
        return null;
    }
    
    @PostConstruct
    void doLayout() {
        // Nothing to do.
    }
}
