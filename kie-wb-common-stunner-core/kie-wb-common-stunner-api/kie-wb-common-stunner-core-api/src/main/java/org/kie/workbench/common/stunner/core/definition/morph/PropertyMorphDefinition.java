package org.kie.workbench.common.stunner.core.definition.morph;

public interface PropertyMorphDefinition extends MorphDefinition {

    Iterable<MorphProperty> getMorphProperties( String definitionId );

}
