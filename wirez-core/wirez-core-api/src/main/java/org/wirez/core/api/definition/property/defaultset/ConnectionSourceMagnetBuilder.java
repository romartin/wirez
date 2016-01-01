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

package org.wirez.core.api.definition.property.defaultset;

import org.wirez.core.api.definition.property.builder.BasePropertyBuilder;
import org.wirez.core.api.definition.property.type.IntegerType;
import org.wirez.core.api.definition.property.type.StringType;

public class ConnectionSourceMagnetBuilder extends BasePropertyBuilder<Integer> {
    
    public static final String PROPERTY_ID = "connectionSourceMagnet";
    
    public ConnectionSourceMagnetBuilder() {
        super(PROPERTY_ID, new IntegerType());
        this.caption("Source connection magnet")
            .description("The source connection magnet")
            .optional(true)
            .readOnly(false)
            .publish(true)
            .defaultValue(0);
    }
    
}
