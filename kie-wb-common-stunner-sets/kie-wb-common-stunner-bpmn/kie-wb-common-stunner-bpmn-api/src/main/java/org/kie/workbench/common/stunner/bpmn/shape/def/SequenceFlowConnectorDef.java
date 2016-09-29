package org.kie.workbench.common.stunner.bpmn.shape.def;

import org.kie.workbench.common.stunner.bpmn.definition.SequenceFlow;
import org.kie.workbench.common.stunner.shapes.def.AbstractConnectorDef;
import org.kie.workbench.common.stunner.shapes.def.ConnectorShapeDef;

public final class SequenceFlowConnectorDef
        extends AbstractConnectorDef<SequenceFlow>
        implements ConnectorShapeDef<SequenceFlow> {
    
    @Override
    public String getBackgroundColor( final SequenceFlow element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public double getBackgroundAlpha( final SequenceFlow element ) {
        return 1;
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
    public double getBorderAlpha( final SequenceFlow element ) {
        return 1;
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
