package org.wirez.core.api.graph.content;

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
    public String toString() {
        return id;
    }
}
