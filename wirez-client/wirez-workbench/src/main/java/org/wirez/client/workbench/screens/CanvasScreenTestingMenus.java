package org.wirez.client.workbench.screens;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.Window;
import org.uberfire.mvp.Command;
import org.uberfire.workbench.model.menu.MenuFactory;
import org.uberfire.workbench.model.menu.Menus;
import org.wirez.client.widgets.session.presenter.impl.AbstractFullSessionPresenter;
import org.wirez.client.widgets.session.presenter.impl.DefaultFullSessionPresenter;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandManagerImpl;
import org.wirez.core.client.canvas.command.CanvasViolation;
import org.wirez.core.client.canvas.controls.docking.DockingAcceptorControl;
import org.wirez.core.client.session.impl.DefaultCanvasFullSession;

import javax.enterprise.context.Dependent;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

@Dependent
public class CanvasScreenTestingMenus {

    private static Logger LOGGER = Logger.getLogger(CanvasScreenTestingMenus.class.getName());
    
    private Menus menu = null;
    private DefaultCanvasFullSession session;
    private HandlerRegistration mousePointerCoordsHandlerReg;
    private DefaultFullSessionPresenter canvasSessionPresenter;
    private boolean isDockingControlEnabled = true;
    
    public void init( final DefaultCanvasFullSession session,
            final DefaultFullSessionPresenter canvasSessionPresenter) {
        this.session = session;
        this.canvasSessionPresenter = canvasSessionPresenter;
        this.menu = makeMenuBar();
    }
    
    private Menus makeMenuBar() {
        // All commands moved to the canvas toolbar.
        /*return MenuFactory
                .newTopLevelMenu("Switch log level")
                .respondsWith(getSwitchLogLevelCommand())
                .endMenu()
                .newTopLevelMenu("Mouse pointer coords")
                .respondsWith(getMousePointerCoordsCommand())
                .endMenu()
                .newTopLevelMenu("Clear selection")
                .respondsWith(getClearSelectionCommand())
                .endMenu()
                .newTopLevelMenu("Delete selection")
                .respondsWith(getDeleteSelectionCommand())
                .endMenu()
                .newTopLevelMenu("Clear diagram")
                .respondsWith(getClearGridCommand())
                .endMenu()
                .newTopLevelMenu("Undo")
                .respondsWith(getUndoCommand())
                .endMenu()
                .newTopLevelMenu("Resume")
                .respondsWith(getResumeGraphCommand())
                .endMenu()
                .newTopLevelMenu("Visit graph")
                .respondsWith(getVisitGraphCommand())
                .endMenu()
                .newTopLevelMenu("Save")
                .respondsWith(getSaveCommand())
                .endMenu()
                .build();*/

        return MenuFactory
                .newTopLevelMenu("Switch Docking Ctrol")
                .respondsWith(getSwitchDockingControlCommand())
                .endMenu()
                .newTopLevelMenu("Log Command History")
                .respondsWith(getLogCommandStackCommand())
                .endMenu()
                .build();

    }

    public Menus getMenu() {
        return menu;
    }

    public void destroy() {
        this.canvasSessionPresenter = null;
        this.session = null;
    }
    
    
    private Command getLogCommandStackCommand() {

        return () -> {
            final CanvasCommandManagerImpl ccmi = (CanvasCommandManagerImpl) session.getCanvasCommandManager();
            final Stack<Stack<org.wirez.core.command.Command<AbstractCanvasHandler, CanvasViolation>>> history = ccmi.getHistory();
            logCommandHistory( history );
        };

    }

    private Command getSwitchDockingControlCommand() {

        return () -> {
            final DockingAcceptorControl<AbstractCanvasHandler> dockingControl = session.getDockingAcceptorControl();
            
            if ( isDockingControlEnabled ) {
                this.isDockingControlEnabled = false;
                dockingControl.disable();
                Window.alert("Docking control disabled");
            } else {
                this.isDockingControlEnabled = true;
                dockingControl.enable( session.getCanvasHandler() );
                Window.alert("Docking control enabled");
            }
        };

    }

