package org.wirez.bpmn.backend.marshall.json.parser.common;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.wirez.bpmn.backend.marshall.json.parser.Parser;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

public abstract class CompositeParser<T> extends AbstractParser {
    
    protected final Queue<Parser> parsers = new ArrayDeque<>();
    protected final String name;
    protected Parser current = null;
    
    public CompositeParser(String name) {
        this.name = name;
    }

    public CompositeParser(String name, Queue<Parser> _parsers) {
        this.name = name;
        this.parsers.addAll(_parsers);
    }
    
    protected abstract JsonToken getStartToken();

    protected abstract JsonToken getEndToken();
    
    public T addParser(Parser _parser) {
        this.parsers.add(_parser);
        return (T) this;
    }

    @Override
    protected JsonToken next() throws IOException, JsonParseException {
        
        boolean existParsers = !this.parsers.isEmpty();
        
        if (  existParsers && this.tokenCount == 0 ) {

            this.current = this.parsers.remove();
            return getStartToken();
            
        } else {

            if ( null != current && !this.current.isConsumed() ) {

                return this.current.nextToken();
            
            } else if ( null != current && this.current.isConsumed() && !this.parsers.isEmpty() ) {

                this.current = this.parsers.remove();
                return this.current.nextToken();
                
            } else {
                
                // Finished.
                this.current = null;
                return getEndToken();
                
            }
        }
        
        // throw new RuntimeException("Invalid token count for this object parser.");
    }

    @Override
    public String getCurrentName() throws IOException, JsonParseException {
        
        if ( this.tokenCount == 0 ) {
            return name;
        } else  {
            return this.current.getCurrentName();
        }

    }

    @Override
    public String getText() throws IOException, JsonParseException {
        return this.current.getText();
    }

    @Override
    public int getIntValue() throws IOException, JsonParseException {
        return this.current.getIntValue();
    }

    @Override
    public JsonParser skipChildren() throws IOException, JsonParseException {
        return this.current.skipChildren();
    }

    @Override
    public boolean isConsumed() {
        return this.tokenCount > 0 && this.current == null;
    }
}
