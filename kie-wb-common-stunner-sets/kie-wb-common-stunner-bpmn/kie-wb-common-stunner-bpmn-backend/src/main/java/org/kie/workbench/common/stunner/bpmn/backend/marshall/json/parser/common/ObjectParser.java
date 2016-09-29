package org.kie.workbench.common.stunner.bpmn.backend.marshall.json.parser.common;

import org.codehaus.jackson.JsonToken;
import org.kie.workbench.common.stunner.bpmn.backend.marshall.json.parser.Parser;

import java.util.Queue;

public class ObjectParser extends CompositeParser<ObjectParser> {

    public ObjectParser(String name) {
        super(name);
    }

    public ObjectParser(String name, Queue<Parser> _parsers) {
        super(name, _parsers);
    }

    @Override
    protected JsonToken getStartToken() {
        return JsonToken.START_OBJECT;
    }

    @Override
    protected JsonToken getEndToken() {
        return JsonToken.END_OBJECT;
    }
    
}
