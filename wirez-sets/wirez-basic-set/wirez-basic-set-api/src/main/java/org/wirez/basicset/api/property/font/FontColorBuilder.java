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

package org.wirez.basicset.api.property.font;

import org.wirez.core.api.definition.property.builder.BasePropertyBuilder;
import org.wirez.core.api.definition.property.type.ColorType;

public class FontColorBuilder extends BasePropertyBuilder<String> {
    
    public static final String PROPERTY_ID = "fontcolor";
    
    public FontColorBuilder() {
        super(PROPERTY_ID, new ColorType());
        this.caption("Font Color")
            .description("The font color")
            .optional(true)
            .readOnly(false)
            .publish(true)
            .defaultValue("#000000");
    }
    
}