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

package ${packageName};

import javax.annotation.Generated;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.annotation.PostConstruct;
import org.roger600.uberfire.testapp.api.model.Definition;
import org.roger600.uberfire.testapp.api.model.RuntimeDefinition;
import org.roger600.uberfire.testapp.api.model.property.Property;

import java.util.List;
import java.util.ArrayList;

/*
 * WARNING! This class is generated. Do not modify.
 */
@Generated("org.wirez.core.processors.definition.DefinitionProcessor")
@Dependent
public class ${className} implements ${realClassName}, RuntimeDefinition {

    // private final ${realClassName} realDefinition = new ${realClassName}();

    List<Property> properties = new ArrayList();
    
    <#list properties as property>
        @Inject
        ${property.propertyClassName} ${property.methodName};
    </#list>

    public ${className}() {}

    @PostConstruct
    public void init() {
        <#list properties as property>
            properties.add(${property.methodName});
        </#list>
    }
    
    @Override
    public String getId() {
        return "${identifier}";
    }

    @Override
    public String getName() {
        return "${defName}";
    }

    @Override
    public List<Property> getProperties() {
        return properties;
    }

    <#list properties as property>
        @Override
        public ${property.propertyClassName} ${property.methodName}() {
          return ${property.methodName};
        }
    </#list>

}
