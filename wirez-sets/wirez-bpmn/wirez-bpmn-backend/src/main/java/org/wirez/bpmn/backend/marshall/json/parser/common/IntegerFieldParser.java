package org.wirez.bpmn.backend.marshall.json.parser.common;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonToken;

import java.io.IOException;

public class IntegerFieldParser extends AbstractParser {
    
    private final String name;
    private final int value;

    public IntegerFieldParser(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    protected JsonToken next() throws IOException, JsonParseException {
        return tokenCount == 0 ? JsonToken.FIELD_NAME : JsonToken.VALUE_NUMBER_INT;
    }

    @Override
    public String getCurrentName() throws IOException, JsonParseException {
        return name;
    }

    @Override
    public String getText() throws IOException, JsonParseException {
        throw new RuntimeException("Should not be called!");
    }

    @Override
    public int getIntValue() throws IOException, JsonParseException {
        return value;
    }

    @Override
    public boolean isConsumed() {
        return tokenCount == 2;
    }

}
