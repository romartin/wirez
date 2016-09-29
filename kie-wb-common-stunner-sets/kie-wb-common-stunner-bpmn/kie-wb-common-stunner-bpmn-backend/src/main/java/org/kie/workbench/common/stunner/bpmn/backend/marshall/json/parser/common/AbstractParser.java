package org.kie.workbench.common.stunner.bpmn.backend.marshall.json.parser.common;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonToken;
import org.kie.workbench.common.stunner.bpmn.backend.marshall.json.parser.Parser;

import java.io.IOException;

public abstract class AbstractParser implements Parser {
    
    protected int tokenCount;

    public AbstractParser() {
        this.tokenCount = 0;
    }
    
    protected abstract JsonToken next() throws IOException, JsonParseException;

    @Override
    public JsonToken nextToken() throws IOException, JsonParseException {
        JsonToken token = next();
        tokenCount++;
        return token;
    }
    
}
