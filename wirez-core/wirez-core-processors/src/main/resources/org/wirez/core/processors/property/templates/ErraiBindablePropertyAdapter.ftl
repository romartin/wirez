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

import org.wirez.core.api.adapter.generated.GeneratedDefinitionSetPropertyAdapter;

import javax.annotation.Generated;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;

@Generated("org.wirez.core.processors.property.ErraiBindablePropertyAdapterGenerator")
@ApplicationScoped
public class ${className} extends GeneratedDefinitionSetPropertyAdapter {

    private static final Map<Class, String> propValueFieldNames = new HashMap<Class, String>(${valuePropNamesSize}) {{
        <#list valuePropNames as valuePropName>
            put( ${valuePropName.className}.class, "${valuePropName.methodName}" );
        </#list>
    
    }};

    private static final Map<Class, String> propDefaultValueFieldNames = new HashMap<Class, String>(${defaultValuePropNamesSize}) {{
        <#list defaultValuePropNames as defaultValuePropName>
            put( ${defaultValuePropName.className}.class, "${defaultValuePropName.methodName}" );
        </#list>
    }};

    @Override
    protected Map<Class, String> getPropertyValueFieldNames() {
        return propValueFieldNames;
    }
    
    @Override
    protected Map<Class, String> getPropertyDefaultValueFieldNames() {
        return propDefaultValueFieldNames;
    }

}
