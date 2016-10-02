package org.kie.workbench.common.stunner.basicset.shape.def;

import org.kie.workbench.common.stunner.basicset.definition.BasicConnector;
import org.kie.workbench.common.stunner.shapes.def.ConnectorShapeDef;

public final class BasicConnectorDefImpl
        implements ConnectorShapeDef<BasicConnector> {
    
    @Override
    public String getBackgroundColor( final BasicConnector element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public double getBackgroundAlpha( final BasicConnector element ) {
        return 1;
    }

    @Override
    public String getBorderColor( final BasicConnector element ) {
        return element.getBackgroundSet().getBorderColor().getValue();
    }

    @Override
    public double getBorderSize( final BasicConnector element ) {
        return element.getBackgroundSet().getBorderSize().getValue();
    }

    @Override
    public double getBorderAlpha( final BasicConnector element ) {
        return 1;
    }

    @Override
    public String getNamePropertyValue( final BasicConnector element ) {
        return element.getName().getValue();
    }

    @Override
    public String getFontFamily( final BasicConnector element ) {
        return null;
    }

    @Override
    public String getFontColor( final BasicConnector element ) {
        return null;
    }

    @Override
    public double getFontSize( final BasicConnector element ) {
        return 0;
    }

    @Override
    public double getFontBorderSize( final BasicConnector element ) {
        return 0;
    }


    @Override
    public String getGlyphBackgroundColor( final BasicConnector element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public String getGlyphDefinitionId( final String definitionId ) {
        return null;
    }

    @Override
    public String getGlyphDescription( final BasicConnector element ) {
        return element.getTitle();
    }

}
