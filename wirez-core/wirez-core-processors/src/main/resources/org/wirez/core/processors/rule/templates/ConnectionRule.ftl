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

import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.rule.impl.DefaultConnectionRule;
import org.wirez.core.api.rule.impl.DefaultPermittedConnection;

import javax.annotation.Generated;
import javax.enterprise.context.Dependent;
import java.util.HashSet;

/*
 * WARNING! This class is generated. Do not modify.
 */
@Generated("org.wirez.core.processors.rule.ConnectionRuleGenerator")
@Dependent
@Portable
public class ${className} extends DefaultConnectionRule {

    public ${className}() {
        super("${ruleName}", "${ruleDefinitionId}", new HashSet<PermittedConnection>() {{
                <#list connections as connection>
                    new DefaultPermittedConnection( "${connection.from}", "${connection.to}" );
                </#list>
            }});
    }

}
