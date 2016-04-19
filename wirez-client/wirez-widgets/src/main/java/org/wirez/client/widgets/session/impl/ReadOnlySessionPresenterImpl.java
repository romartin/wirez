package org.wirez.client.widgets.session.impl;

import org.uberfire.client.workbench.widgets.common.ErrorPopupPresenter;
import org.wirez.core.client.service.ClientDiagramServices;
import org.wirez.core.client.session.impl.DefaultCanvasReadOnlySession;
import org.wirez.core.client.session.impl.DefaultCanvasSessionManager;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class ReadOnlySessionPresenterImpl extends AbstractReadOnlySessionPresenter<DefaultCanvasReadOnlySession>
 implements DefaultReadOnlySessionPresenter {

    @Inject
    public ReadOnlySessionPresenterImpl(final DefaultCanvasSessionManager canvasSessionManager,
                                        final ClientDiagramServices clientDiagramServices,
                                        final ErrorPopupPresenter errorPopupPresenter) {
        super(canvasSessionManager, clientDiagramServices, errorPopupPresenter);
    }
    
}
