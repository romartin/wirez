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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.databinding.client.HasProperties;
import org.jboss.errai.databinding.client.api.DataBinder;
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
import org.wirez.bpmn.api.StartNoneEvent;
import org.wirez.bpmn.api.Task;
import org.wirez.bpmn.api.property.Width;
import org.wirez.core.api.adapter.DefinitionSetAdapter;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.property.HasValue;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.rule.Rule;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.service.definition.DefinitionSetResponse;
import org.wirez.core.api.util.PropertyUtils;
import org.wirez.core.client.ClientDefinitionManager;
import org.wirez.core.client.service.ClientDefinitionServices;
import org.wirez.core.client.service.ClientRuntimeError;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Set;

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
        
        log(new Task(), new Command() {
            @Override
            public void execute() {
                
            }
        });
        
    }
    
    private void log(final Definition definition, final Command callback) {
        GWT.log("****************************************************************");
        GWT.log("           " + definition.getTitle());
        GWT.log("****************************************************************");
        clientDefinitionServices.buildGraphElement(definition, new ClientDefinitionServices.ServiceCallback<Element>() {
            @Override
            public void onSuccess(Element element) {
                Definition definitionSet = ((ViewContent<Definition>) element.getContent()).getDefinition();
                
                GWT.log("Element uuid=" + element.getUUID() + " , properties=" + element.getProperties().size());
                final Property property = PropertyUtils.getProperty(element.getProperties(), "bgColor");
                Object value = clientDefinitionManager.getPropertyAdapter(property).getValue(property);
                GWT.log("Element BgColor -> " + value);
                final Task task = (Task) definitionSet;
                Object pojoVlaue = task.getBackgroundSet().getBgColor().getValue();
                GWT.log("POJO BgColor -> " + pojoVlaue);

            }

            @Override
            public void onError(ClientRuntimeError error) {
                showError(error.getMessage());
                
            }
        });
    }

    private void showError(final String message) {
        GWT.log(message);
        Window.alert(message);
    }
    
    @EventHandler( "testButton2" )
    public void doSomethingC2(ClickEvent e) {


        clientDefinitionManager.getRules(new BPMNDefinitionSet(), new ClientDefinitionServices.ServiceCallback<Collection<Rule>>() {
            @Override
            public void onSuccess(Collection<Rule> rules) {
                GWT.log("RULES size = " + rules.size() );
            }

            @Override
            public void onError(ClientRuntimeError error) {
                showError(error.getMessage());
            }
        });
        
        if (true) {
            return;
        }
        
        clientDefinitionServices.buildGraphElement(new Task(), new ClientDefinitionServices.ServiceCallback<Element>() {
            @Override
            public void onSuccess(Element element) {
                Task task = ((ViewContent<Task>) element.getContent()).getDefinition();
                GWT.log("Element uuid=" + element.getUUID() + " , properties=" + element.getProperties().size());


                // DataBinder<Task> taskDataBinder = DataBinder.forModel(task);

                /*String name = (String) PropertyUtils.getValue(element.getProperties(), "name");
                GWT.log("Element name=" + name );
                PropertyUtils.setValue(element.getProperties(), "name", "New name setted");
                String newName = (String) PropertyUtils.getValue(element.getProperties(), "name");
                GWT.log("Element newName=" + newName );
                GWT.log("Element newNameTyped=" + task.getGeneral().getName().getValue() );

                task.getGeneral().getName().setValue("New name setted TYPED");
                String newNameTyped = (String) PropertyUtils.getValue(element.getProperties(), "name");
                GWT.log("Element newNameTYPED=" + newNameTyped );
                GWT.log("Element newNameTYPED=" + task.getGeneral().getName().getValue() );*/
                
            }

            @Override
            public void onError(ClientRuntimeError error) {
                showError(error.getMessage());

            }
        });
        
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
