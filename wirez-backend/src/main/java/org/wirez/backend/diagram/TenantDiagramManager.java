package org.wirez.backend.diagram;

import org.wirez.core.api.DiagramManager;
import org.wirez.core.backend.annotation.Application;
import org.wirez.core.backend.annotation.Tenant;
import org.wirez.core.diagram.Diagram;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Collection;

/**
 * The diagram service for each tenant.
 */
@SessionScoped
@Tenant
public class TenantDiagramManager implements DiagramManager<Diagram>, Serializable {

    DiagramManager<Diagram> appDiagramManager;
    
    protected TenantDiagramManager() {
        this( null );
    }
    
    @Inject
    public TenantDiagramManager( @Application DiagramManager<Diagram> appDiagramManager) {
        this.appDiagramManager = appDiagramManager;
    }

    @Override
    public void update(Diagram diagram) {
        appDiagramManager.update( diagram );
    }

    @Override
    public void register(Diagram diagram) {
        appDiagramManager.register( diagram );
    }

    @Override
    public boolean contains(Diagram item) {
        return appDiagramManager.contains( item );
    }

    @Override
    public boolean remove(Diagram diagram) {
        return appDiagramManager.remove( diagram );
    }

    @Override
    public Diagram getDiagramByUUID(String uuid) {
        return appDiagramManager.getDiagramByUUID( uuid );
    }

    @Override
    public Collection<Diagram> getItems() {
        return appDiagramManager.getItems();
    }

    @Override
    public void clear() {
        appDiagramManager.clear();
    }
    
}
