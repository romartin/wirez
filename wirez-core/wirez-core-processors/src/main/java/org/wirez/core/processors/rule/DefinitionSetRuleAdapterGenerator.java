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

import org.uberfire.annotations.processors.exceptions.GenerationException;
import org.uberfire.relocated.freemarker.template.Configuration;
import org.uberfire.relocated.freemarker.template.DefaultObjectWrapper;
import org.uberfire.relocated.freemarker.template.Template;
import org.uberfire.relocated.freemarker.template.TemplateException;
import org.wirez.core.processors.ProcessingElement;
import org.wirez.core.processors.ProcessingEntity;
import org.wirez.core.processors.ProcessingRule;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

public class DefinitionSetRuleAdapterGenerator {

    private static ExceptionInInitializerError INITIALIZER_EXCEPTION = null;
    private Configuration config;

    public DefinitionSetRuleAdapterGenerator() {
        try {
            this.config = new Configuration();
            this.config.setClassForTemplateLoading(this.getClass(), "templates");
            this.config.setObjectWrapper(new DefaultObjectWrapper());
        } catch (NoClassDefFoundError var2) {
            if (var2.getCause() == null) {
                var2.initCause(INITIALIZER_EXCEPTION);
            }

            throw var2;
        } catch (ExceptionInInitializerError var3) {
            INITIALIZER_EXCEPTION = var3;
            throw var3;
        }
    }


    public StringBuffer generate(String packageName, String className,
                                 String defSetClassName,
                                 List<ProcessingRule> rules,
                                 Messager messager) throws GenerationException {

        Map<String, Object> root = new HashMap<String, Object>();
        
        
        root.put("packageName",
                packageName);
        root.put("className",
                className);
        root.put("defSetClassName",
                defSetClassName);
        root.put("rules",
                rules);
        root.put("rulesSize",
                rules.size());
        root.put("ruleIds",
                getRuleIds(rules));
        
        //Generate code
        final StringWriter sw = new StringWriter();
        final BufferedWriter bw = new BufferedWriter(sw);
        try {
            final Template template = config.getTemplate("DefinitionSetRuleAdapter.ftl");
            template.process(root,
                    bw);
        } catch (IOException ioe) {
            throw new GenerationException(ioe);
        } catch (TemplateException te) {
            throw new GenerationException(te);
        } finally {
            try {
                bw.close();
                sw.close();
            } catch (IOException ioe) {
                throw new GenerationException(ioe);
            }
        }

        messager.printMessage(Diagnostic.Kind.NOTE, "Successfully generated code for [" + packageName + "." + className + "]");

        return sw.getBuffer();

    }
    
    private List<String> getRuleIds(List<ProcessingRule> rules) {
        List<String> result = new ArrayList<>();
        for (ProcessingRule entity : rules ) {
            result.add(entity.getId());
        }
        return result;
    }
    
}
