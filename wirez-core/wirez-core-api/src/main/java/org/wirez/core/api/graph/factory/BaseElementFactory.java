package org.wirez.core.api.graph.factory;

import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.content.Definition;

public abstract class BaseElementFactory<W, C extends Definition<W>, T extends Element<C>>  implements ElementFactory<W, C, T> {

}
