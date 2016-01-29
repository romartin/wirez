package org.wirez.bpmn.api.factory;

import org.wirez.bpmn.api.BPMNPropertySet;
import org.wirez.bpmn.api.property.diagram.DiagramSet;
import org.wirez.bpmn.api.property.general.*;
import org.wirez.core.api.definition.property.defaults.Name;
import org.wirez.core.api.factory.PropertySetFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.LinkedHashSet;
import java.util.Set;

@ApplicationScoped
public class BPMNPropertySetFactory implements PropertySetFactory<BPMNPropertySet> {

    @Inject
    BPMNPropertyFactory bpmnPropertyFactory;
    
    private static final Set<String> SUPPORTED_PROP_SET_IDS = new LinkedHashSet<String>() {{
        add(BPMNGeneral.ID);
        add(DiagramSet.ID);
        add(BackgroundSet.ID);
        add(FontSet.ID);
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

        throw new RuntimeException("Instance expected to be build here.");
    }

    public BPMNGeneral buildGeneralSet() {
        return new BPMNGeneral(bpmnPropertyFactory.buildName(), 
                bpmnPropertyFactory.buildDocumentation());
    }

    public DiagramSet buildDiagramSet() {
        return new DiagramSet(bpmnPropertyFactory.buildPackage(), 
                bpmnPropertyFactory.buildExecutable());
    }

    public BackgroundSet buildBackgroundSet() {
        return new BackgroundSet(bpmnPropertyFactory.buildBgColor(), 
                bpmnPropertyFactory.buildBorderColor(), 
                bpmnPropertyFactory.buildBorderSize());
    }

    public FontSet buildFontSet() {
        return new FontSet(bpmnPropertyFactory.buildFontFamily(), 
                bpmnPropertyFactory.buildFontColor(),
                bpmnPropertyFactory.buildFontSize(), 
                bpmnPropertyFactory.buildFontBorderSize());
    }
    
}
