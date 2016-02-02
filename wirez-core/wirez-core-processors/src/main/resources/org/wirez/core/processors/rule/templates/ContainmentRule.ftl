
DefaultContainmentRule  ${ruleId} = new DefaultContainmentRule("${ruleId}", "${ruleDefinitionId}", new HashSet<String>() {{
    <#list roles as role>
        add( ${role} );
    </#list>
}});

