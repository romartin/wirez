package org.wirez.bpmn.api;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.rule.Rule;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

@ApplicationScoped
@Portable
public class BPMNDefinitionSet implements DefinitionSet {

    @Inject
    BPMNDiagram diagram;

    @Inject
    StartNoneEvent startNoneEvent;

    @Override
    public String getId() {
        return "bpmnDefSet";
    }

    @Override
    public String getDomain() {
        return "bpmn.org";
    }

    @Override
    public String getDescription() {
        return "THe BPMN definition set";
    }

    @org.wirez.core.api.annotation.definitionset.Definition
    public BPMNDiagram getDiagram() {
        return diagram;
    }

    @org.wirez.core.api.annotation.definitionset.Definition
    public StartNoneEvent getStartNoneEvent() {
        return startNoneEvent;
    }
    
}
