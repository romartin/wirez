package org.wirez.core.definition.morph;

public interface MorphDefinition {

    boolean accepts( String definitionId );

    String getDefault();

    Iterable<String> getTargets( String definitionId );

    MorphPolicy getPolicy();

}
