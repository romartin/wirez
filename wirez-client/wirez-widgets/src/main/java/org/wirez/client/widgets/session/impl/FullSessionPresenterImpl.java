package org.wirez.client.widgets.session.impl;

import org.uberfire.client.workbench.widgets.common.ErrorPopupPresenter;
import org.wirez.core.client.ClientDefinitionManager;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.service.ClientDiagramServices;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.client.session.impl.DefaultCanvasFullSession;
import org.wirez.core.client.session.impl.DefaultCanvasSessionManager;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class FullSessionPresenterImpl extends AbstractFullSessionPresenter<DefaultCanvasFullSession> implements DefaultFullSessionPresenter {

    @Inject
    public FullSessionPresenterImpl(final DefaultCanvasSessionManager canvasSessionManager,
                                    final ClientDefinitionManager clientDefinitionManager,
                                    final ClientFactoryServices clientFactoryServices,
                                    final CanvasCommandFactory commandFactory,
                                    final ClientDiagramServices clientDiagramServices,
                                    final ErrorPopupPresenter errorPopupPresenter) {
        super(canvasSessionManager, clientDefinitionManager, clientFactoryServices, 
                commandFactory, clientDiagramServices, errorPopupPresenter);
    }
    
}
