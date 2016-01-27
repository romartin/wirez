package org.wirez.bpmn.backend.marshall.json;

import org.uberfire.ext.wirez.bpmn.backend.legacy.Bpmn2JsonUnmarshaller;
import org.uberfire.ext.wirez.bpmn.backend.marshall.json.builder.BPMNGraphObjectBuilderFactory;

public class Bpmn2Marshaller extends Bpmn2JsonUnmarshaller {

    BPMNGraphObjectBuilderFactory wiresFactory;

    public Bpmn2Marshaller(BPMNGraphObjectBuilderFactory wiresFactory) {
        this.wiresFactory = wiresFactory;
    }

}