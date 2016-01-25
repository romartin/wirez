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
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.uberfire.client.annotations.WorkbenchPartTitle;
import org.uberfire.client.annotations.WorkbenchScreen;
import org.uberfire.mvp.Command;
import org.wirez.bpmn.api.BPMNDefinitionSet;
import org.wirez.bpmn.api.Task;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.registry.DefinitionSetRegistry;
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
                
        Collection<DefinitionSet> definitionSets = definitionSetRegistry.getItems();
        GWT.log(definitionSets.size() + "");
        
    }
    
    private void log(final Definition definition, final Command callback) {
        GWT.log("****************************************************************");
        GWT.log("           " + definition.getTitle());
        GWT.log("****************************************************************");
        clientDefinitionServices.buildGraphElement(definition, new ServiceCallback<Element>() {
            @Override
            public void onSuccess(Element element) {
                Definition definitionSet = ((ViewContent<Definition>) element.getContent()).getDefinition();
                
                GWT.log("Element uuid=" + element.getUUID() + " , properties=" + element.getProperties().size());
                final Property property = ElementUtils.getProperty(element, "bgColor");
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


        clientDefinitionManager.getRules(new BPMNDefinitionSet(), new ServiceCallback<Collection<Rule>>() {
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
        
    }
    
    @Inject
    ClientDefinitionServices clientDefinitionServices;

    @Inject
    ClientDefinitionManager clientDefinitionManager;
    
    @Inject
    DefinitionSetRegistry definitionSetRegistry;
    
    @WorkbenchPartTitle
    public String getScreenTitle() {
        return "Welcome to UberFire Wirez";
    }

    @PostConstruct
    void doLayout() {
        // Nothing to do.
    }
}
