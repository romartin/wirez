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

package org.wirez.client.widgets.wizard;

import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.uberfire.client.mvp.UberView;
import org.uberfire.mvp.Command;
import org.wirez.client.widgets.event.ShapeSetSelectedEvent;
import org.wirez.core.client.ShapeSet;
import org.wirez.core.client.WirezClientManager;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.Collection;

@Dependent
public class CanvasWizard implements IsWidget {

    public interface View extends UberView<CanvasWizard> {
    
        View setHeading(String title);
        
        View addItem(String name, String description, SafeUri thumbnailUri, boolean isSelected, Command clickHandler);
        
        View showEmptyView();
        
        View setActionButtonText(String caption);
        
        View setActionButtonEnabled(boolean isEnabled);
        
        View clear();
        
    }

    WirezClientManager wirezClientManager;
    Event<ShapeSetSelectedEvent> shapeSetSelectedEvent;
    View view;
    private String selectedShapeSetId;

    @Inject
    public CanvasWizard(final WirezClientManager wirezClientManager, 
                        final Event<ShapeSetSelectedEvent> shapeSetSelectedEvent,
                        final View view) {
        this.wirezClientManager = wirezClientManager;
        this.shapeSetSelectedEvent = shapeSetSelectedEvent;
        this.view = view;
    }
    
    @PostConstruct
    public void init() {
        view.init(this);
        view.setHeading("Choose a diagram:");
        view.setActionButtonText("Create");
        view.setActionButtonEnabled(false);
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }
    
    public void show() {
        view.clear();
        final Collection<ShapeSet> shapeSets = wirezClientManager.getShapeSets();
        if (shapeSets == null || shapeSets.isEmpty()) {
            view.showEmptyView();
        } else {
            for (final ShapeSet shapeSet : shapeSets) {
                final String uuid = shapeSet.getId();
                final String name = shapeSet.getName();
                final String description = shapeSet.getDescription();
                final SafeUri thumbnailUri = shapeSet.getThumbnailUri();
                final boolean isSelected = selectedShapeSetId == null || selectedShapeSetId.equals(uuid);
                view.addItem(name, description, thumbnailUri, isSelected, 
                        new Command() {
                            @Override
                            public void execute() {
                                CanvasWizard.this.onShapeSetSelected(uuid);
                            }
                        });
                view.setActionButtonEnabled(selectedShapeSetId != null);
                
            }
        }
        
    }
    
    public void clear() {
        selectedShapeSetId = null;
        view.setActionButtonEnabled(false);
        view.clear();
    }

    private void onShapeSetSelected(final String shapeSetId) {
        selectedShapeSetId = shapeSetId;
        show();
    }

    void onActionButtonClick() {
        assert selectedShapeSetId != null;
        shapeSetSelectedEvent.fire(new ShapeSetSelectedEvent(selectedShapeSetId));
        clear();
        show();
    }
}
