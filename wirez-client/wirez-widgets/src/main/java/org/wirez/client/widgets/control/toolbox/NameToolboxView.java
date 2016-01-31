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
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import org.gwtbootstrap3.client.ui.Icon;
import org.gwtbootstrap3.client.ui.TextBox;

public class NameToolboxView extends Composite implements NameToolbox.View {

    interface ViewBinder extends UiBinder<Widget, NameToolboxView> {

    }

    private static ViewBinder uiBinder = GWT.create( ViewBinder.class );

    @UiField
    FlowPanel mainPanel;
    
    @UiField
    TextBox nameBox;
    
    @UiField
    Icon closeButton;
    
    private NameToolbox presenter;
    
    @Override
    public void init(final NameToolbox presenter) {
        this.presenter = presenter;
        initWidget( uiBinder.createAndBindUi( this ) );
        mainPanel.getElement().getStyle().setPosition(Style.Position.ABSOLUTE);
        mainPanel.getElement().getStyle().setDisplay(Style.Display.NONE);
        nameBox.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(final ValueChangeEvent<String> event) {
                final String name = event.getValue();
                presenter.onChangeName(name);
            }
        });
        closeButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                presenter.onClose();
            }
        });
    }

    @Override
    public NameToolbox.View show(final String name,
                                 final double x,
                                 final double y) {
        nameBox.setValue(name);
        nameBox.setText(name);
        mainPanel.setVisible(true);
        mainPanel.getElement().getStyle().setLeft(x, Style.Unit.PX);
        mainPanel.getElement().getStyle().setTop(y, Style.Unit.PX);
        mainPanel.getElement().getStyle().setDisplay(Style.Display.BLOCK);
        return this;
    }

    @Override
    public NameToolbox.View hide() {
        mainPanel.getElement().getStyle().setDisplay(Style.Display.NONE);
        return this;
    }

    
}
