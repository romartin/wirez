package org.kie.workbench.common.stunner.basicset.shape.proxy;

import org.kie.workbench.common.stunner.basicset.definition.BasicConnector;
import org.kie.workbench.common.stunner.shapes.proxy.AbstractBasicConnectorProxy;
import org.kie.workbench.common.stunner.shapes.proxy.ConnectorProxy;

public final class BasicConnectorProxy
        extends AbstractBasicConnectorProxy<BasicConnector>
        implements ConnectorProxy<BasicConnector> {
    
    @Override
    public String getBackgroundColor( final BasicConnector element ) {
        return element.getBackgroundSet().getBgColor().getValue();
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
    public String getGlyphBackgroundColor( final BasicConnector element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public String getGlyphDescription( final BasicConnector element ) {
        return element.getTitle();
    }

}
