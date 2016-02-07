/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
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

package org.wirez.core.api.graph.content;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.graph.Bounds;

@Portable
public class ConnectionContentImpl<W extends Definition> extends ViewContentImpl<W> implements ConnectionContent<W> {
    protected Integer sourceMagnetIndex;
    protected Integer targetMagnetIndex;

    public ConnectionContentImpl(@MapsTo("definition") W definition, 
                                 @MapsTo("bounds") Bounds bounds) {
        super(definition, bounds);
        this.sourceMagnetIndex = 0;
        this.targetMagnetIndex = 0;
    }


    @Override
    public Integer getSourceMagnetIndex() {
        return sourceMagnetIndex;
    }

    @Override
    public Integer getTargetMagnetIndex() {
        return targetMagnetIndex;
    }

    @Override
    public void setSourceMagnetIndex(final Integer index) {
        this.sourceMagnetIndex = index;
    }

    @Override
    public void setTargetMagnetIndex(final Integer index) {
        this.targetMagnetIndex = index;
    }
}
