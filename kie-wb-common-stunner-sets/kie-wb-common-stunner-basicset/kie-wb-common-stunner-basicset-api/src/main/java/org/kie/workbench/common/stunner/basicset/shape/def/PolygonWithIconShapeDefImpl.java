package org.kie.workbench.common.stunner.basicset.shape.def;

import org.kie.workbench.common.stunner.basicset.definition.PolygonWithIcon;
import org.kie.workbench.common.stunner.core.client.shape.HasChildren;
import org.kie.workbench.common.stunner.core.definition.shape.AbstractShapeDef;
import org.kie.workbench.common.stunner.core.definition.shape.ShapeDef;
import org.kie.workbench.common.stunner.shapes.def.HasChildShapeDefs;
import org.kie.workbench.common.stunner.shapes.def.PolygonShapeDef;
import org.kie.workbench.common.stunner.shapes.def.icon.dynamics.DynamicIconShapeDef;
import org.kie.workbench.common.stunner.shapes.def.icon.dynamics.Icons;

import java.util.LinkedHashMap;
import java.util.Map;

public final class PolygonWithIconShapeDefImpl
        extends AbstractShapeDef<PolygonWithIcon>
        implements
        PolygonShapeDef<PolygonWithIcon>,
        HasChildShapeDefs<PolygonWithIcon> {
    
    @Override
    public double getRadius( final PolygonWithIcon element ) {
        return element.getRadius().getValue();
    }

    @Override
    public String getNamePropertyValue( final PolygonWithIcon element ) {
        return element.getName().getValue();
    }

    @Override
    public String getBackgroundColor( final PolygonWithIcon element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public double getBackgroundAlpha( final PolygonWithIcon element ) {
        return 1;
    }

    @Override
    public String getBorderColor( final PolygonWithIcon element ) {
        return element.getBackgroundSet().getBorderColor().getValue();
    }

    @Override
    public double getBorderSize( final PolygonWithIcon element ) {
        return element.getBackgroundSet().getBorderSize().getValue();
    }

    @Override
    public double getBorderAlpha( final PolygonWithIcon element ) {
        return 1;
    }

    @Override
    public String getFontFamily( final PolygonWithIcon element ) {
        return element.getFontSet().getFontFamily().getValue();
    }

    @Override
    public String getFontColor( final PolygonWithIcon element ) {
        return element.getFontSet().getFontColor().getValue();
    }

    @Override
    public double getFontSize( final PolygonWithIcon element ) {
        return element.getFontSet().getFontSize().getValue();
    }

    @Override
    public double getFontBorderSize( final PolygonWithIcon element ) {
        return element.getFontSet().getFontBorderSize().getValue();
    }

    @Override
    public String getGlyphDescription(final PolygonWithIcon element ) {
        return PolygonWithIcon.title;
    }

    @Override
    public String getGlyphBackgroundColor( final PolygonWithIcon element ) {
        return PolygonWithIcon.PolygonWithIconBuilder.COLOR;
    }

    @Override
    public Map<ShapeDef<PolygonWithIcon>, HasChildren.Layout> getChildShapeDefs() {
        return new LinkedHashMap<ShapeDef<PolygonWithIcon>, HasChildren.Layout>() {{
            put( new IconProxy(), HasChildren.Layout.CENTER );
        }};
    }

    public final class IconProxy
            extends AbstractShapeDef<PolygonWithIcon>
            implements DynamicIconShapeDef<PolygonWithIcon> {

        private static final String COLOR = "#000000";
        
        @Override
        public Icons getIcon( final PolygonWithIcon definition ) {
            return definition.getIconType().getValue();
        }

        @Override
        public double getWidth( final PolygonWithIcon element ) {
            return element.getRadius().getValue() / 2;
        }

        @Override
        public double getHeight( final PolygonWithIcon element ) {
            return element.getRadius().getValue() / 2;
        }

        @Override
        public String getBackgroundColor( final PolygonWithIcon element ) {
            return COLOR;
        }

        @Override
        public double getBackgroundAlpha( final PolygonWithIcon element ) {
            return 1;
        }

        @Override
        public String getBorderColor( final PolygonWithIcon element ) {
            return COLOR;
        }

        @Override
        public double getBorderSize( final PolygonWithIcon element ) {
            return 5;
        }

        @Override
        public double getBorderAlpha( final PolygonWithIcon element ) {
            return 1;
        }

        @Override
        public String getGlyphDescription(final PolygonWithIcon element ) {
            return null;
        }

        @Override
        public String getGlyphBackgroundColor( final PolygonWithIcon element ) {
            return null;
        }
    }
    
}
