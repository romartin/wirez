package org.wirez.core.graph.factory;

import org.wirez.core.graph.Element;
import org.wirez.core.graph.content.definition.DefinitionSet;

public abstract class BaseGraphFactory<C extends DefinitionSet, T extends Element<C>>  implements GraphFactory<C, T> {

}
