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

package org.wirez.client.widgets.wizard.screen.load;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.constants.HeadingSize;

public class LoadDiagramWizardScreenView extends Composite implements LoadDiagramWizardScreen.View {

    private static final double ENTRY_HEIGHT = 50;
    private static final String IMAGE_SIZE = "35px";
    
    interface ViewBinder extends UiBinder<Widget, LoadDiagramWizardScreenView> {

    }

    private static ViewBinder uiBinder = GWT.create( ViewBinder.class );

    @UiField
    FlowPanel mainPanel;
    
    @UiField
    FlowPanel emptyPanel;

    @UiField
    VerticalPanel diagramsPanel;
    
    LoadDiagramWizardScreen presenter;

    @Override
    public void init(final LoadDiagramWizardScreen presenter) {
        this.presenter = presenter;
        initWidget( uiBinder.createAndBindUi( this ) );
        clear();
    }

    @Override
    public LoadDiagramWizardScreen.View showEmpty() {
        clear();
        emptyPanel.setVisible(true);
        return this;
    }

    @Override
    public LoadDiagramWizardScreen.View add(final String uuid, final String title, final String path, final SafeUri thumbUri) {
        emptyPanel.setVisible(false);
        diagramsPanel.setVisible(true);
        insertDiagramEntry( uuid, title, path, thumbUri );
        return this;
    }

    @Override
    public LoadDiagramWizardScreen.View clear() {
        diagramsPanel.clear();
        emptyPanel.setVisible(false);
        diagramsPanel.setVisible(false);
        return this;
    }
    
    private void insertDiagramEntry(final String uuid, final String title, final String path, final SafeUri thumbUri) {
        
        final HorizontalPanel panel = new HorizontalPanel();
        panel.getElement().getStyle().setWidth(100, Style.Unit.PCT);
        panel.getElement().getStyle().setHeight(ENTRY_HEIGHT, Style.Unit.PCT);
        panel.getElement().getStyle().setMargin(15, Style.Unit.PX);
        panel.getElement().getStyle().setPadding(15, Style.Unit.PX);
        panel.getElement().getStyle().setBackgroundColor( "#f2f2f2" );
        panel.getElement().getStyle().setCursor(Style.Cursor.POINTER);

        final org.gwtbootstrap3.client.ui.Image image = new org.gwtbootstrap3.client.ui.Image(thumbUri);
        image.getElement().getStyle().setCursor(Style.Cursor.POINTER);
        image.getElement().getStyle().setMarginLeft(10, Style.Unit.PX);
        image.getElement().getStyle().setMarginTop(5, Style.Unit.PX);
        image.setSize(IMAGE_SIZE, IMAGE_SIZE);
        panel.add( image );
        
        final Heading heading = new Heading(HeadingSize.H3);
        heading.setText( new SafeHtmlBuilder().appendEscaped(title).toSafeHtml().asString() );
        panel.add( heading );
        
        panel.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                presenter.onItemClick( uuid );
            }
        }, ClickEvent.getType());
        
        diagramsPanel.add( panel );
        
    }

    
}
