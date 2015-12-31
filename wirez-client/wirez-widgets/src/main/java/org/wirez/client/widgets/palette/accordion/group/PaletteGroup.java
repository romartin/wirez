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

package org.wirez.client.widgets.palette.accordion.group;

import com.ait.lienzo.client.core.shape.IPrimitive;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.uberfire.client.mvp.UberView;
import org.uberfire.mvp.Command;
import org.wirez.client.widgets.palette.accordion.group.layout.HorizLayoutBuilder;
import org.wirez.client.widgets.palette.accordion.group.layout.LayoutBuilder;
import org.wirez.core.client.ShapeGlyph;
import org.wirez.core.client.WirezClientManager;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class PaletteGroup implements IsWidget {

    public interface View extends UberView<PaletteGroup> {

        View setSize(double width, double height);
            
        View setHeader(String title);
        
        View addGlyph(IPrimitive glyphView, double x, double y, GlyphViewCallback callback);
        
        View showTooltip(String title, double x, double y, int duration);
        
        View clear();
    }

    public interface GlyphViewCallback {
        void onClick();
        void onNodeMouseMove(double x, double y);
    }
    
    WirezClientManager wirezClientManager;
    View view;
    LayoutBuilder layoutBuilder;

    @Inject
    public PaletteGroup(final View view,
                        final WirezClientManager wirezClientManager) {
        this.view = view;
        this.wirezClientManager = wirezClientManager;
    }

    @PostConstruct
    public void init() {
        view.init(this);
        layoutBuilder = new HorizLayoutBuilder();
        setSize(200, 200);
    }
    
    @Override
    public Widget asWidget() {
        return view.asWidget();
    }
    
    public void setHeader(final String header) {
        view.setHeader(header);
    }
    
    public void setSize(final double width, final double height) {
        view.setSize(width, height);
        layoutBuilder.setSize(width, height);
    }

    public void addGlyph(final String description, final ShapeGlyph glyph, final Command callback) {
        assert glyph != null;

        final double[] positions = layoutBuilder.add(glyph.getWidth(), glyph.getHeight());
        view.addGlyph(glyph.getGroup().setDraggable(false), positions[0], positions[1], new GlyphViewCallback() {
            @Override
            public void onClick() {
                callback.execute();
            }

            @Override
            public void onNodeMouseMove(final double x, final double y) {
                final double tx = x + ( glyph.getWidth() / 2);
                final double ty = y + ( glyph.getHeight() / 2);
                view.showTooltip(description, tx, ty, 1000);

            }
        });
        
    }

    public void clear() {
        layoutBuilder.clear();
        view.clear();
    }
    
}
