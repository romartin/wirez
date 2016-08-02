package org.wirez.core.api;

import org.wirez.core.diagram.Diagram;
import org.wirez.core.registry.diagram.DiagramRegistry;

import java.util.Collection;
import java.util.Collections;

public abstract class AbstractDiagramManager<D extends Diagram> implements DiagramManager<D> {
    
    protected final DiagramRegistry<D> registry;

    protected AbstractDiagramManager() {
        this.registry = null;
    }
    
    public AbstractDiagramManager(final DiagramRegistry<D> registry) {
        this.registry = registry;
    }

    @Override
    public void update( final D diagram ) {
        registry.update( diagram );
    }

    @Override
    public void register( final D item ) {
        registry.register( item );
    }

    @Override
    public boolean contains( final D item ) {
        return registry.contains( item );
    }

    @Override
    public boolean remove( final D item ) {
        return registry.remove( item );
    }

    @Override
    public D getDiagramByUUID( final String uuid ) {
        return registry.getDiagramByUUID( uuid );
    }

    @Override
    public Collection<D> getItems() {
        return Collections.unmodifiableCollection( registry.getItems() );
    }

    @Override
    public void clear() {
        registry.clear();
    }
}
