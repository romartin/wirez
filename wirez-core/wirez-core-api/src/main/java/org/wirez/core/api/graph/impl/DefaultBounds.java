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

package org.wirez.core.api.graph.impl;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.graph.Bounds;

@Portable
public class DefaultBounds implements Bounds {
    
    private DefaultBound lr;
    private DefaultBound ul;

    public DefaultBounds(@MapsTo("lr") DefaultBound lr,
                         @MapsTo("ul") DefaultBound ul) {
        this.lr = lr;
        this.ul = ul;
    }

    @Override
    public DefaultBound getLowerRight() {
        return lr;
    }

    @Override
    public DefaultBound getUpperLeft() {
        return ul;
    }

    public void setLowerRight(DefaultBound lr) {
        this.lr = lr;
    }

    public void setUpperLeft(DefaultBound ul) {
        this.ul = ul;
    }
}
