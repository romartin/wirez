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

package org.wirez.basicset.client;

import com.google.gwt.safehtml.shared.SafeUri;
import org.wirez.basicset.api.BasicSet;
import org.wirez.basicset.client.resources.BasicSetImageResources;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.client.ShapeSet;

public class BasicShapeSet implements ShapeSet {

    public static final String ID = "basic";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getName() {
        return "Basic Shapes";
    }

    @Override
    public String getDescription() {
        return "The basic shapes set";
    }

    @Override
    public SafeUri getThumbnailUri() {
        return BasicSetImageResources.INSTANCE.basicSetThumb().getSafeUri();
    }

    @Override
    public DefinitionSet getDefinitionSet() {
        return BasicSet.INSTANCE;
    }
}
