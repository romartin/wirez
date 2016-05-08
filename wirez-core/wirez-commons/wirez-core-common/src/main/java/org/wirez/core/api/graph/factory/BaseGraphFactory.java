package org.wirez.core.api.graph.factory;

import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.content.definition.DefinitionSet;

public abstract class BaseGraphFactory<C extends DefinitionSet, T extends Element<C>>  implements GraphFactory<C, T> {

}
