package org.kie.workbench.common.stunner.bpmn.shape.def;

import org.kie.workbench.common.stunner.bpmn.definition.ParallelGateway;
import org.kie.workbench.common.stunner.core.client.shape.HasChildren;
import org.kie.workbench.common.stunner.core.definition.shape.AbstractShapeDef;
import org.kie.workbench.common.stunner.core.definition.shape.ShapeDef;
import org.kie.workbench.common.stunner.shapes.def.HasChildShapeDefs;
import org.kie.workbench.common.stunner.shapes.def.PolygonShapeDef;
import org.kie.workbench.common.stunner.shapes.def.icon.dynamics.DynamicIconShapeDef;
import org.kie.workbench.common.stunner.shapes.def.icon.dynamics.Icons;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ParallelGatewayShapeDef
        extends AbstractShapeDef<ParallelGateway>
        implements
        PolygonShapeDef<ParallelGateway>,
        HasChildShapeDefs<ParallelGateway> {
    
    @Override
    public double getRadius( final ParallelGateway element ) {
        return element.getDimensionsSet().getRadius().getValue();
    }

    @Override
    public String getBackgroundColor( final ParallelGateway element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public double getBackgroundAlpha( final ParallelGateway element ) {
        return 1;
    }

    @Override
    public String getBorderColor( final ParallelGateway element ) {
        return element.getBackgroundSet().getBorderColor().getValue();
    }

    @Override
    public double getBorderSize( final ParallelGateway element ) {
        return element.getBackgroundSet().getBorderSize().getValue();
    }

    @Override
    public double getBorderAlpha( final ParallelGateway element ) {
        return 1;
    }

    @Override
    public String getFontFamily( final ParallelGateway element ) {
        return element.getFontSet().getFontFamily().getValue();
    }

    @Override
    public String getFontColor( final ParallelGateway element ) {
        return element.getFontSet().getFontColor().getValue();
    }

    @Override
    public double getFontSize( final ParallelGateway element ) {
        return element.getFontSet().getFontSize().getValue();
    }

    @Override
    public String getNamePropertyValue( final ParallelGateway element ) {
        return element.getGeneral().getName().getValue();
    }

    @Override
    public double getFontBorderSize( final ParallelGateway element ) {
        return element.getFontSet().getFontBorderSize().getValue();
    }

    @Override
    public String getGlyphBackgroundColor( final ParallelGateway element ) {
        return ParallelGateway.ParallelGatewayBuilder.COLOR;
    }

    @Override
    public String getGlyphDescription(final ParallelGateway element ) {
        return ParallelGateway.description;
    }

    @Override
    public Map<ShapeDef<ParallelGateway>, HasChildren.Layout> getChildProxies() {
        
        return new LinkedHashMap<ShapeDef<ParallelGateway>, HasChildren.Layout>() {{
            
            put( new IconProxy(), HasChildren.Layout.CENTER );
            
        }};
        
    }

    public final class IconProxy
            extends AbstractShapeDef<ParallelGateway>
            implements DynamicIconShapeDef<ParallelGateway> {

        @Override
        public Icons getIcon(final ParallelGateway definition ) {
            return Icons.PLUS;
        }

        @Override
        public double getWidth( final ParallelGateway element ) {
            return element.getDimensionsSet().getRadius().getValue() / 2;
        }

        @Override
        public double getHeight( final ParallelGateway element ) {
            return element.getDimensionsSet().getRadius().getValue() / 2;
        }

        @Override
        public String getBackgroundColor( final ParallelGateway element ) {
            return ParallelGateway.ParallelGatewayBuilder.ICON_COLOR;
        }

        @Override
        public double getBackgroundAlpha( final ParallelGateway element ) {
            return 1;
        }

        @Override
        public String getBorderColor( final ParallelGateway element ) {
            return ParallelGateway.ParallelGatewayBuilder.ICON_COLOR;
        }

        @Override
        public double getBorderSize( final ParallelGateway element ) {
            return 2;
        }

        @Override
        public double getBorderAlpha( final ParallelGateway element ) {
            return 1;
        }

        @Override
        public String getGlyphDescription(final ParallelGateway element ) {
            return null;
        }

        @Override
        public String getGlyphBackgroundColor( final ParallelGateway element ) {
            return null;
        }
    }
    
}
