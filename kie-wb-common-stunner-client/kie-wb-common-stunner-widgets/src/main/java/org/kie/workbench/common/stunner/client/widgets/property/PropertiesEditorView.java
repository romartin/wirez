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

package org.kie.workbench.common.stunner.client.widgets.property;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import org.uberfire.ext.properties.editor.client.PropertyEditorWidget;
import org.uberfire.ext.properties.editor.model.PropertyEditorEvent;

public class PropertiesEditorView extends Composite implements PropertiesEditor.View {

    interface ViewBinder extends UiBinder<Widget, PropertiesEditorView> {

    }

    private static ViewBinder uiBinder = GWT.create( ViewBinder.class );

    @UiField
    FlowPanel panel;

    PropertyEditorWidget propertyEditorWidget;
    
    PropertiesEditor presenter;

    @Override
    public void init(final PropertiesEditor presenter) {
        this.presenter = presenter;
        initWidget( uiBinder.createAndBindUi( this ) );
        clear();
    }
    
    @Override
    public void handle(final PropertyEditorEvent propertyEditorEvent) {
        panel.clear();
        propertyEditorWidget = GWT.create( PropertyEditorWidget.class );
        propertyEditorWidget.init();
        panel.add(propertyEditorWidget);
        propertyEditorWidget.handle(propertyEditorEvent);
    }

    @Override
    public void clear() {
        panel.clear();
        showEmpty();
    }
    
    private void showEmpty() {
        final HTML emptyText = new HTML("No elements selected");
        emptyText.getElement().getStyle().setMargin(20, Style.Unit.PX);
        emptyText.getElement().getStyle().setFontStyle(Style.FontStyle.ITALIC);
        panel.add(emptyText);
    }
    
}
