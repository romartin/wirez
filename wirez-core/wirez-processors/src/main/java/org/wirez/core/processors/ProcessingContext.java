package org.wirez.core.processors;

import java.util.ArrayList;
import java.util.List;

public class ProcessingContext {

    private static ProcessingContext context;
    private ProcessingEntity definitionSet;
    private String namePropertyClass;
    private final List<ProcessingRule> rules = new ArrayList<>();

    private final ProcessingDefinitionSetAnnotations defSetAnnotations = new ProcessingDefinitionSetAnnotations();
    private final ProcessingDefinitionAnnotations definitionAnnotations = new ProcessingDefinitionAnnotations();
    private final ProcessingPropertySetAnnotations propertySetAnnotations = new ProcessingPropertySetAnnotations();
    private final ProcessingPropertyAnnotations propertyAnnotations = new ProcessingPropertyAnnotations();

    public synchronized static ProcessingContext getInstance() {
        if ( null == context ) {
            context = new ProcessingContext();
        }
        
        return context;
    }

    public ProcessingContext() {
    }
    
    public ProcessingEntity getDefinitionSet() {
        return definitionSet;
    }

    public String getNamePropertyClass() {
        return namePropertyClass;
    }

    public void setNamePropertyClass(String namePropertyClass) {
        this.namePropertyClass = namePropertyClass;
    }

    public void setDefinitionSet(String packageName, String className) {
        if ( null != this.definitionSet ) {
            throw new RuntimeException("Only a single definition set allowed for a single processing.");
        }
        this.definitionSet = new ProcessingEntity(packageName + "." + className, className);
    }

    public void addRule(String id, ProcessingRule.TYPE type, StringBuffer content) {
        rules.add(new ProcessingRule(id, type, content));
    }
    
    public List<ProcessingRule> getRules() {
        return rules;
    }

    public ProcessingDefinitionSetAnnotations getDefSetAnnotations() {
        return defSetAnnotations;
    }

    public ProcessingDefinitionAnnotations getDefinitionAnnotations() {
        return definitionAnnotations;
    }

    public ProcessingPropertySetAnnotations getPropertySetAnnotations() {
        return propertySetAnnotations;
    }

    public ProcessingPropertyAnnotations getPropertyAnnotations() {
        return propertyAnnotations;
    }
}
