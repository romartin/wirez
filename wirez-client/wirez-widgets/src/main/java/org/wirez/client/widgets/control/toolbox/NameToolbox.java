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

import com.google.gwt.user.client.ui.Widget;
import org.uberfire.client.mvp.UberView;
import org.wirez.core.api.definition.property.defaults.Name;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.util.ElementUtils;
import org.wirez.core.client.canvas.command.WiresCanvasCommandManager;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
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
    CanvasCommandFactory canvasCommandFactory;
    private Element element;

    @Inject
    public NameToolbox(final CanvasCommandFactory canvasCommandFactory, final View view) {
        this.canvasCommandFactory = canvasCommandFactory;
        this.view = view;
    }

    @PostConstruct
    public void setup() {
        view.init(this);
    }
    
    @Override
    public void show(final Element element, final double x, final double y) {
        this.element = element;
        final Name nameProperty = (Name) ElementUtils.getProperty(element, Name.ID);
        final String name = nameProperty.getValue();
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
        canvasHandler.getCommandManager().execute( canvasHandler, canvasCommandFactory.UPDATE_PROPERTY(element, "name", name) );
        view.hide();
    }
    
    void onClose() {
        this.hide();   
    }
    
}
