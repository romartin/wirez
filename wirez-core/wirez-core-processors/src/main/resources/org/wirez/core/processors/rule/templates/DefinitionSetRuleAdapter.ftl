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

import org.wirez.core.api.adapter.DefinitionSetRuleAdapter;
import org.wirez.core.api.rule.Rule;

import javax.annotation.Generated;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Generated("org.wirez.core.processors.rule.DefinitionSetRuleAdapterGenerator")
@ApplicationScoped
public class ${className} implements DefinitionSetRuleAdapter<${defSetClassName}> {

    <#list rules as rule>
        @Inject
        ${rule.className} ${rule.id};
    </#list>

    private static final Set<Rule> rules = new HashSet<Rule>(${rulesSize});

    @PostConstruct
    public void init() {
        <#list ruleIds as ruleId>
            rules.add(${ruleId});
        </#list>
    }

    @Override
    public boolean accepts(final Class pojoClass) {
        return pojoClass.equals(${defSetClassName}.class);
    }

    @Override
    public Collection<Rule> getRules(final ${defSetClassName} pojo) {
        return rules;
    }

    @Override
    public int getPriority() {
        return 1;
    }

}