    private void logCommandHistory( final Stack<Stack<org.wirez.core.command.Command<AbstractCanvasHandler, CanvasViolation>>> history ) {
        log( "**** COMMAND HISTORY START *********");

        if ( null == history ) {

            log( "History is null");

        } else {

            log( " ( FOUND " + history.size() + " ENTRIES )");

            for ( final Stack<org.wirez.core.command.Command<AbstractCanvasHandler, CanvasViolation>> entry : history ) {

                log( "--------------- History Entry Start ---------------");

                if ( entry.isEmpty() ) {

                    log( "No commands");

                } else {

                    int c = 0;
                    for ( final org.wirez.core.command.Command<AbstractCanvasHandler, CanvasViolation> command : entry ) {

                        logCommand( c, command );

                        c++;
                    }

                }

                log( "--------------- History Entry End ---------------");


            }

        }

        log( "**** COMMAND HISTORY END *********");

    }

    private void logCommand( final int count,
                             final org.wirez.core.command.Command<AbstractCanvasHandler, CanvasViolation> command ) {

        if ( null == command ) {

            log( "Command is null");

        } else {

            log( "Command [" + count + "] => " + command.toString() );
        }

    }

    private Command getSaveCommand() {

        return () -> {
            canvasSessionPresenter.save( () -> {});
        };

    }

    private Command getClearGridCommand() {
        return () -> canvasSessionPresenter.clear();
    }

    private Command getClearSelectionCommand() {
        return () -> canvasSessionPresenter.clearSelection();
    }

    private Command getDeleteSelectionCommand() {
        return () -> canvasSessionPresenter.deleteSelected();
    }

    private Command getUndoCommand() {
        return () -> canvasSessionPresenter.undo();
    }

    // While dev & testing.
    private Command getLogGraphCommand() {
        return () -> ( (AbstractFullSessionPresenter) canvasSessionPresenter).logGraph();
    }

    private Command getResumeGraphCommand() {
        return () -> ( (AbstractFullSessionPresenter) canvasSessionPresenter).resumeGraph();
    }

    private Command getVisitGraphCommand() {
        return () -> ( (AbstractFullSessionPresenter) canvasSessionPresenter).visitGraph();
    }

    // For testing...
    private Command getMousePointerCoordsCommand() {
        return new Command() {
            public void execute() {

                /*if ( null == mousePointerCoordsHandlerReg ) {
                    final LienzoLayer wiresLayer = (LienzoLayer) canvasSessionPresenter.getCanvasHandler().getCanvas().getLayer();
                    mousePointerCoordsHandlerReg = wiresLayer.getLienzoLayer().addNodeMouseMoveHandler(new NodeMouseMoveHandler() {
                        @Override
                        public void onNodeMouseMove(NodeMouseMoveEvent nodeMouseMoveEvent) {
                            LOGGER.log(Level.INFO, "Mouse at [" + nodeMouseMoveEvent.getX() + ", " + nodeMouseMoveEvent.getY() + "]");
                            *//*final GraphBoundsIndexer indexer = new GraphBoundsIndexer(canvasHandler.getDiagram().getGraph());
                            final Node node = indexer.getNodeAt(nodeMouseMoveEvent.getX(), nodeMouseMoveEvent.getY());
                            LOGGER.log(Level.INFO, "Node [" + ( node != null ? node.getUUID() : null ) +
                                    "at [" + nodeMouseMoveEvent.getX() + ", " + nodeMouseMoveEvent.getY() + "]");*//*
                        }
                    });

                } else {
                    mousePointerCoordsHandlerReg.removeHandler();
                    mousePointerCoordsHandlerReg = null;

                }*/

            }
        };
    }

    // For testing...
    private Command getSwitchLogLevelCommand() {
        return () -> {
            final Level level = Logger.getLogger("").getLevel();
            final Level newLevel = Level.SEVERE.equals(level) ? Level.FINE : Level.SEVERE;
            LOGGER.log(Level.SEVERE, "Switching to log level [" + newLevel.getName() + "]");
            Logger.getLogger("").setLevel(newLevel);
        };
    }

    private void log(final String message) {
        if (LogConfiguration.loggingIsEnabled()) {
            LOGGER.log(Level.SEVERE, message);
        }
    }

    private void log(final Level level, final String message) {
        if (LogConfiguration.loggingIsEnabled()) {
            LOGGER.log(level, message);
        }
    }
}
