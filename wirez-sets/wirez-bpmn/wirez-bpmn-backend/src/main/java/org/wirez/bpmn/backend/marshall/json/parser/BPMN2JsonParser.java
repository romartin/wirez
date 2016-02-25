package org.wirez.bpmn.backend.marshall.json.parser;

import org.codehaus.jackson.*;
import org.codehaus.jackson.impl.JsonParserBase;
import org.codehaus.jackson.io.IOContext;

import java.io.IOException;

// Ex; ReaderBasedParser
public class BPMN2JsonParser extends JsonParserBase {

    public BPMN2JsonParser() {
        super(null, 0);
    }
    
    protected BPMN2JsonParser(IOContext ctxt, int features) {
        super(ctxt, features);
    }
    
    private void test() {
        try {
            new JsonFactory().createJsonParser("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean loadMore() throws IOException {
        return false;
    }

    @Override
    protected void _finishString() throws IOException, JsonParseException {

    }

    @Override
    protected void _closeInput() throws IOException {

    }

    @Override
    public ObjectCodec getCodec() {
        return null;
    }

    @Override
    public void setCodec(ObjectCodec c) {

    }

    @Override
    public JsonToken nextToken() throws IOException, JsonParseException {
        return null;
    }

    @Override
    public String getText() throws IOException, JsonParseException {
        return null;
    }

    @Override
    public char[] getTextCharacters() throws IOException, JsonParseException {
        return new char[0];
    }

    @Override
    public int getTextLength() throws IOException, JsonParseException {
        return 0;
    }

    @Override
    public int getTextOffset() throws IOException, JsonParseException {
        return 0;
    }

    @Override
    public byte[] getBinaryValue(Base64Variant b64variant) throws IOException, JsonParseException {
        return new byte[0];
    }
}
