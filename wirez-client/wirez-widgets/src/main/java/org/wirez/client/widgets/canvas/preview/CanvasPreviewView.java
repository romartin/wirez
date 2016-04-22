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

package org.wirez.client.widgets.canvas.preview;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class CanvasPreviewView extends Composite implements CanvasPreview.View {

    interface ViewBinder extends UiBinder<Widget, CanvasPreviewView> {

    }

    private static ViewBinder uiBinder = GWT.create( ViewBinder.class );

    
    @UiField
    FlowPanel mainPanel;
    
    CanvasPreview presenter;

    @Override
    public void init(final CanvasPreview presenter) {
        this.presenter = presenter;
        initWidget( uiBinder.createAndBindUi( this ) );
    }

    @Override
    public CanvasPreview.View show(final String dataUrl,
                                   final String width,
                                   final String height) {
        final Image thumb = new Image( dataUrl );
        thumb.setSize( width, height);
        mainPanel.add( thumb );
        return this;
    }

    @Override
    public CanvasPreview.View clear() {
        mainPanel.clear();
        return this;
    }

}
