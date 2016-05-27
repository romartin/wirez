/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wirez.shapes.client;

import org.wirez.core.client.shape.AbstractCompositeShape;
import org.wirez.core.client.shape.MutationContext;
import org.wirez.core.client.shape.view.HasRadius;
import org.wirez.core.client.shape.view.HasSize;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.graph.util.GraphUtils;

public abstract class AbstractBasicShape<W, V extends org.wirez.shapes.client.view.BasicShapeView>
    extends AbstractCompositeShape<W, Node<View<W>, Edge>, V> {

    public AbstractBasicShape(final V shapeView) {
        super(shapeView);
    }

    protected W getDefinition( final Node<View<W>, Edge> element ) {
        return element.getContent().getDefinition();
    }

}
