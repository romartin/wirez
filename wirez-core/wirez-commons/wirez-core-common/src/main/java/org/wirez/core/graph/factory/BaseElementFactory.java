package org.wirez.core.graph.factory;

import org.wirez.core.graph.Element;
import org.wirez.core.graph.content.definition.Definition;

public abstract class BaseElementFactory<W, C extends Definition<W>, T extends Element<C>>  implements ElementFactory<W, C, T> {

}
