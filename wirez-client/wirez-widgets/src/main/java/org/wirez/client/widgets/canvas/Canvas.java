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

package org.wirez.client.widgets.canvas;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.uberfire.client.mvp.UberView;
import org.wirez.core.client.canvas.Layer;
import org.wirez.core.client.canvas.wires.WiresCanvas;
import org.wirez.core.client.event.ShapeStateModifiedEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

@Dependent
public class Canvas extends WiresCanvas implements IsWidget {

    private static final int PADDING = 15;

    @Inject
    public Canvas(final Event<ShapeStateModifiedEvent> canvasShapeStateModifiedEvent,
                  final Layer layer,
                  final WiresCanvas.View view) {
        super(canvasShapeStateModifiedEvent, layer, view);
    }

    @PostConstruct
    public void init() {
        super.init();
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }

    public Canvas show(final int width, final int height) {
        view.show(width, height, PADDING);
        return this;
    }
    
    @Override
    public Canvas addControl(final IsWidget control) {
        view.add(control);
        return this;
    }

    @Override
    public org.wirez.core.client.canvas.Canvas deleteControl(final IsWidget control) {
        view.remove(control);
        return this;
    }

    public Canvas clear() {
        super.clear();
        view.clear();
        return this;
    }
    
}
