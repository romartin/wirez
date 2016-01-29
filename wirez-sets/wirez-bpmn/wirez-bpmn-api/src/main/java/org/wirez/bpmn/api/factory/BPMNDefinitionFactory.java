package org.wirez.bpmn.api.factory;

import org.wirez.bpmn.api.*;
import org.wirez.bpmn.api.property.Height;
import org.wirez.bpmn.api.property.Radius;
import org.wirez.bpmn.api.property.Width;
import org.wirez.bpmn.api.property.diagram.DiagramSet;
import org.wirez.bpmn.api.property.general.BPMNGeneral;
import org.wirez.bpmn.api.property.general.BackgroundSet;
import org.wirez.bpmn.api.property.general.FontSet;
import org.wirez.core.api.factory.DefinitionFactory;
import org.wirez.core.api.factory.DefinitionSetFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.LinkedHashSet;
import java.util.Set;

@ApplicationScoped
public class BPMNDefinitionFactory implements DefinitionFactory<BPMNDefinition> {

    @Inject
    BPMNPropertyFactory bpmnPropertyFactory;

    @Inject
    BPMNPropertySetFactory bpmnPropertySetFactory;
    
    private static final Set<String>  SUPPORTED_DEF_IDS = new LinkedHashSet<String>() {{
        add(BPMNDiagram.ID);
        add(Task.ID);
        add(StartNoneEvent.ID);
        add(EndNoneEvent.ID);
        add(SequenceFlow.ID);
        add(ParallelGateway.ID);
    }};
    @Override
    public boolean accepts(final String id) {
        return SUPPORTED_DEF_IDS.contains(id);
    }

    @Override
    public BPMNDefinition build(final String id) {
        
        if (BPMNDiagram.ID.equals(id)) {
            return buildBPMNDiagram();
        }
        if (Task.ID.equals(id)) {
            return buildTask();
        }
        if (StartNoneEvent.ID.equals(id)) {
            return buildStartNoneEvent();
        }
        if (EndNoneEvent.ID.equals(id)) {
            return buildEndNoneEvent();
        }
        if (SequenceFlow.ID.equals(id)) {
            return buildSequenceFlow();
        }
        if (ParallelGateway.ID.equals(id)) {
            return buildParallelGateway();
        }
        return null;
    }
    
    public Task buildTask() {
        return new Task(bpmnPropertySetFactory.buildGeneralSet(),
                    bpmnPropertySetFactory.buildBackgroundSet(),
                    bpmnPropertySetFactory.buildFontSet(),
                    bpmnPropertyFactory.buildWidth(),
                    bpmnPropertyFactory.buildHeight())
                .buildDefaults();
    }

    public StartNoneEvent buildStartNoneEvent() {
        return new StartNoneEvent(bpmnPropertySetFactory.buildGeneralSet(),
                    bpmnPropertySetFactory.buildBackgroundSet(),
                    bpmnPropertySetFactory.buildFontSet(),
                    bpmnPropertyFactory.buildRadius())
                .buildDefaults();
    }
    

    public EndNoneEvent buildEndNoneEvent() {
        return new EndNoneEvent(bpmnPropertySetFactory.buildGeneralSet(),
                    bpmnPropertySetFactory.buildBackgroundSet(),
                    bpmnPropertySetFactory.buildFontSet(),
                    bpmnPropertyFactory.buildRadius())
                .buildDefaults();
    }
    public SequenceFlow buildSequenceFlow() {
        return new SequenceFlow(bpmnPropertySetFactory.buildGeneralSet(),
                    bpmnPropertySetFactory.buildBackgroundSet(), 
                    bpmnPropertySetFactory.buildFontSet())
                .buildDefaults();
    }

    public ParallelGateway buildParallelGateway() {
        return new ParallelGateway(bpmnPropertySetFactory.buildGeneralSet(),
                    bpmnPropertySetFactory.buildBackgroundSet(),
                    bpmnPropertySetFactory.buildFontSet(),
                    bpmnPropertyFactory.buildRadius())
                .buildDefaults();
    }

    public BPMNDiagram buildBPMNDiagram() {
        return new BPMNDiagram(bpmnPropertySetFactory.buildGeneralSet(),
                    bpmnPropertySetFactory.buildDiagramSet())
                .buildDefaults();
    }
}
