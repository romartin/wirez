package org.wirez.core.graph.content.definition;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;

@Portable
public class DefinitionSetImpl implements DefinitionSet {
    
    private String id;

    public DefinitionSetImpl(@MapsTo("id") String id) {
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
}
