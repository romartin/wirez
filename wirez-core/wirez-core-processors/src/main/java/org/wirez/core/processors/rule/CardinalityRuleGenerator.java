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

package org.wirez.core.processors.rule;

import org.jboss.errai.bus.client.api.messaging.Message;
import org.uberfire.annotations.processors.AbstractGenerator;
import org.uberfire.annotations.processors.exceptions.GenerationException;
import org.uberfire.relocated.freemarker.template.Template;
import org.uberfire.relocated.freemarker.template.TemplateException;
import org.wirez.core.api.annotation.rule.EdgeOccurrences;
import org.wirez.core.api.annotation.rule.EdgeType;
import org.wirez.core.api.annotation.rule.Occurrences;
import org.wirez.core.api.annotation.rule.PermittedConnection;
import org.wirez.core.processors.MainProcessor;
import org.wirez.core.processors.ProcessingContext;
import org.wirez.core.processors.ProcessingRule;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardinalityRuleGenerator extends AbstractGenerator  {

    public class CardinalityRuleEntry {
        private final long min;
        private final long max;
        private final String edgeDefinitionId;
        private final String name;

        public CardinalityRuleEntry(long min, long max, String edgeDefinitionId, String name) {
            this.min = min;
            this.max = max;
            this.edgeDefinitionId = edgeDefinitionId;
            this.name = name;
        }

        public long getMin() {
            return min;
        }

        public long getMax() {
            return max;
        }

        public String getEdgeDefinitionId() {
            return edgeDefinitionId;
        }

        public String getName() {
            return name;
        }
    }

    private final ProcessingContext processingContext = ProcessingContext.getInstance();
    
    @Override
    public StringBuffer generate(String packageName, PackageElement packageElement, String className, Element element, ProcessingEnvironment processingEnvironment) throws GenerationException {

        final Messager messager = processingEnvironment.getMessager();
        messager.printMessage( Diagnostic.Kind.NOTE, "Starting code generation for [" + className + "]" );

        final Elements elementUtils = processingEnvironment.getElementUtils();

        //Extract required information
        final TypeElement classElement = (TypeElement) element;
        final boolean isInterface = classElement.getKind().isInterface();
        final String ruleId = MainProcessor.toValidId(className);
        final String ruleDefinitionId = className.substring(0, ( className.length() - MainProcessor.RULE_CARDINALITY_SUFFIX_CLASSNAME.length()) );

        Occurrences[] occs = classElement.getAnnotationsByType(Occurrences.class);
        if ( null != occs ) {
            
            for (Occurrences occurrence : occs) {
                String role = occurrence.role();
                String id = occurrence.id();
                if ( null == id || id.trim().length() == 0 ) {
                    id = ruleId + "_" + role + "_" + MainProcessor.RULE_CARDINALITY_SUFFIX_CLASSNAME;
                }
                long min = occurrence.min();
                long max = occurrence.max();
                List<CardinalityRuleEntry> incomingEdgeRules = new ArrayList<>();
                List<CardinalityRuleEntry> outgoingEdgeRules = new ArrayList<>();
                EdgeOccurrences[] edgeOccurrences = occurrence.value();
                if ( null != edgeOccurrences ) {
                    for (EdgeOccurrences edgeOccurrence : edgeOccurrences) {
                        EdgeType type = edgeOccurrence.type();
                        String edgeDefId = edgeOccurrence.edge();
                        long edgeMin = edgeOccurrence.min();
                        long edgeMax = edgeOccurrence.max();
                        if ( EdgeType.INCOMING.equals(type)) {
                            incomingEdgeRules.add( new CardinalityRuleEntry( edgeMin, edgeMax, edgeDefId, "incoming" + edgeDefId + MainProcessor.RULE_CARDINALITY_SUFFIX_CLASSNAME ) );
                        } else {
                            outgoingEdgeRules.add( new CardinalityRuleEntry( edgeMin, edgeMax, edgeDefId, "outgoing" + edgeDefId + MainProcessor.RULE_CARDINALITY_SUFFIX_CLASSNAME ) );
                        }

                    }
                }

                StringBuffer ruleSourceCode = generateRule(messager, id, role, min, max, incomingEdgeRules, outgoingEdgeRules);

                processingContext.addRule(id, ProcessingRule.TYPE.CARDINALITY, ruleSourceCode);
                
            }
            
        }
        
        return null;

    }
    
    private StringBuffer generateRule(Messager messager, 
                                       String ruleId, 
                                       String ruleRoleId,
                                       long min,
                                       long max,
                                       List<CardinalityRuleEntry> incomingEdgeRules,
                                       List<CardinalityRuleEntry> outgoingEdgeRules) throws GenerationException {
        
        Map<String, Object> root = new HashMap<String, Object>();
        root.put( "ruleId",
                ruleId );
        root.put( "ruleRoleId",
                ruleRoleId );
        root.put( "min",
                min );
        root.put( "max",
                max );
        root.put( "incomingRulesSize",
                incomingEdgeRules.size() );
        root.put( "incomingRules",
                incomingEdgeRules );
        root.put( "outgoingRulesSize",
                outgoingEdgeRules.size() );
        root.put( "outgoingRules",
                outgoingEdgeRules );

        //Generate code
        final StringWriter sw = new StringWriter();
        final BufferedWriter bw = new BufferedWriter( sw );
        try {
            final Template template = config.getTemplate( "CardinalityRule.ftl" );
            template.process( root,
                    bw );
        } catch ( IOException ioe ) {
            throw new GenerationException( ioe );
        } catch ( TemplateException te ) {
            throw new GenerationException( te );
        } finally {
            try {
                bw.close();
                sw.close();
            } catch ( IOException ioe ) {
                throw new GenerationException( ioe );
            }
        }
        messager.printMessage( Diagnostic.Kind.NOTE, "Successfully generated code for [" + ruleId + "]" );

        return sw.getBuffer();
    }

}
