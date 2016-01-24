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

import org.jboss.errai.databinding.client.HasProperties;
import org.jboss.errai.databinding.client.api.DataBinder;
import org.wirez.core.api.adapter.PropertyAdapter;
import org.wirez.core.api.definition.property.Property;

import javax.annotation.Generated;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;

@Generated("org.wirez.core.processors.property.ErraiBindablePropertyAdapterGenerator")
@ApplicationScoped
public class ${className} implements PropertyAdapter<Property> {

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
    public Object getValue(final Property pojo) {
        Object result = null;
        if ( null != pojo ) {
            Class pojoClass = pojo.getClass();
            String valueFieldName = propValueFieldNames.get(pojoClass);
            HasProperties hasProperties = (HasProperties) DataBinder.forModel(pojo).getModel();
            result = hasProperties.get(valueFieldName);
        }

        return result;
    }

    @Override
    public Object getDefaultValue(final Property pojo) {
        Object result = null;
        if ( null != pojo ) {
            Class pojoClass = pojo.getClass();
            String valueFieldName = propDefaultValueFieldNames.get(pojoClass);
            HasProperties hasProperties = (HasProperties) DataBinder.forModel(pojo).getModel();
            result = hasProperties.get(valueFieldName);
        }
    
        return result;
    }

    @Override
    public void setValue(final Property pojo, final Object value) {
        if ( null != pojo ) {
            Class pojoClass = pojo.getClass();
            String valueFieldName = propValueFieldNames.get(pojoClass);
            HasProperties hasProperties = (HasProperties) DataBinder.forModel(pojo).getModel();
            hasProperties.set(valueFieldName, value);
        }
    }

    @Override
    public boolean accepts(final Object pojo) {
        return propValueFieldNames.containsKey(pojo.getClass());

    }

    @Override
    public int getPriority() {
        return 0;
    }

}
