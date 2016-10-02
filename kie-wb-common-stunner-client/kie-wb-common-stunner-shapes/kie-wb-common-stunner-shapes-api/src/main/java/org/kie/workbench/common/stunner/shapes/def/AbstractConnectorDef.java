package org.kie.workbench.common.stunner.shapes.def;

import org.kie.workbench.common.stunner.core.definition.shape.AbstractBindableGlyphDef;

// TODO: Connector text not supported yet.
public abstract class AbstractConnectorDef<W> extends AbstractBindableGlyphDef<W> implements ConnectorShapeDef<W> {

    @Override
    public String getNamePropertyValue( final W element ) {
        return null;
    }

    @Override
    public String getFontFamily( final W element ) {
        return null;
    }

    @Override
    public String getFontColor( final W element ) {
        return null;
    }

    @Override
    public double getFontSize( final W element ) {
        return 0;
    }

    @Override
    public double getFontBorderSize( final W element ) {
        return 0;
    }

}
