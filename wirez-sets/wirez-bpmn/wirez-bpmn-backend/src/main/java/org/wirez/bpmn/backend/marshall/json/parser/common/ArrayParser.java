package org.wirez.bpmn.backend.marshall.json.parser.common;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonToken;
import org.wirez.bpmn.backend.marshall.json.parser.Parser;

import java.io.IOException;
import java.util.Queue;

public class ArrayParser extends CompositeParser<ArrayParser> {

    public ArrayParser(String name) {
        super(name);
    }

    public ArrayParser(String name, Queue<Parser> _parsers) {
        super(name, _parsers);
    }

    @Override
    protected JsonToken getStartToken() {
        return JsonToken.START_ARRAY;
    }

    @Override
    protected JsonToken getEndToken() {
        return JsonToken.END_ARRAY;
    }
    
}
