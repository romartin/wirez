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

package org.wirez.core.client.canvas.settings;

import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.processing.index.Index;
import org.wirez.core.api.graph.processing.index.IndexBuilder;
import org.wirez.core.api.graph.processing.visitor.ContentVisitorCallback;
import org.wirez.core.api.graph.processing.visitor.Visitor;
import org.wirez.core.api.graph.processing.visitor.VisitorPolicy;

public abstract class CanvasSettingsImpl<G extends Graph<?, N>, N extends Node, E extends Edge> implements CanvasSettings<G, N, E> {

    private IndexBuilder<G, N, E, ? extends Index<N, E>> indexBuilder;
    private Visitor<G, ? extends ContentVisitorCallback<N, E, G>, ? extends VisitorPolicy> visitor;

    public CanvasSettingsImpl() {
    }

    public CanvasSettingsImpl(final IndexBuilder<G, N, E, ? extends Index<N, E>> indexBuilder,
                            final Visitor<G, ? extends ContentVisitorCallback<N, E, G>, ? extends VisitorPolicy> visitor) {
        this.indexBuilder = indexBuilder;
        this.visitor = visitor;
    }

    @Override
    public IndexBuilder<G, N, E, ? extends Index<N, E>> getIndexBuilder() {
        return indexBuilder;
    }

    @Override
    public Visitor<G, ? extends ContentVisitorCallback<N, E, G>, ? extends VisitorPolicy> getVisitor() {
        return visitor;
    }

    public void setIndexBuilder(final IndexBuilder<G, N, E, ? extends Index<N, E>> indexBuilder) {
        this.indexBuilder = indexBuilder;
    }

    public void setVisitor(final Visitor<G, ? extends ContentVisitorCallback<N, E, G>, ? extends VisitorPolicy> visitor) {
        this.visitor = visitor;
    }
}
