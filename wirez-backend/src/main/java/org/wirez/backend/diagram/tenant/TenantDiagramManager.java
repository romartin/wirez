package org.wirez.backend.diagram.tenant;

import org.wirez.core.api.DiagramManager;
import org.wirez.core.diagram.Diagram;
import org.wirez.core.registry.List;
import org.wirez.core.registry.diagram.DiagramRegistry;
import org.wirez.core.backend.annotation.Application;
import org.wirez.core.backend.annotation.Tenant;

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
        
    }
    
    @Inject
    public TenantDiagramManager(@List DiagramRegistry<Diagram> registry,
                                @Application DiagramManager<Diagram> appDiagramManager) {
        this.appDiagramManager = appDiagramManager;
    }

    @Override
    public void update(Diagram diagram) {
        appDiagramManager.update( diagram );
    }

    @Override
    public void add(Diagram diagram) {
        appDiagramManager.add( diagram );
    }

    @Override
    public boolean contains(Diagram item) {
        return appDiagramManager.contains( item );
    }

    @Override
    public void remove(Diagram diagram) {
        appDiagramManager.remove( diagram );
    }

    @Override
    public Diagram get(String uuid) {
        return appDiagramManager.get( uuid );
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
