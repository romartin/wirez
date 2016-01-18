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
import com.ait.lienzo.client.widget.LienzoPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.uberfire.client.mvp.UberView;
import org.wirez.client.widgets.palette.accordion.group.layout.HorizLayoutBuilder;
import org.wirez.client.widgets.palette.accordion.group.layout.HorizLayoutSettings;
import org.wirez.client.widgets.palette.accordion.group.layout.LayoutBuilder;
import org.wirez.core.client.ShapeGlyph;
import org.wirez.core.client.ShapeManager;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Collection;

@Dependent
public class PaletteGroup implements IsWidget {

    public interface View extends UberView<PaletteGroup> {

        View setSize(double width, double height);
            
        View setHeader(String title);
        
        View addGlyph(IPrimitive glyphView, double width, double height, double x, double y, GlyphViewCallback callback);
        
        View clear();
    }

    public interface GlyphViewCallback {

        void onFocus(double x, double y);

        void onLostFocus();
        
        void onClick();
        
        void onMouseDown(LienzoPanel lienzoPanel, double x, double y);
        
    }
    
    ShapeManager wirezClientManager;
    View view;
    LayoutBuilder layoutBuilder;

    @Inject
    public PaletteGroup(final View view,
                        final ShapeManager wirezClientManager) {
        this.view = view;
        this.wirezClientManager = wirezClientManager;
    }

    @PostConstruct
    public void init() {
        view.init(this);
        layoutBuilder = new HorizLayoutBuilder();
    }
    
    @Override
    public Widget asWidget() {
        return view.asWidget();
    }
    
    
    public static PaletteGroupItem buildItem(final ShapeGlyph glyph,
                                             final String description,
                                             final PaletteGroupItem.Handler clickHandler) {
        return new PaletteGroupItemImpl(glyph, description, clickHandler);
    }
    
    private static class PaletteGroupItemImpl implements PaletteGroupItem {
        private final ShapeGlyph glyph;
        private final String description;
        private final Handler clickHandler;
        double x;
        double y;
        
        public PaletteGroupItemImpl(final ShapeGlyph glyph, 
                                    final String description, 
                                    final Handler clickHandler) {
            this.glyph = glyph;
            this.description = description;
            this.clickHandler = clickHandler;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public ShapeGlyph getGlyph() {
            return glyph;
        }

        @Override
        public Handler getClickHandler() {
            return clickHandler;
        }
    }
    
    public void show(final String header, final double width, final Collection<PaletteGroupItem> items) {
        
        // Group header.
        view.setHeader(header);
        
        // Layout item positions calculations.
        final double[] size = buildItemPositions(width, items);
        
     
        // View init & canvas size.
        view.setSize(size[0], size[1]);


        if (null != items && !items.isEmpty()) {
            for (final PaletteGroupItem item : items) {
                final PaletteGroupItemImpl itemImpl = (PaletteGroupItemImpl) item;
                final ShapeGlyph glyph = item.getGlyph();
                final PaletteGroupItem.Handler callback = item.getClickHandler();

                // Add the glyph view to the canvas..
                view.addGlyph(glyph.getGroup().setDraggable(false),
                        glyph.getWidth(), glyph.getHeight(),
                        itemImpl.x, itemImpl.y, new GlyphViewCallback() {
                            @Override
                            public void onFocus(double x, double y) {
                                callback.onFocus(x, y);
                            }

                            @Override
                            public void onLostFocus() {
                                callback.onLostFocus();
                            }

                            @Override
                            public void onClick() {
                                callback.onClick();
                            }

                            @Override
                            public void onMouseDown(final LienzoPanel panel, final double x, final double y) {
                                callback.onDragStart(panel, x, y);
                            }
                        });
            }
        }
        
        
        
    }
    
    private double[] buildItemPositions(final double width, final Collection<PaletteGroupItem> items) {
        
        // Layout settings.
        layoutBuilder.setSettings(new HorizLayoutSettings(width, 10));
        
        // Glyph positions.
        if ( null != items && !items.isEmpty() ) {
            for (final PaletteGroupItem item : items) {
                PaletteGroupItemImpl itemImpl = (PaletteGroupItemImpl) item;
                final double[] positions = layoutBuilder.add(item.getGlyph().getWidth(), item.getGlyph().getHeight());
                itemImpl.x = positions[0];
                itemImpl.y = positions[1];
                GWT.log("PaletteGroup#buildItemPositions [desc=" + item.getDescription() + ", x=" + itemImpl.x + ", y=" + itemImpl.y + "]");
            }
        }
        
        return layoutBuilder.build();
    }


    public void clear() {
        layoutBuilder.clear();
        view.clear();
    }
    
}
