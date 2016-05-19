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

package org.wirez.client.widgets.popup;

import com.google.gwt.user.client.ui.Widget;
import org.uberfire.client.mvp.UberView;
import org.wirez.core.definition.util.DefinitionUtils;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.content.definition.Definition;
import org.wirez.core.graph.util.GraphUtils;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.command.impl.UpdateCanvasElementPropertyCommand;
import org.wirez.core.client.components.popup.AbstractPopupBox;
import org.wirez.core.client.session.command.Session;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class NamePopupBox extends AbstractPopupBox<Element> {
    
    public interface View extends UberView<NamePopupBox> {
        
        View show(String name, double x, double y);
        
        View hide();
        
    }

    View view;
    DefinitionUtils definitionUtils;
    CanvasCommandFactory canvasCommandFactory;
    CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager;
    GraphUtils graphUtils;
    private Element<? extends Definition> element;

    @Inject
    public NamePopupBox(final DefinitionUtils definitionUtils,
                        final CanvasCommandFactory canvasCommandFactory,
                        final @Session CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager,
                        final GraphUtils graphUtils,
                        final View view) {
        this.definitionUtils = definitionUtils;
        this.canvasCommandFactory = canvasCommandFactory;
        this.canvasCommandManager = canvasCommandManager;
        this.graphUtils = graphUtils;
        this.view = view;
    }

    @PostConstruct
    public void setup() {
        view.init(this);
    }
    
    @Override
    public void show(final Element element, final double x, final double y) {
        this.element = element;
        final String name = definitionUtils.getName( this.element.getContent().getDefinition() );
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
    
    // TODO: Check command result.
    void onChangeName(final String name) {
        final Object def = element.getContent().getDefinition();
        final String nameId = definitionUtils.getNameIdentifier( def );
        if ( null != name && null != nameId ) {
            UpdateCanvasElementPropertyCommand command = canvasCommandFactory.UPDATE_PROPERTY(element, nameId, name);
            canvasCommandManager.execute( canvasHandler, command );
        }
        view.hide();
    }
    
    void onClose() {
        this.hide();   
    }
    
}
