
DefaultCardinalityRule ${ruleId} = new DefaultCardinalityRule("${ruleId}", "${ruleRoleId}", ${min}l, ${max}l,
    new HashSet<CardinalityRule.ConnectorRule>(${incomingRulesSize}) {{
        <#list incomingRules as rule>
            add(new DefaultConnectorRule(${rule.min}l, ${rule.max}l, "${rule.edgeDefinitionId}", "${rule.name}"));
        </#list>
    }},
    new HashSet<CardinalityRule.ConnectorRule>(${outgoingRulesSize}) {{
        <#list outgoingRules as rule>
            add(new DefaultConnectorRule(${rule.min}l, ${rule.max}l, "${rule.edgeDefinitionId}", "${rule.name}"));
        </#list>
    }}
);