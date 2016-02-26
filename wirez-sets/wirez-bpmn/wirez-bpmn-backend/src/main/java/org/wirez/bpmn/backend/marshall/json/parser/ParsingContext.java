package org.wirez.bpmn.backend.marshall.json.parser;

import org.wirez.core.api.DefinitionManager;

public class ParsingContext implements ContextualParser.Context {

    private final DefinitionManager definitionManager;

    public ParsingContext(DefinitionManager definitionManager) {
        this.definitionManager = definitionManager;
    }

    @Override
    public DefinitionManager getDefinitionManager() {
        return definitionManager;
    }
}
