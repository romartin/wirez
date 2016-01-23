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
import org.wirez.client.workbench.screens.CanvasWizardScreen;
import org.wirez.client.workbench.screens.NotificationsScreen;
import org.wirez.client.workbench.screens.PaletteScreen;
import org.wirez.client.workbench.screens.PropertiesScreen;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@WorkbenchPerspective(identifier = "WirezPerspective", isTransient = false)
public class WirezPerspective {

    PanelDefinition palettePanel;
    PanelDefinition notificationsPanel;
    PanelDefinition propertiesPanel;
    
    @Perspective
    public PerspectiveDefinition buildPerspective() {
        PerspectiveDefinition perspective = new PerspectiveDefinitionImpl( MultiListWorkbenchPanelPresenter.class.getName() );
        perspective.setName("Wirez");

        // perspective.getRoot().addPart( new PartDefinitionImpl( new DefaultPlaceRequest( WirezCanvasScreen.SCREEN_ID ) ) );

        perspective.getRoot().addPart( new PartDefinitionImpl( new DefaultPlaceRequest( CanvasWizardScreen.SCREEN_ID ) ) );
        
        palettePanel = new PanelDefinitionImpl( MultiListWorkbenchPanelPresenter.class.getName() );
        palettePanel.setMinWidth( 400 );
        palettePanel.setWidth( 400 );
        palettePanel.addPart( new PartDefinitionImpl( new DefaultPlaceRequest( PaletteScreen.SCREEN_ID ) ) );

        propertiesPanel = new PanelDefinitionImpl( MultiListWorkbenchPanelPresenter.class.getName() );
        propertiesPanel.setMinWidth( PaletteScreen.WIDTH );
        propertiesPanel.setWidth( PaletteScreen.WIDTH );
        propertiesPanel.addPart( new PartDefinitionImpl( new DefaultPlaceRequest(PropertiesScreen.SCREEN_ID ) ) );
       
        notificationsPanel = new PanelDefinitionImpl( MultiListWorkbenchPanelPresenter.class.getName() );
        notificationsPanel.setMinWidth( 400 );
        notificationsPanel.setWidth( 400 );
        notificationsPanel.setMinHeight( 100 );
        notificationsPanel.setHeight( 100);
        notificationsPanel.addPart( new PartDefinitionImpl( new DefaultPlaceRequest(NotificationsScreen.SCREEN_ID ) ) );

        perspective.getRoot().insertChild( CompassPosition.WEST,
                palettePanel );

        perspective.getRoot().insertChild( CompassPosition.EAST,
                propertiesPanel );

        perspective.getRoot().insertChild( CompassPosition.SOUTH,
                notificationsPanel );

        return perspective;
    }

    public PanelDefinition getPalettePanel() {
        return palettePanel;
    }

    public PanelDefinition getNotificationsPanel() {
        return notificationsPanel;
    }

    public PanelDefinition getPropertiesPanel() {
        return propertiesPanel;
    }
}
