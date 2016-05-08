package org.wirez.bpmn.api.factory;

import org.wirez.bpmn.api.BPMNPropertySet;
import org.wirez.bpmn.api.property.diagram.DiagramSet;
import org.wirez.bpmn.api.property.general.BPMNGeneral;
import org.wirez.bpmn.api.property.background.BackgroundSet;
import org.wirez.bpmn.api.property.font.FontSet;
import org.wirez.bpmn.api.property.simulation.CatchEventAttributes;
import org.wirez.bpmn.api.property.simulation.ThrowEventAttributes;
import org.wirez.core.api.definition.factory.BindableModelFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.LinkedHashSet;
import java.util.Set;

@ApplicationScoped
public class BPMNPropertySetFactory extends BindableModelFactory<BPMNPropertySet> {

    BPMNPropertyFactory bpmnPropertyBuilder;

    protected BPMNPropertySetFactory() {
    }

    @Inject
    public BPMNPropertySetFactory(BPMNPropertyFactory bpmnPropertyBuilder) {
        this.bpmnPropertyBuilder = bpmnPropertyBuilder;
    }

    private static final Set<Class<?>> SUPPORTED_PROP_CLASSES = new LinkedHashSet<Class<?>>() {{
        add(BPMNGeneral.class);
        add(DiagramSet.class);
        add(BackgroundSet.class);
        add(FontSet.class);
        add(CatchEventAttributes.class);
        add(ThrowEventAttributes.class);
    }};

    @Override
    public Set<Class<?>> getAcceptedClasses() {
        return SUPPORTED_PROP_CLASSES;
    }

    @Override
    public BPMNPropertySet build(final Class<?> clazz) {
        if (BPMNGeneral.class.equals(clazz)) {
            return buildGeneralSet();
        }
        if (DiagramSet.class.equals(clazz)) {
            return buildDiagramSet();
        }
        if (BackgroundSet.class.equals(clazz)) {
            return buildBackgroundSet();
        }
        if (FontSet.class.equals(clazz)) {
            return buildFontSet();
        }
        if (CatchEventAttributes.class.equals(clazz)) {
            return buildCatchEventAttributes();
        }
        if (ThrowEventAttributes.class.equals(clazz)) {
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
