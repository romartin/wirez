package org.wirez.basicset;

import org.jboss.errai.databinding.client.api.Bindable;
import org.wirez.basicset.definition.Circle;
import org.wirez.basicset.definition.Polygon;
import org.wirez.basicset.definition.PolygonWithIcon;
import org.wirez.basicset.definition.Rectangle;
import org.wirez.core.definition.annotation.Description;
import org.wirez.core.definition.annotation.definitionset.DefinitionSet;
import org.wirez.core.graph.Graph;
import org.wirez.core.rule.annotation.CanContain;
import org.wirez.core.definition.annotation.ShapeSet;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Bindable
@DefinitionSet(
        
        type = Graph.class,
        
        definitions = {
                
                // Basic shapes.
                Rectangle.class,
                Circle.class,
                Polygon.class,
                PolygonWithIcon.class,
                
                // Icons.
                org.wirez.basicset.definition.icon.MinusIcon.class,
                org.wirez.basicset.definition.icon.PlusIcon.class,
                org.wirez.basicset.definition.icon.XORIcon.class
        }
)
@CanContain( roles = { "all" } )
@ShapeSet
public class BasicSet {

    @Description
    public static final transient String description = "Basic Set";

    public BasicSet() {
        
    }
    
    public String getDescription() {
        return description;
    }

}
