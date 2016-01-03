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

package org.wirez.client.widgets.palette.tooltip;

import com.ait.lienzo.client.core.shape.IPrimitive;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import org.uberfire.client.mvp.UberView;
import org.wirez.core.client.ShapeGlyph;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultPaletteTooltip implements PaletteTooltip {
    
    public interface View extends UberView<DefaultPaletteTooltip> {
        
        View show(IPrimitive<?> glyph, String text, double width, double height, double x, double y);
        
        View hide();
        
    }
    
    View view;

    @Inject
    public DefaultPaletteTooltip(final View view) {
        this.view = view;
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }

    @PostConstruct
    public void init() {
        view.init(this);
    }

    @Override
    public void show(final ShapeGlyph glyph, final String text, final double x, final double y) {
        GWT.log("DefaultPaletteTooltip#show [x=" + x + ", y=" + y + "]");
        view.show(glyph.getGroup(), text, glyph.getWidth(), glyph.getHeight(), x, y);
    }

    @Override
    public void hide() {
        view.hide();
    }

}