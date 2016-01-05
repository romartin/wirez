/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
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

package org.wirez.client.widgets.control.toolbox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import org.uberfire.client.mvp.UberView;
import org.wirez.core.api.definition.property.defaultset.NameBuilder;
import org.wirez.core.api.graph.Element;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.command.impl.DefaultCanvasCommands;
import org.wirez.core.client.control.toolbox.BaseToolbox;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class NameToolbox extends BaseToolbox {
    
    public interface View extends UberView<NameToolbox> {
        
        View show(String name, double x, double y);
        
        View hide();
        
    }

    View view;
    DefaultCanvasCommands defaultCanvasCommands;
    private Element element;

    @Inject
    public NameToolbox(final DefaultCanvasCommands defaultCanvasCommands, final View view) {
        this.defaultCanvasCommands = defaultCanvasCommands;
        this.view = view;
    }

    @PostConstruct
    public void setup() {
        view.init(this);
    }
    
    @Override
    public void show(final Element element, final double x, final double y) {
        this.element = element;
        final String name = (String) element.getProperties().get(NameBuilder.PROPERTY_ID);
        GWT.log("Showing NameToolbox at [" + x + "," + y + "]");
        view.show(name, x, y);
    }

    @Override
    public void hide() {
        view.hide();
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }
    
    void onChangeName(final String name) {
        GWT.log("NameContextMenu - Change name to '" + name + "'");
        final CanvasCommandManager commandManager = (CanvasCommandManager) canvasHandler;
        commandManager.execute(defaultCanvasCommands.UPDATE_PROPERTY(element, new NameBuilder().build(), name));
        view.hide();
    }
    
}
