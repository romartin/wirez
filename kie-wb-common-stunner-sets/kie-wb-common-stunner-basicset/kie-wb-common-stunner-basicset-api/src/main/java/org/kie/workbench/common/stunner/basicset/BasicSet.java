package org.kie.workbench.common.stunner.basicset;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.kie.workbench.common.stunner.basicset.definition.*;
import org.kie.workbench.common.stunner.basicset.definition.icon.dynamics.MinusIcon;
import org.kie.workbench.common.stunner.basicset.definition.icon.dynamics.PlusIcon;
import org.kie.workbench.common.stunner.basicset.definition.icon.dynamics.XORIcon;
import org.kie.workbench.common.stunner.basicset.definition.icon.statics.BusinessRuleIcon;
import org.kie.workbench.common.stunner.basicset.definition.icon.statics.ScriptIcon;
import org.kie.workbench.common.stunner.basicset.definition.icon.statics.TimerIcon;
import org.kie.workbench.common.stunner.basicset.definition.icon.statics.UserIcon;
import org.kie.workbench.common.stunner.core.definition.annotation.Description;
import org.kie.workbench.common.stunner.core.definition.annotation.ShapeSet;
import org.kie.workbench.common.stunner.core.definition.annotation.DefinitionSet;
import org.kie.workbench.common.stunner.core.definition.builder.Builder;
import org.kie.workbench.common.stunner.core.factory.graph.GraphFactory;
import org.kie.workbench.common.stunner.core.rule.annotation.CanContain;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Bindable
@DefinitionSet(
        graphFactory= GraphFactory.class,
        definitions = {
                
                // Basic shapes.
                Rectangle.class,
                Circle.class,
                Ring.class,
                Polygon.class,
                PolygonWithIcon.class,

                // Connectors.
                BasicConnector.class,

                // Dynamic Icons.
                MinusIcon.class,
                PlusIcon.class,
                XORIcon.class,

                // Static Icons.
                UserIcon.class,
                ScriptIcon.class,
                BusinessRuleIcon.class,
                TimerIcon.class
                
        },
        builder = BasicSet.BasicSetBuilder.class
)
@CanContain( roles = { "all" } )
@ShapeSet
public class BasicSet {

    @Description
    public static final transient String description = "Basic Set";

    @NonPortable
    public static class BasicSetBuilder implements Builder<BasicSet> {

        @Override
        public BasicSet build() {
            return new BasicSet();
        }

    }

    public BasicSet() {
        
    }
    
    public String getDescription() {
        return description;
    }

}
