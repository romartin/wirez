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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.uberfire.client.annotations.WorkbenchMenu;
import org.uberfire.client.annotations.WorkbenchPartTitle;
import org.uberfire.client.annotations.WorkbenchScreen;
import org.uberfire.lifecycle.OnStartup;
import org.uberfire.mvp.Command;
import org.uberfire.mvp.PlaceRequest;
import org.uberfire.workbench.model.menu.MenuFactory;
import org.uberfire.workbench.model.menu.Menus;
import org.wirez.bpmn.api.BPMNDefinitionSet;
import org.wirez.bpmn.api.BPMNDiagram;
import org.wirez.core.api.adapter.DefinitionSetAdapter;
import org.wirez.core.api.definition.property.HasValue;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.client.ClientDefinitionManager;
import org.wirez.core.client.service.ClientDefinitionServices;
import org.wirez.core.client.service.ClientRuntimeError;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
@Templated
@WorkbenchScreen(identifier="HomeScreen")
public class HomeScreen extends Composite {

    @Inject
    @DataField( "testButton" )
    private Button testButton = new Button();

    @Inject
    @DataField( "testButton2" )
    private Button testButton2 = new Button();

    @EventHandler( "testButton" )
    public void doSomethingC1(ClickEvent e) {
        clientDefinitionServices.buildGraphElement(new BPMNDiagram(), new ClientDefinitionServices.ServiceCallback<Element>() {
            @Override
            public void onSuccess(Element item) {
                GWT.log("item uuid=" + item.getUUID() + " , properties=" + item.getProperties().size());
                Property property = (Property) item.getProperties().iterator().next();
                GWT.log("Property=" + property.getId());
                final String pvalue = ( (ViewContent<BPMNDiagram>) item.getContent()).getDefinition().getDiagramSet().getPackage().getValue();
                GWT.log("Property value='" + pvalue + "'");
            }

            @Override
            public void onError(ClientRuntimeError error) {
                GWT.log("Error");
            }
        });
    }

    @EventHandler( "testButton2" )
    public void doSomethingC2(ClickEvent e) {
        DefinitionSetAdapter adapter = clientDefinitionManager.getDefinitionSetAdapter(BPMNDefinitionSet.class);
        GWT.log("adapter=" + adapter);
    }
    
    @Inject
    ClientDefinitionServices clientDefinitionServices;

    @Inject
    ClientDefinitionManager clientDefinitionManager;
    
    @WorkbenchPartTitle
    public String getScreenTitle() {
        return "Welcome to UberFire Wirez";
    }

    @PostConstruct
    void doLayout() {
        // Nothing to do.
    }
}
