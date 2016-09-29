package org.kie.workbench.common.stunner.bpmn.backend.marshall.json.parser;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonToken;

import java.io.IOException;

public interface Parser {

    JsonToken nextToken() throws IOException, JsonParseException;

    String getCurrentName() throws IOException, JsonParseException;
            
    String getText() throws IOException, JsonParseException;

    int getIntValue() throws IOException, JsonParseException;

    boolean isConsumed();
    
}
