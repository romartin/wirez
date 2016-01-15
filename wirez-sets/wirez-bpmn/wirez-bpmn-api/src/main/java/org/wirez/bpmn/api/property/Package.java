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

package org.wirez.bpmn.api.property;

import org.wirez.core.api.annotation.property.DefaultValue;
import org.wirez.core.api.definition.property.HasDefaultValue;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.type.StringType;

@org.wirez.core.api.annotation.property.Property( 
        identifier = Package.ID, 
        type = StringType.class,
        caption = "Package", 
        description = "The diagram's package", 
        readOnly = false,
        optional = true,
        isPublic = true)
public interface Package extends Property, HasDefaultValue<String> {

    String ID = "package";
    
    @DefaultValue
    String DEFAULT_VALUE = "/defaultPackage/defaultPackage";
    
}
