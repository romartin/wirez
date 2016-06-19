package org.wirez.core.definition.morph;

// TODO: Refactor to #getTargets()
//      - Bindable impl -> do not use a map, as single class a key is available
public interface MorphDefinition {

    boolean accepts( String definitionId );

    String getBase();

    String getDefault();

    Iterable<String> getTargets( String definitionId );

    MorphPolicy getPolicy();

}
