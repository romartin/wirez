package org.kie.workbench.common.stunner.core.command;

public interface HasCommandManagerListener<L extends CommandManagerListener> {

    void setCommandManagerListener( L listener );

}
