package org.kie.workbench.common.stunner.core.graph.content.definition;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.kie.workbench.common.stunner.core.graph.content.Bounds;
import org.kie.workbench.common.stunner.core.graph.content.definition.DefinitionSet;

@Portable
public class DefinitionSetImpl implements DefinitionSet {
    
    private String id;
    private Bounds bounds;

    public DefinitionSetImpl( @MapsTo("id") String id ) {
        this.id = id;
    }

    @Override
    public String getDefinition() {
        return id;
    }

    @Override
    public void setDefinition( final String definition ) {
        this.id = definition;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public Bounds getBounds() {
        return bounds;
    }

    @Override
    public void setBounds( final Bounds bounds ) {
        this.bounds = bounds;
    }

}
