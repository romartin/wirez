package org.wirez.client.workbench.screens;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.Window;
import org.uberfire.mvp.Command;
import org.uberfire.workbench.model.menu.MenuFactory;
import org.uberfire.workbench.model.menu.Menus;
import org.wirez.client.widgets.session.presenter.impl.AbstractFullSessionPresenter;
import org.wirez.client.widgets.session.presenter.impl.DefaultFullSessionPresenter;
import org.wirez.core.client.api.platform.ClientPlatform;
import org.wirez.core.client.api.platform.PlatformManager;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.command.CanvasViolation;
import org.wirez.core.client.canvas.controls.docking.DockingAcceptorControl;
import org.wirez.core.client.session.impl.DefaultCanvasFullSession;
import org.wirez.core.command.stack.StackCommandManager;
import org.wirez.core.util.WirezLogger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class if a helper during development. It provides menu entires for some testing stuff.
 */
@Dependent
public class CanvasScreenTestingMenus {

    private static Logger LOGGER = Logger.getLogger(CanvasScreenTestingMenus.class.getName());

    @Inject
    PlatformManager platformManager;

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
                .newTopLevelMenu("Switch Docking Control")
                .respondsWith(getSwitchDockingControlCommand())
                .endMenu()
                .newTopLevelMenu("Log Client Platform")
                .respondsWith(getLogCurrentClientPlatform())
                .endMenu()
                newTopLevelMenu("Log Canvas Image Data")
                .respondsWith(getLogCanvasImageData())
                .endMenu()
                .build();*/

        return MenuFactory
                .newTopLevelMenu("Switch log level")
                .respondsWith(getSwitchLogLevelCommand())
                .endMenu()
                .newTopLevelMenu("Log Command History")
                .respondsWith(getLogCommandStackCommand())
                .endMenu()
                .newTopLevelMenu("Log Selected Node")
                .respondsWith(getLogNodeCommand())
                .endMenu()
                .newTopLevelMenu("Log Graph")
                .respondsWith(getLogGraphCommand())
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

    private Command getLogCanvasImageData() {

        return () -> {

            final String dataURL = session.getCanvas().getLayer().toDataURL();
            GWT.log("DATA URL = \"" + dataURL + "\"" );

        };

    }

    private Command getLogCurrentClientPlatform() {

        return () -> {
            
            final ClientPlatform p = platformManager.getCurrentPlatform();
            
        };

    }

    @SuppressWarnings( "unchecked" )
    private Command getLogCommandStackCommand() {

        return () -> {

            final Iterable<Iterable<org.wirez.core.command.Command<AbstractCanvasHandler, CanvasViolation>>> history =
                    ( ( StackCommandManager<AbstractCanvasHandler, CanvasViolation>) session.getCanvasCommandManager()).getRegistry().getCommandHistory();
            Logger.getLogger("").setLevel( Level.FINE );
            logCommandHistory( history );
            Logger.getLogger("").setLevel( Level.SEVERE );
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

    private void logCommandHistory( final Iterable<Iterable<org.wirez.core.command.Command<AbstractCanvasHandler, CanvasViolation>>> history ) {
        log( "**** COMMAND HISTORY START *********");

        if ( null == history ) {

            log( "History is null");

        } else {


            int x = 0;
            for ( final Iterable<org.wirez.core.command.Command<AbstractCanvasHandler, CanvasViolation>> entry : history ) {

                log( "--------------- History Entry Start ---------------");

                if ( entry.iterator().hasNext() ) {

                    log( "No commands");

                } else {

                    int c = 0;
                    for ( final org.wirez.core.command.Command<AbstractCanvasHandler, CanvasViolation> command : entry ) {

                        logCommand( c, command );

                        c++;
                    }

                }

                log( "--------------- History Entry End ---------------");

                x++;

            }

            log( " ( FOUND " + x + " ENTRIES )");


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
        return () -> {
            Logger.getLogger("").setLevel( Level.FINE );
            logGraph();
            Logger.getLogger("").setLevel( Level.SEVERE );
        };
    }

    private Command getLogNodeCommand() {
        return () -> {
            Logger.getLogger("").setLevel( Level.FINE );

            final Collection<String> selected = session.getShapeSelectionControl().getSelectedItems();

            if ( null == selected || selected.isEmpty() ) {
                throw new RuntimeException( "No selecte elements." );
            }

            final String uuid = selected.iterator().next();

            logNode( uuid );

            Logger.getLogger("").setLevel( Level.SEVERE );
        };
    }

    public void logGraph() {
        WirezLogger.log( session.getCanvasHandler().getDiagram().getGraph() );
    }

    public void logNode( final String uuid ) {
        WirezLogger.log( session.getCanvasHandler().getDiagram().getGraph().getNode( uuid ) );
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
            final Level level = Logger.getLogger("org.wirez").getLevel();
            final Level newLevel = Level.SEVERE.equals(level) ? Level.FINE : Level.SEVERE;
            GWT.log( "Switching to level " + newLevel.toString() );
            Logger.getLogger("org.wirez").setLevel(newLevel);
        };
    }

    private void log(final String message) {
        if (LogConfiguration.loggingIsEnabled()) {
            LOGGER.log(Level.INFO, message);
        }
    }

    private void log(final Level level, final String message) {
        if (LogConfiguration.loggingIsEnabled()) {
            LOGGER.log(level, message);
        }
    }
}
