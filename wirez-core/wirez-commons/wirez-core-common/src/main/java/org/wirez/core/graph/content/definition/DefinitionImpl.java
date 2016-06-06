package org.wirez.core.graph.content.definition;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;

@Portable
public class DefinitionImpl<T> implements Definition<T> {
    
    protected T definition;

    public DefinitionImpl(@MapsTo("definition") T definition ) {
        this.definition = definition;
    }

    @Override
    public T getDefinition() {
        return definition;
    }

    @Override
    public void setDefinition( final T definition ) {
        this.definition = definition;
    }


}
