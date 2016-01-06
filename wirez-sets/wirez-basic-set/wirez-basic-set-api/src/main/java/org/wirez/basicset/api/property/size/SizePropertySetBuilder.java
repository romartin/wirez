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

package org.wirez.basicset.api.property.size;

import org.wirez.basicset.api.property.font.FontBorderSizeBuilder;
import org.wirez.basicset.api.property.font.FontColorBuilder;
import org.wirez.basicset.api.property.font.FontSizeBuilder;
import org.wirez.core.api.definition.property.DefaultPropertySet;
import org.wirez.core.api.definition.property.builder.BasePropertySetBuilder;

public class SizePropertySetBuilder extends BasePropertySetBuilder<DefaultPropertySet> {

    private static final String ID = "basicset.size";
    private static final String NAME = "Size";
    
    public SizePropertySetBuilder() {
        super();
        propertySet = new DefaultPropertySet(ID, NAME, properties);
    }

}
