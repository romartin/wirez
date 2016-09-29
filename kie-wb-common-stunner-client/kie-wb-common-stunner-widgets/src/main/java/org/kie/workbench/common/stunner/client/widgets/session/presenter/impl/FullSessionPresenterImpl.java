package org.kie.workbench.common.stunner.client.widgets.session.presenter.impl;

import org.kie.workbench.common.stunner.client.widgets.session.toolbar.command.*;
import org.uberfire.client.workbench.widgets.common.ErrorPopupPresenter;
import org.kie.workbench.common.stunner.client.widgets.session.toolbar.impl.AbstractToolbar;
import org.kie.workbench.common.stunner.core.client.api.ClientDefinitionManager;
import org.kie.workbench.common.stunner.core.client.command.factory.CanvasCommandFactory;
import org.kie.workbench.common.stunner.core.client.service.ClientDiagramServices;
import org.kie.workbench.common.stunner.core.client.service.ClientFactoryServices;
import org.kie.workbench.common.stunner.core.client.session.impl.DefaultCanvasFullSession;
import org.kie.workbench.common.stunner.core.client.session.impl.DefaultCanvasSessionManager;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class FullSessionPresenterImpl extends AbstractFullSessionPresenter<DefaultCanvasFullSession> implements DefaultFullSessionPresenter {

    @Inject
    public FullSessionPresenterImpl(final DefaultCanvasSessionManager canvasSessionManager,
                                    final ClientDefinitionManager clientDefinitionManager,
                                    final ClientFactoryServices clientFactoryServices,
                                    final AbstractToolbar<DefaultCanvasFullSession> toolbar,
                                    final ClearSelectionCommand clearSelectionCommand,
                                    final ClearCommand clearCommand,
                                    final DeleteSelectionCommand deleteSelectionCommand,
                                    final SaveCommand saveCommand,
                                    final UndoCommand undoCommand,
                                    final ValidateCommand validateCommand,
                                    final VisitGraphCommand visitGraphCommand,
                                    final SwitchGridCommand switchGridCommand,
                                    final CanvasCommandFactory commandFactory,
                                    final ClientDiagramServices clientDiagramServices,
                                    final ErrorPopupPresenter errorPopupPresenter,
                                    final View view) {
        super( canvasSessionManager, clientDefinitionManager, clientFactoryServices,
                commandFactory, clientDiagramServices, toolbar, clearSelectionCommand, clearCommand, 
                deleteSelectionCommand, saveCommand, undoCommand, validateCommand, visitGraphCommand,
                switchGridCommand, errorPopupPresenter, view );
    }
    
}
