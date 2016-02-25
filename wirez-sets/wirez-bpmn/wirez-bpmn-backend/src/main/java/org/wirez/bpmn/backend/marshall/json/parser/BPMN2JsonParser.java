package org.wirez.bpmn.backend.marshall.json.parser;

import org.codehaus.jackson.*;
import org.codehaus.jackson.impl.JsonParserBase;
import org.codehaus.jackson.io.IOContext;
import org.codehaus.jackson.util.BufferRecycler;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.diagram.Settings;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

// See org.codehaus.jackson.impl.ReaderBasedParser

public class BPMN2JsonParser extends JsonParserBase {

    private Diagram<Settings> diagram;
    
    public BPMN2JsonParser(Diagram<Settings> diagram) {
        super(new IOContext(new BufferRecycler(), new StringReader(""), true), JsonParser.Feature.collectDefaults());
        this.diagram = diagram;
    }
    
    protected BPMN2JsonParser(IOContext ctxt, int features) {
        super(ctxt, features);
    }
    
    public static JsonParser testCreateParserForJsonContent() {
        try {
            return new JsonFactory().createJsonParser("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private IOContext testCreateContext() {
        Reader reader = new StringReader("");
        return new IOContext(new BufferRecycler(), reader, true);
    }
    
    @Override
    protected boolean loadMore() throws IOException {
        doLog("Call loadMore");
        return false;
    }

    @Override
    protected void _finishString() throws IOException, JsonParseException {
        doLog("Call _finishString");

    }

    @Override
    protected void _closeInput() throws IOException {
        doLog("Call _closeInput");

    }

    @Override
    public ObjectCodec getCodec() {
        doLog("Call getCodec");
        return null;
    }

    @Override
    public void setCodec(ObjectCodec c) {
        doLog("Call setCodec");

    }

    @Override
    public JsonToken nextToken() throws IOException, JsonParseException {
        doLog("Call nextToken");
        return null;
    }

    @Override
    public String getText() throws IOException, JsonParseException {
        doLog("Call getText");
        return null;
    }

    @Override
    public char[] getTextCharacters() throws IOException, JsonParseException {
        doLog("Call getTextCharacters");
        return new char[0];
    }

    @Override
    public int getTextLength() throws IOException, JsonParseException {
        doLog("Call getTextLength");
        return 0;
    }

    @Override
    public int getTextOffset() throws IOException, JsonParseException {
        doLog("Call getTextOffset");
        return 0;
    }

    @Override
    public byte[] getBinaryValue(Base64Variant b64variant) throws IOException, JsonParseException {
        doLog("Call getBinaryValue");
        return new byte[0];
    }
    
    private void doLog(String message) {
        System.out.println("BPMN2JsonParser - " + message);
    }
}
