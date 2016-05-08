package org.wirez.backend.diagram;

import org.wirez.backend.registry.SyncRegistry;
import org.wirez.core.api.AbstractDiagramManager;
import org.wirez.core.api.DiagramManager;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.registry.diagram.DiagramRegistry;
import org.wirez.core.backend.annotation.Application;
import org.wirez.core.backend.annotation.VFS;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;

/**
 * The application's global diagram manager.
 */
@ApplicationScoped
@Application
public class ApplicationDiagramManager extends AbstractDiagramManager<Diagram> {

    DiagramManager<Diagram> vfsDiagramManager;
    
    protected ApplicationDiagramManager() {
    }
    
    @Inject
    public ApplicationDiagramManager(@SyncRegistry DiagramRegistry<Diagram> registry,
                                     @VFS  DiagramManager<Diagram> vfsDiagramManager) {
        super(registry);
        this.vfsDiagramManager = vfsDiagramManager;
    }
    
    @PostConstruct
    public void initializeCache() {
        
        // Load vfs diagrams and put into the registry.
        final Collection<Diagram> diagrams = vfsDiagramManager.getItems();
        if ( null != diagrams && !diagrams.isEmpty() ) {
            for ( Diagram diagram : diagrams ) {
                this.add( diagram );
            }
        }
        
    }

    @Override
    public void update(Diagram diagram) {
        super.update(diagram);
        
        // Update the VFS storage.
        vfsDiagramManager.update( diagram );
    }

    @Override
    public void add(Diagram diagram) {
        super.add(diagram);

        // Update the VFS storage.
        vfsDiagramManager.add( diagram );
    }

    @Override
    public void remove(Diagram diagram) {
        super.remove(diagram);

        // Update the VFS storage.
        vfsDiagramManager.remove( diagram );
    }
    
}
