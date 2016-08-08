package org.wirez.core.command;

public interface HasCommandManagerListener<L extends CommandManagerListener> {

    void setCommandManagerListener( L listener );

}
