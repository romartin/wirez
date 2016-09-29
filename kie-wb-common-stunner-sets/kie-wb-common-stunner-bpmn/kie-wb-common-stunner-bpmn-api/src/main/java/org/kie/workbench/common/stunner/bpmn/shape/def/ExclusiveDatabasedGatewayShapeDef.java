package org.kie.workbench.common.stunner.bpmn.shape.def;

import org.kie.workbench.common.stunner.bpmn.definition.ExclusiveDatabasedGateway;
import org.kie.workbench.common.stunner.core.client.shape.HasChildren;
import org.kie.workbench.common.stunner.core.definition.shape.AbstractShapeDef;
import org.kie.workbench.common.stunner.core.definition.shape.ShapeDef;
import org.kie.workbench.common.stunner.shapes.def.HasChildShapeDefs;
import org.kie.workbench.common.stunner.shapes.def.PolygonShapeDef;
import org.kie.workbench.common.stunner.shapes.def.icon.dynamics.DynamicIconShapeDef;
import org.kie.workbench.common.stunner.shapes.def.icon.dynamics.Icons;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ExclusiveDatabasedGatewayShapeDef
        extends AbstractShapeDef<ExclusiveDatabasedGateway>
        implements
        PolygonShapeDef<ExclusiveDatabasedGateway>,
        HasChildShapeDefs<ExclusiveDatabasedGateway> {
    
    @Override
    public double getRadius( final ExclusiveDatabasedGateway element ) {
        return element.getDimensionsSet().getRadius().getValue();
    }

    @Override
    public String getBackgroundColor( final ExclusiveDatabasedGateway element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public double getBackgroundAlpha( final ExclusiveDatabasedGateway element ) {
        return 1;
    }

    @Override
    public String getBorderColor( final ExclusiveDatabasedGateway element ) {
        return element.getBackgroundSet().getBorderColor().getValue();
    }

    @Override
    public double getBorderSize( final ExclusiveDatabasedGateway element ) {
        return element.getBackgroundSet().getBorderSize().getValue();
    }

    @Override
    public double getBorderAlpha( final ExclusiveDatabasedGateway element ) {
        return 1;
    }

    @Override
    public String getFontFamily( final ExclusiveDatabasedGateway element ) {
        return element.getFontSet().getFontFamily().getValue();
    }

    @Override
    public String getFontColor( final ExclusiveDatabasedGateway element ) {
        return element.getFontSet().getFontColor().getValue();
    }

    @Override
    public double getFontSize( final ExclusiveDatabasedGateway element ) {
        return element.getFontSet().getFontSize().getValue();
    }

    @Override
    public String getNamePropertyValue( final ExclusiveDatabasedGateway element ) {
        return element.getGeneral().getName().getValue();
    }

    @Override
    public double getFontBorderSize( final ExclusiveDatabasedGateway element ) {
        return element.getFontSet().getFontBorderSize().getValue();
    }

    @Override
    public String getGlyphBackgroundColor( final ExclusiveDatabasedGateway element ) {
        return ExclusiveDatabasedGateway.ExclusiveDatabasedGatewayBuilder.COLOR;
    }

    @Override
    public String getGlyphDescription(final ExclusiveDatabasedGateway element ) {
        return ExclusiveDatabasedGateway.description;
    }

    @Override
    public Map<ShapeDef<ExclusiveDatabasedGateway>, HasChildren.Layout> getChildShapeDefs() {
        
        return new LinkedHashMap<ShapeDef<ExclusiveDatabasedGateway>, HasChildren.Layout>() {{
            
            put( new IconProxy(), HasChildren.Layout.CENTER );
            
        }};
        
    }

    public final class IconProxy
            extends AbstractShapeDef<ExclusiveDatabasedGateway>
            implements DynamicIconShapeDef<ExclusiveDatabasedGateway> {

        @Override
        public Icons getIcon(final ExclusiveDatabasedGateway definition ) {
            return Icons.XOR;
        }

        @Override
        public double getWidth( final ExclusiveDatabasedGateway element ) {
            return element.getDimensionsSet().getRadius().getValue() / 2;
        }

        @Override
        public double getHeight( final ExclusiveDatabasedGateway element ) {
            return element.getDimensionsSet().getRadius().getValue() / 2;
        }

        @Override
        public String getBackgroundColor( final ExclusiveDatabasedGateway element ) {
            return ExclusiveDatabasedGateway.ExclusiveDatabasedGatewayBuilder.ICON_COLOR;
        }

        @Override
        public double getBackgroundAlpha( final ExclusiveDatabasedGateway element ) {
            return 1;
        }

        @Override
        public String getBorderColor( final ExclusiveDatabasedGateway element ) {
            return ExclusiveDatabasedGateway.ExclusiveDatabasedGatewayBuilder.ICON_COLOR;
        }

        @Override
        public double getBorderSize( final ExclusiveDatabasedGateway element ) {
            return 2;
        }

        @Override
        public double getBorderAlpha( final ExclusiveDatabasedGateway element ) {
            return 1;
        }

        @Override
        public String getGlyphDescription(final ExclusiveDatabasedGateway element ) {
            return null;
        }

        @Override
        public String getGlyphBackgroundColor( final ExclusiveDatabasedGateway element ) {
            return null;
        }
    }
    
}
