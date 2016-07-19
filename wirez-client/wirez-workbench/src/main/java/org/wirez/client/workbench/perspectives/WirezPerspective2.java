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
package org.wirez.client.workbench.perspectives;

import org.uberfire.client.annotations.Perspective;
import org.uberfire.client.annotations.WorkbenchPerspective;
import org.uberfire.client.workbench.panels.impl.MultiListWorkbenchPanelPresenter;
import org.uberfire.mvp.impl.DefaultPlaceRequest;
import org.uberfire.workbench.model.CompassPosition;
import org.uberfire.workbench.model.PanelDefinition;
import org.uberfire.workbench.model.PerspectiveDefinition;
import org.uberfire.workbench.model.impl.PanelDefinitionImpl;
import org.uberfire.workbench.model.impl.PartDefinitionImpl;
import org.uberfire.workbench.model.impl.PerspectiveDefinitionImpl;
import org.wirez.client.widgets.loading.LoadingBox;
import org.wirez.client.workbench.screens.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * This perspective is used for showing other widgets rather than the default ones used in WirezPerspective,
 * such as another palette and another properties editor.
 */
@ApplicationScoped
@WorkbenchPerspective( identifier = "WirezPerspective2", isTransient = false )
public class WirezPerspective2 {

    public static final int WEST_PANEL_WIDTH = 80;
    public static final int EAST_PANEL_WIDTH = 300;

    PanelDefinition defSetPalettePanel;
    PanelDefinition definitionsPalettePanel;
    PanelDefinition notificationsPanel;
    PanelDefinition propertiesPanel;
    PanelDefinition treeExplorerPanel;

    @Inject
    LoadingBox loadingBox;

    @Perspective
    public PerspectiveDefinition buildPerspective() {
        PerspectiveDefinition perspective = new PerspectiveDefinitionImpl( MultiListWorkbenchPanelPresenter.class.getName() );
        perspective.setName( "Wirez with other widgets" );

        perspective.getRoot().addPart( new PartDefinitionImpl( new DefaultPlaceRequest( CanvasWizardScreen.SCREEN_ID ) ) );

        // Lienzo Definitions Palette ( all Definitions in a single level, no grouping ).
        definitionsPalettePanel = new PanelDefinitionImpl( MultiListWorkbenchPanelPresenter.class.getName() );
        definitionsPalettePanel.setMinWidth( EAST_PANEL_WIDTH );
        definitionsPalettePanel.setWidth( EAST_PANEL_WIDTH );
        definitionsPalettePanel.setMinHeight( 300 );
        definitionsPalettePanel.setHeight( 450 );
        definitionsPalettePanel.addPart( new PartDefinitionImpl( new DefaultPlaceRequest( LienzoDefinitionsPaletteScreen.SCREEN_ID ) ) );

        // Lienzo Definition Set Palette ( group by morph base )
        defSetPalettePanel = new PanelDefinitionImpl( MultiListWorkbenchPanelPresenter.class.getName() );
        defSetPalettePanel.setMinWidth( WEST_PANEL_WIDTH );
        defSetPalettePanel.setWidth( WEST_PANEL_WIDTH );
        defSetPalettePanel.addPart( new PartDefinitionImpl( new DefaultPlaceRequest( LienzoDefinitionSetPaletteScreen.SCREEN_ID ) ) );
        defSetPalettePanel.appendChild( CompassPosition.SOUTH, definitionsPalettePanel );

        treeExplorerPanel = new PanelDefinitionImpl( MultiListWorkbenchPanelPresenter.class.getName() );
        treeExplorerPanel.setMinWidth( EAST_PANEL_WIDTH );
        treeExplorerPanel.setWidth( EAST_PANEL_WIDTH );
        treeExplorerPanel.setMinHeight( 100 );
        treeExplorerPanel.setHeight( 300 );
        treeExplorerPanel.addPart( new PartDefinitionImpl( new DefaultPlaceRequest( TreeExplorerScreen.SCREEN_ID ) ) );

        propertiesPanel = new PanelDefinitionImpl( MultiListWorkbenchPanelPresenter.class.getName() );
        propertiesPanel.setMinWidth( EAST_PANEL_WIDTH );
        propertiesPanel.setWidth( EAST_PANEL_WIDTH );
        propertiesPanel.addPart( new PartDefinitionImpl( new DefaultPlaceRequest( PropertiesScreen.SCREEN_ID ) ) );
        propertiesPanel.appendChild( CompassPosition.SOUTH, treeExplorerPanel );

        notificationsPanel = new PanelDefinitionImpl( MultiListWorkbenchPanelPresenter.class.getName() );
        notificationsPanel.setMinWidth( 400 );
        notificationsPanel.setWidth( 400 );
        notificationsPanel.setMinHeight( 100 );
        notificationsPanel.setHeight( 100 );
        notificationsPanel.addPart( new PartDefinitionImpl( new DefaultPlaceRequest( NotificationsScreen.SCREEN_ID ) ) );

        perspective.getRoot().insertChild( CompassPosition.WEST,
                defSetPalettePanel );

        perspective.getRoot().insertChild( CompassPosition.EAST,
                propertiesPanel );

        perspective.getRoot().insertChild( CompassPosition.SOUTH,
                notificationsPanel );

        return perspective;
    }

}
