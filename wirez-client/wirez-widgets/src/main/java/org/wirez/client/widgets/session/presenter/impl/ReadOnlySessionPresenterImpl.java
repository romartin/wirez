package org.wirez.client.widgets.session.presenter.impl;

import org.uberfire.client.workbench.widgets.common.ErrorPopupPresenter;
import org.wirez.client.widgets.session.toolbar.command.ClearSelectionCommand;
import org.wirez.client.widgets.session.toolbar.command.SwitchGridCommand;
import org.wirez.client.widgets.session.toolbar.command.VisitGraphCommand;
import org.wirez.client.widgets.session.toolbar.impl.AbstractToolbar;
import org.wirez.core.client.service.ClientDiagramServices;
import org.wirez.core.client.session.impl.DefaultCanvasReadOnlySession;
import org.wirez.core.client.session.impl.DefaultCanvasSessionManager;

// TODO: @Dependent - As there is no bean injection for AbstractToolbar<DefaultCanvasReadOnlySession> yet, this class
// is abstract and not eligible by the bean manager for now.
public abstract class ReadOnlySessionPresenterImpl extends AbstractReadOnlySessionPresenter<DefaultCanvasReadOnlySession>
 implements DefaultReadOnlySessionPresenter {

    public ReadOnlySessionPresenterImpl(final DefaultCanvasSessionManager canvasSessionManager,
                                        final ClientDiagramServices clientDiagramServices,
                                        final AbstractToolbar<DefaultCanvasReadOnlySession> toolbar,
                                        final ClearSelectionCommand clearSelectionCommand,
                                        final VisitGraphCommand visitGraphCommand,
                                        final SwitchGridCommand switchGridCommand,
                                        final ErrorPopupPresenter errorPopupPresenter,
                                        final View view) {
        super( canvasSessionManager, clientDiagramServices, toolbar, clearSelectionCommand, visitGraphCommand,
                switchGridCommand, errorPopupPresenter, view );
    }
    
}
