package org.wirez.bpmn.api.factory;

import org.wirez.bpmn.api.*;
import org.wirez.core.api.definition.factory.BindableModelFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.LinkedHashSet;
import java.util.Set;

@ApplicationScoped
public class BPMNDefinitionFactory extends BindableModelFactory<BPMNDefinition> {

    BPMNPropertyFactory bpmnPropertyBuilder;
    BPMNPropertySetFactory bpmnPropertySetBuilder;

    protected BPMNDefinitionFactory() {
    }

    @Inject
    public BPMNDefinitionFactory(BPMNPropertyFactory bpmnPropertyBuilder, BPMNPropertySetFactory bpmnPropertySetBuilder) {
        this.bpmnPropertyBuilder = bpmnPropertyBuilder;
        this.bpmnPropertySetBuilder = bpmnPropertySetBuilder;
    }

    private static final Set<Class<?>>  SUPPORTED_DEF_CLASSES = new LinkedHashSet<Class<?>>() {{
        add(BPMNDiagram.class);
        add(Task.class);
        add(StartNoneEvent.class);
        add(EndNoneEvent.class);
        add(EndTerminateEvent.class);
        add(SequenceFlow.class);
        add(ParallelGateway.class);
        add(Lane.class);
    }};

    @Override
    public Set<Class<?>> getAcceptedClasses() {
        return SUPPORTED_DEF_CLASSES;
    }

    @Override
    public BPMNDefinition build(final Class<?> clazz) {

        if (BPMNDiagram.class.equals(clazz)) {
            return buildBPMNDiagram();
        }
        if (Task.class.equals(clazz)) {
            return buildTask();
        }
        if (StartNoneEvent.class.equals(clazz)) {
            return buildStartNoneEvent();
        }
        if (EndNoneEvent.class.equals(clazz)) {
            return buildEndNoneEvent();
        }
        if (EndTerminateEvent.class.equals(clazz)) {
            return buildEndTerminateEvent();
        }
        if (SequenceFlow.class.equals(clazz)) {
            return buildSequenceFlow();
        }
        if (ParallelGateway.class.equals(clazz)) {
            return buildParallelGateway();
        }
        if (Lane.class.equals(clazz)) {
            return buildLane();
        }

        return null;
    }

    public Lane buildLane() {
        Lane lane = new Lane(bpmnPropertySetBuilder.buildGeneralSet(),
                bpmnPropertySetBuilder.buildBackgroundSet(),
                bpmnPropertySetBuilder.buildFontSet(),
                bpmnPropertyBuilder.buildWidth(),
                bpmnPropertyBuilder.buildHeight());
        lane.getGeneral().getName().setValue("My lane");
        lane.getBackgroundSet().getBgColor().setValue(Lane.COLOR);
        lane.getBackgroundSet().getBorderSize().setValue(Lane.BORDER_SIZE);
        lane.getWidth().setValue(Lane.WIDTH);
        lane.getHeight().setValue(Lane.HEIGHT);
        return lane;
    }

    public Task buildTask() {
        Task task = new Task(bpmnPropertySetBuilder.buildGeneralSet(),
                    bpmnPropertySetBuilder.buildBackgroundSet(),
                    bpmnPropertySetBuilder.buildFontSet(),
                    bpmnPropertyBuilder.buildWidth(),
                    bpmnPropertyBuilder.buildHeight(),
                    bpmnPropertyBuilder.buildMin(),
                    bpmnPropertyBuilder.buildMax(),
                    bpmnPropertyBuilder.buildMean(),
                    bpmnPropertyBuilder.buildTimeUnit(),
                    bpmnPropertyBuilder.buildStandardDeviation(),
                    bpmnPropertyBuilder.buildDistributionType(),
                    bpmnPropertyBuilder.buildQuantity(),
                    bpmnPropertyBuilder.buildWorkingHours(),
                    bpmnPropertyBuilder.buildUnitCost(),
                    bpmnPropertyBuilder.buildCurrency());
        task.getGeneral().getName().setValue("My task");
        task.getBackgroundSet().getBgColor().setValue(Task.COLOR);
        task.getBackgroundSet().getBorderSize().setValue(Task.BORDER_SIZE);
        task.getWidth().setValue(Task.WIDTH);
        task.getHeight().setValue(Task.HEIGHT);
        return task;
    }

