
DefaultConnectionRule ${ruleId} = new DefaultConnectionRule("${ruleId}", "${ruleDefinitionId}",
    new HashSet<ConnectionRule.PermittedConnection>(${connectionsSize}) {{
    <#list connections as connection>
        add(new DefaultPermittedConnection( "${connection.from}", "${connection.to}" ));
    </#list>
    }}
);