package org.wirez.basicset.api;

import org.jboss.errai.databinding.client.api.Bindable;
import org.wirez.basicset.api.basic.Circle;
import org.wirez.basicset.api.basic.Polygon;
import org.wirez.basicset.api.basic.Rectangle;
import org.wirez.basicset.api.basic.icon.PolygonWithIcon;
import org.wirez.basicset.api.icon.MinusIcon;
import org.wirez.basicset.api.icon.PlusIcon;
import org.wirez.basicset.api.icon.XORIcon;
import org.wirez.basicset.client.BasicSetThumbnailProvider;
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
                
                // Basic shapes.
                Rectangle.class,
                Circle.class,
                Polygon.class,
                PolygonWithIcon.class,
                
                // Icons.
                MinusIcon.class,
                PlusIcon.class,
                XORIcon.class
        }
)
@CanContain( roles = { "all" } )
@ShapeSet( thumb = BasicSetThumbnailProvider.class )
public class BasicSet {

    @Description
    public static final transient String description = "Basic Set";

    public BasicSet() {
        
    }
    
    public String getDescription() {
        return description;
    }

}
