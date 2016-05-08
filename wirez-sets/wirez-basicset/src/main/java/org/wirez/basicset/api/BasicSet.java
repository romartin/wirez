package org.wirez.basicset.api;

import org.jboss.errai.databinding.client.api.Bindable;
import org.wirez.core.api.definition.annotation.Description;
import org.wirez.core.api.definition.annotation.definitionset.DefinitionSet;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.rule.annotation.CanContain;
import org.wirez.core.client.annotation.ShapeSet;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Bindable
@DefinitionSet(
        type = Graph.class,
        definitions = {
                Rectangle.class,
                Circle.class,
                Polygon.class
        }
)
@CanContain( roles = { "all" } )
@ShapeSet( thumb = "org.wirez.basicset.client.resources.BasicSetmageResources.INSTANCE.basicSetThumb()" )
public class BasicSet {

    @Description
    public static final transient String description = "Basic Set";

    public BasicSet() {
        
    }
    
    public String getDescription() {
        return description;
    }

}
