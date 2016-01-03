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

package org.wirez.client.widgets.palette.accordion;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import org.gwtbootstrap3.client.ui.PanelGroup;

public class PaletteView extends Composite implements Palette.View {

    interface ViewBinder extends UiBinder<Widget, PaletteView> {

    }

    private static ViewBinder uiBinder = GWT.create( ViewBinder.class );

    @UiField
    FlowPanel mainPanel;
    
    @UiField
    FlowPanel noCanvasPanel;
    
    @UiField
    PanelGroup accordion;

    Palette presenter;

    @Override
    public void init(final Palette presenter) {
        this.presenter = presenter;
        initWidget( uiBinder.createAndBindUi( this ) );
    }

    @Override
    public Palette.View setNoCanvasViewVisible(boolean isVisible) {
        noCanvasPanel.setVisible(isVisible);
        return this;
    }

    @Override
    public Palette.View setGroupsViewVisible(boolean isVisible) {
        accordion.setVisible(isVisible);
        return null;
    }

    @Override
    public Palette.View addGroup(final IsWidget paletteGroupView) {
        accordion.add(paletteGroupView);
        return this;
    }

    @Override
    public Palette.View clearGroups() {
        accordion.clear();
        return this;
    }

    @Override
    public Palette.View clear() {
        accordion.clear();
        return this;
    }

}
