package org.wirez.bpmn.shape.proxy;

import org.wirez.bpmn.definition.SequenceFlow;
import org.wirez.shapes.proxy.AbstractBasicConnectorProxy;
import org.wirez.shapes.proxy.ConnectorProxy;

public final class SequenceFlowConnectorProxy
        extends AbstractBasicConnectorProxy<SequenceFlow>
        implements ConnectorProxy<SequenceFlow> {
    
    @Override
    public String getBackgroundColor( final SequenceFlow element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public String getBorderColor( final SequenceFlow element ) {
        return element.getBackgroundSet().getBorderColor().getValue();
    }

    @Override
    public double getBorderSize( final SequenceFlow element ) {
        return element.getBackgroundSet().getBorderSize().getValue();
    }


    @Override
    public String getGlyphBackgroundColor( final SequenceFlow element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public String getGlyphDescription( final SequenceFlow element ) {
        return element.getTitle();
    }

}
