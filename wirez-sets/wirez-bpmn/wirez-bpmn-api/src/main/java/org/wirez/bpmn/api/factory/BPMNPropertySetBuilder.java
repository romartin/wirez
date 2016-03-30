package org.wirez.bpmn.api.factory;

import org.wirez.bpmn.api.BPMNPropertySet;
import org.wirez.bpmn.api.property.diagram.DiagramSet;
import org.wirez.bpmn.api.property.general.*;
import org.wirez.bpmn.api.property.simulation.CatchEventAttributes;
import org.wirez.bpmn.api.property.simulation.ThrowEventAttributes;
import org.wirez.core.api.factory.PropertySetBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.LinkedHashSet;
import java.util.Set;

@ApplicationScoped
public class BPMNPropertySetBuilder implements PropertySetBuilder<BPMNPropertySet> {

    BPMNPropertyBuilder bpmnPropertyBuilder;

    protected BPMNPropertySetBuilder() {
    }

    @Inject
    public BPMNPropertySetBuilder(BPMNPropertyBuilder bpmnPropertyBuilder) {
        this.bpmnPropertyBuilder = bpmnPropertyBuilder;
    }

    private static final Set<String> SUPPORTED_PROP_SET_IDS = new LinkedHashSet<String>() {{
        add(BPMNGeneral.ID);
        add(DiagramSet.ID);
        add(BackgroundSet.ID);
        add(FontSet.ID);
        add(CatchEventAttributes.ID);
        add(ThrowEventAttributes.ID);
    }};
    
    @Override
    public boolean accepts(String id) {
        return SUPPORTED_PROP_SET_IDS.contains(id);
    }

    @Override
    public BPMNPropertySet build(String id) {
        if (BPMNGeneral.ID.equals(id)) {
            return buildGeneralSet();
        }
        if (DiagramSet.ID.equals(id)) {
            return buildDiagramSet();
        }
        if (BackgroundSet.ID.equals(id)) {
            return buildBackgroundSet();
        }
        if (FontSet.ID.equals(id)) {
            return buildFontSet();
        }
        if (CatchEventAttributes.ID.equals(id)) {
            return buildCatchEventAttributes();
        }
        if (ThrowEventAttributes.ID.equals(id)) {
            return buildThrowEventAttributes();
        }
        
        throw new RuntimeException("Instance expected to be build here.");
    }

    public CatchEventAttributes buildCatchEventAttributes() {
        return new CatchEventAttributes(bpmnPropertyBuilder.buildMin(),
                bpmnPropertyBuilder.buildMax(),
                bpmnPropertyBuilder.buildMean(),
                bpmnPropertyBuilder.buildTimeUnit(),
                bpmnPropertyBuilder.buildStandardDeviation(),
                bpmnPropertyBuilder.buildDistributionType());
    }

    public ThrowEventAttributes buildThrowEventAttributes() {
        return new ThrowEventAttributes(bpmnPropertyBuilder.buildMin(),
                bpmnPropertyBuilder.buildMax(),
                bpmnPropertyBuilder.buildMean(),
                bpmnPropertyBuilder.buildTimeUnit(),
                bpmnPropertyBuilder.buildStandardDeviation(),
                bpmnPropertyBuilder.buildDistributionType());
    }

    public BPMNGeneral buildGeneralSet() {
        return new BPMNGeneral(bpmnPropertyBuilder.buildName(), 
                bpmnPropertyBuilder.buildDocumentation());
    }

    public DiagramSet buildDiagramSet() {
        return new DiagramSet(bpmnPropertyBuilder.buildPackage(), 
                bpmnPropertyBuilder.buildExecutable());
    }

    public BackgroundSet buildBackgroundSet() {
        return new BackgroundSet(bpmnPropertyBuilder.buildBgColor(), 
                bpmnPropertyBuilder.buildBorderColor(), 
                bpmnPropertyBuilder.buildBorderSize());
    }

    public FontSet buildFontSet() {
        return new FontSet(bpmnPropertyBuilder.buildFontFamily(), 
                bpmnPropertyBuilder.buildFontColor(),
                bpmnPropertyBuilder.buildFontSize(), 
                bpmnPropertyBuilder.buildFontBorderSize());
    }
    
}
