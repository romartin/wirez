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

import ${parentAdapterClassName};

import javax.annotation.Generated;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Generated("${generatedByClassName}")
@ApplicationScoped
public class ${className} extends ${parentAdapterClassName} {

    private static final Map<Class, String> descriptionFieldNames = new HashMap<Class, String>(${valuePropNamesSize}) {{
        <#list valuePropNames as valuePropName>
            put( ${valuePropName.className}.class, "${valuePropName.methodName}" );
        </#list>
    
    }};

    private static final Set<String> definitionIds = new HashSet<String>(${definitionIdsSize}) {{
        <#list definitionIds as definitionId>
            add( "${definitionId}" );
        </#list>
    }};

    private static final Map<Class, Class> graphTypes = new HashMap<Class, Class>(${graphTypesSize}) {{
    <#list graphTypes as graphType>
        put( ${graphType.className}.class, ${graphType.methodName}.class );
    </#list>
        }};
    
        private static final Map<Class, String> graphFactories = new HashMap<Class, String>(${graphFactoriesSize}) {{
    <#list graphFactories as graphFactory>
        put( ${graphFactory.className}.class, "${graphFactory.methodName}" );
    </#list>
        }};

    @Override
    protected Map<Class, String> getPropertyDescriptionFieldNames() {
        return descriptionFieldNames;
    }
    
    @Override
    protected Set<String> getDefinitionIds() {
        return definitionIds;
    }

    @Override
    protected Map<Class, Class> getGraphTypes() {
        return graphTypes;
    }

    @Override
    protected Map<Class, String> getGraphFactory() {
        return graphFactories;
    }

}
