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

import ${parentClassName};
import ${defSetClass};
import javax.annotation.Generated;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.kie.workbench.common.stunner.core.api.DefinitionManager;

@Generated( "${generatedByClassName}" )
@ApplicationScoped
public class ${className} extends ${parentClassName} {
    
    public static final String ID = "${className}";

    protected ${className}() {
    }

    @Inject
    public ${className}( final DefinitionManager definitionManager) {
        super( definitionManager );
    }

    @PostConstruct
    public void init() {
        super.doInit();
    }
    
    @Override
    public String getId() {
        return ID;
    }
    
    @Override
    protected Class<?> getDefinitionSetClass() {
        return ${defSetClass}.class;
    }

}