    public StartNoneEvent buildStartNoneEvent() {
        StartNoneEvent event = new StartNoneEvent(bpmnPropertySetBuilder.buildGeneralSet(),
                    bpmnPropertySetBuilder.buildBackgroundSet(),
                    bpmnPropertySetBuilder.buildFontSet(),
                    bpmnPropertySetBuilder.buildCatchEventAttributes(),
                    bpmnPropertyBuilder.buildRadius());
        event.getGeneral().getName().setValue("My start event");
        event.getBackgroundSet().getBgColor().setValue(StartNoneEvent.COLOR);
        event.getRadius().setValue(StartNoneEvent.RADIUS);
        return event;
    }
    

    public EndNoneEvent buildEndNoneEvent() {
        EndNoneEvent event = new EndNoneEvent(bpmnPropertySetBuilder.buildGeneralSet(),
                    bpmnPropertySetBuilder.buildBackgroundSet(),
                    bpmnPropertySetBuilder.buildFontSet(),
                    bpmnPropertySetBuilder.buildThrowEventAttributes(),
                    bpmnPropertyBuilder.buildRadius());
        event.getGeneral().getName().setValue("My end event");
        event.getBackgroundSet().getBgColor().setValue(EndNoneEvent.COLOR);
        event.getRadius().setValue(EndNoneEvent.RADIUS);
        return event;
    }

    public EndTerminateEvent buildEndTerminateEvent() {
        EndTerminateEvent endTerminateEvent =  new EndTerminateEvent(bpmnPropertySetBuilder.buildGeneralSet(),
                bpmnPropertySetBuilder.buildBackgroundSet(),
                bpmnPropertySetBuilder.buildFontSet(),
                bpmnPropertySetBuilder.buildThrowEventAttributes(),
                bpmnPropertyBuilder.buildRadius());
        endTerminateEvent.getGeneral().getName().setValue("My terminate event");
        endTerminateEvent.getBackgroundSet().getBgColor().setValue(EndTerminateEvent.COLOR);
        endTerminateEvent.getBackgroundSet().getBorderColor().setValue(EndTerminateEvent.BORDER_COLOR);
        endTerminateEvent.getRadius().setValue(EndTerminateEvent.RADIUS);
        return endTerminateEvent;
    }
    
    public SequenceFlow buildSequenceFlow() {
        SequenceFlow flow = new SequenceFlow(bpmnPropertySetBuilder.buildGeneralSet(),
                    bpmnPropertySetBuilder.buildBackgroundSet());
        flow.getGeneral().getName().setValue("My sequence flow");
        flow.getBackgroundSet().getBgColor().setValue(SequenceFlow.COLOR);
        flow.getBackgroundSet().getBorderSize().setValue(SequenceFlow.BORDER_SIZE);
        flow.getBackgroundSet().getBorderColor().setValue(SequenceFlow.BORDER_COLOR);
        return flow;
    }

    public ParallelGateway buildParallelGateway() {
        ParallelGateway gateway = new ParallelGateway(bpmnPropertySetBuilder.buildGeneralSet(),
                    bpmnPropertySetBuilder.buildBackgroundSet(),
                    bpmnPropertySetBuilder.buildFontSet(),
                    bpmnPropertyBuilder.buildRadius());
        gateway.getGeneral().getName().setValue("My gateway");
        gateway.getBackgroundSet().getBgColor().setValue(ParallelGateway.COLOR);
        gateway.getRadius().setValue(ParallelGateway.RADIUS);
        return gateway;
    }

    public BPMNDiagram buildBPMNDiagram() {
        BPMNDiagram bpmnDiagram = new BPMNDiagram(bpmnPropertySetBuilder.buildGeneralSet(),
                    bpmnPropertySetBuilder.buildDiagramSet(),
                bpmnPropertySetBuilder.buildBackgroundSet(),
                bpmnPropertySetBuilder.buildFontSet(),
                bpmnPropertyBuilder.buildWidth(),
                bpmnPropertyBuilder.buildHeight());
        bpmnDiagram.getGeneral().getName().setValue("My BPMN diagram");
        bpmnDiagram.getBackgroundSet().getBgColor().setValue(BPMNDiagram.COLOR);
        bpmnDiagram.getBackgroundSet().getBorderSize().setValue(BPMNDiagram.BORDER_SIZE);
        bpmnDiagram.getBackgroundSet().getBorderColor().setValue(BPMNDiagram.BORDER_COLOR);
        bpmnDiagram.getWidth().setValue(BPMNDiagram.WIDTH);
        bpmnDiagram.getHeight().setValue(BPMNDiagram.HEIGHT);
        return bpmnDiagram;
    }
}
