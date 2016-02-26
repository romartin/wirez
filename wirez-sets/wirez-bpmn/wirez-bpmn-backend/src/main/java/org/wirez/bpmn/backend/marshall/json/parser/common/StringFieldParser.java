package org.wirez.bpmn.backend.marshall.json.parser.common;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import java.io.IOException;

public class StringFieldParser extends AbstractParser {
    
    private final String name;
    private final String value;

    public StringFieldParser(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    protected JsonToken next() throws IOException, JsonParseException {
        return tokenCount == 0 ? JsonToken.FIELD_NAME : JsonToken.VALUE_STRING;
    }

    @Override
    public String getCurrentName() throws IOException, JsonParseException {
        return name;
    }

    @Override
    public String getText() throws IOException, JsonParseException {
        return value;
    }

    @Override
    public int getIntValue() throws IOException, JsonParseException {
        throw new RuntimeException("Should not be called!");
    }

    @Override
    public boolean isConsumed() {
        return tokenCount == 2;
    }

}
