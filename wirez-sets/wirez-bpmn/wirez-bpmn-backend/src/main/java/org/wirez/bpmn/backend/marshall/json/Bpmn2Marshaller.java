package org.wirez.bpmn.backend.marshall.json;


import bpsim.impl.BpsimFactoryImpl;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jboss.drools.impl.DroolsFactoryImpl;
import org.wirez.bpmn.backend.legacy.Bpmn2JsonUnmarshaller;
import org.wirez.bpmn.backend.legacy.resource.JBPMBpmn2ResourceImpl;
import org.wirez.bpmn.backend.marshall.json.builder.BPMNGraphObjectBuilderFactory;
import org.wirez.bpmn.backend.marshall.json.parser.BPMN2JsonParser;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.diagram.Settings;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class Bpmn2Marshaller extends Bpmn2JsonUnmarshaller {

    BPMNGraphObjectBuilderFactory bpmnGraphBuilderFactory;

    public Bpmn2Marshaller(BPMNGraphObjectBuilderFactory bpmnGraphBuilderFactory) {
        this.bpmnGraphBuilderFactory = bpmnGraphBuilderFactory;
    }
    
    public String marshall(Diagram<Settings> diagram) throws IOException {
        DroolsFactoryImpl.init();
        BpsimFactoryImpl.init();
        BPMN2JsonParser parser = createParser(diagram);
        JBPMBpmn2ResourceImpl res = (JBPMBpmn2ResourceImpl) super.unmarshall(parser, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        res.save(outputStream, new HashMap<Object, Object>());
        return StringEscapeUtils.unescapeHtml4(outputStream.toString("UTF-8"));
    }
    
    private BPMN2JsonParser createParser(Diagram<Settings> diagram) {
        
        // TODO
        
        return new BPMN2JsonParser();
    }

}