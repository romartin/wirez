package org.wirez.bpmn.backend.marshall.json;


import bpsim.impl.BpsimFactoryImpl;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jboss.drools.impl.DroolsFactoryImpl;
import org.wirez.bpmn.backend.legacy.Bpmn2JsonUnmarshaller;
import org.wirez.bpmn.backend.legacy.resource.JBPMBpmn2ResourceImpl;
import org.wirez.bpmn.backend.marshall.json.oryx.Bpmn2OryxIdMappings;
import org.wirez.bpmn.backend.marshall.json.oryx.Bpmn2OryxPropertyManager;
import org.wirez.bpmn.backend.marshall.json.parser.BPMN2JsonParser;
import org.wirez.bpmn.backend.marshall.json.parser.ParsingContext;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.diagram.Settings;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.util.GraphUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class Bpmn2Marshaller extends Bpmn2JsonUnmarshaller {

    DefinitionManager definitionManager;
    GraphUtils graphUtils;
    Bpmn2OryxIdMappings oryxIdMappings;
    Bpmn2OryxPropertyManager oryxPropertyManager;

    public Bpmn2Marshaller(DefinitionManager definitionManager,
                           GraphUtils graphUtils,
                           Bpmn2OryxIdMappings oryxIdMappings,
                           Bpmn2OryxPropertyManager oryxPropertyManager) {
        this.definitionManager = definitionManager;
        this.graphUtils = graphUtils;
        this.oryxIdMappings = oryxIdMappings;
        this.oryxPropertyManager = oryxPropertyManager;
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
        return new BPMN2JsonParser( diagram, new ParsingContext( definitionManager, graphUtils, oryxIdMappings, oryxPropertyManager) );
    }
}