package org.wirez.core.definition.morph;

import java.util.Collection;

public interface PropertyMorphDefinition extends MorphDefinition {

    Iterable<MorphProperty> getMorphProperties( String definitionId );

}
