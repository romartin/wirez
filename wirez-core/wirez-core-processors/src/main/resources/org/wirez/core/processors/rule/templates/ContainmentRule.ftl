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
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.annotation.PostConstruct;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.rule.impl.DefaultContainmentRule;
import org.wirez.core.api.registry.RuleRegistry;
import org.wirez.core.api.rule.Rule;
import java.util.HashSet;

/*
 * WARNING! This class is generated. Do not modify.
 */
@Generated("org.wirez.core.processors.rule.RuleProcessor")
@ApplicationScoped
@Portable
public class ${className} extends DefaultContainmentRule {

    @Inject
    transient RuleRegistry<Rule> ruleRegistry;
    
    @Inject
    ${ruleDefinitionId} definition;

    public ${className}() {
        super("${ruleName}", "${ruleDefinitionId}", new HashSet<String>() {{
                <#list roles as role>
                    add( ${role} );
                </#list>
            }});
    }

    @PostConstruct
    public void init() {
        ruleRegistry.addRule(this, definition);
    }

}
