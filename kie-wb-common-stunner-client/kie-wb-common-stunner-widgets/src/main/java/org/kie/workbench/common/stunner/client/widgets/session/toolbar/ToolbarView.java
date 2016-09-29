package org.kie.workbench.common.stunner.client.widgets.session.toolbar;

import com.google.gwt.user.client.ui.IsWidget;
import org.kie.workbench.common.stunner.client.widgets.session.toolbar.impl.AbstractToolbar;
import org.uberfire.client.mvp.UberView;
import org.kie.workbench.common.stunner.core.client.session.CanvasSession;

public interface ToolbarView<S extends CanvasSession> extends UberView<AbstractToolbar> {

    ToolbarView<S> addItem( IsWidget toolbarItemView );

    ToolbarView<S> show();

    ToolbarView<S> hide();

    ToolbarView<S> clear();

    void destroy();

}