package org.wirez.core.backend.diagram.tenant;

import org.wirez.core.api.AbstractDiagramManager;
import org.wirez.core.api.DiagramManager;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.registry.DiagramRegistry;
import org.wirez.core.api.registry.List;
import org.wirez.core.backend.Application;
import org.wirez.core.backend.Tenant;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Collection;

/**
 * The diagram service for each tenant.
 */
@SessionScoped
@Tenant
public class TenantDiagramManager extends AbstractDiagramManager<Diagram> implements Serializable {

    DiagramManager<Diagram> appDiagramManager;
    
    protected TenantDiagramManager() {
        
    }
    
    @Inject
    public TenantDiagramManager(@List DiagramRegistry<Diagram> registry,
                                @Application DiagramManager<Diagram> appDiagramManager) {
        super(registry);
        this.appDiagramManager = appDiagramManager;
    }

    @PostConstruct
    public void init() {

        // Load current existing diagrams and put into the session registry.
        final Collection<Diagram> diagrams = appDiagramManager.getItems();
        if ( null != diagrams && !diagrams.isEmpty() ) {
            for ( Diagram diagram : diagrams ) {
                this.add( diagram );
            }
        }

    }

    @Override
    public void update(Diagram diagram) {
        super.update( diagram );
        
        // Synchronize global diagram registries.
        appDiagramManager.update( diagram );
    }

    @Override
    public void add(Diagram diagram) {
        super.add( diagram );
        
        // Synchronize global diagram registries.
        appDiagramManager.add( diagram );
    }

    @Override
    public void remove(Diagram diagram) {
        super.remove( diagram );

        // Synchronize global diagram registries.
        appDiagramManager.remove( diagram );
    }
    
}
