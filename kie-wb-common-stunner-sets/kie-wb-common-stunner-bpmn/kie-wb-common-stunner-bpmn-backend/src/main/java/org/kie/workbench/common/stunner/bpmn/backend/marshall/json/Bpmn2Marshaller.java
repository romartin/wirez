package org.kie.workbench.common.stunner.bpmn.backend.marshall.json;


import bpsim.impl.BpsimFactoryImpl;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jboss.drools.impl.DroolsFactoryImpl;
import org.kie.workbench.common.stunner.bpmn.backend.legacy.Bpmn2JsonUnmarshaller;
import org.kie.workbench.common.stunner.bpmn.backend.legacy.resource.JBPMBpmn2ResourceImpl;
import org.kie.workbench.common.stunner.bpmn.backend.marshall.json.parser.BPMN2JsonParser;
import org.kie.workbench.common.stunner.bpmn.backend.marshall.json.parser.ParsingContext;
import org.kie.workbench.common.stunner.bpmn.backend.marshall.json.oryx.Bpmn2OryxManager;
import org.kie.workbench.common.stunner.core.api.DefinitionManager;
import org.kie.workbench.common.stunner.core.diagram.Diagram;
import org.kie.workbench.common.stunner.core.diagram.Settings;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.util.GraphUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class Bpmn2Marshaller extends Bpmn2JsonUnmarshaller {

    DefinitionManager definitionManager;
    GraphUtils graphUtils;
    Bpmn2OryxManager oryxManager;

    public Bpmn2Marshaller(DefinitionManager definitionManager,
                           GraphUtils graphUtils,
                           Bpmn2OryxManager oryxManager) {
        this.definitionManager = definitionManager;
        this.graphUtils = graphUtils;
        this.oryxManager = oryxManager;
    }
    
    public String marshall(Diagram<Graph, Settings> diagram) throws IOException {
        DroolsFactoryImpl.init();
        BpsimFactoryImpl.init();
        BPMN2JsonParser parser = createParser(diagram);
        JBPMBpmn2ResourceImpl res = (JBPMBpmn2ResourceImpl) super.unmarshall(parser, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        res.save(outputStream, new HashMap<Object, Object>());
        return StringEscapeUtils.unescapeHtml4(outputStream.toString("UTF-8"));
    }

    private BPMN2JsonParser createParser(Diagram<Graph, Settings> diagram) {
        return new BPMN2JsonParser( diagram, new ParsingContext( definitionManager, graphUtils, oryxManager) );
    }
}