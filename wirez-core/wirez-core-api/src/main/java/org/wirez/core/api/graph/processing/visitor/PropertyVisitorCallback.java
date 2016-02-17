package org.wirez.core.api.graph.processing.visitor;

import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.content.ViewContent;

public interface PropertyVisitorCallback {

    void visitProperty(Element<? extends ViewContent> element, String key, Object value);
    
}
