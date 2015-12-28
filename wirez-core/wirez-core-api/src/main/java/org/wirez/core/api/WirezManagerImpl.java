/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
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

package org.wirez.core.api;

import org.jboss.errai.ioc.client.container.IOCBeanDef;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.wirez.core.api.definition.DefinitionSet;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ApplicationScoped
public class WirezManagerImpl implements WirezManager {

    protected SyncBeanManager beanManager;
    private final List<DefinitionSet> definitionSets = new ArrayList<DefinitionSet>();

    @Inject
    public WirezManagerImpl(final SyncBeanManager beanManager) {
        this.beanManager = beanManager;
    }

    @PostConstruct
    public void init() {
        initDefinitionSets();
    }

    private void initDefinitionSets() {
        Collection<IOCBeanDef<DefinitionSet>> beanDefs = beanManager.lookupBeans(DefinitionSet.class);
        for (IOCBeanDef<DefinitionSet> beanDef : beanDefs) {
            DefinitionSet definitionSet = beanDef.getInstance();
            definitionSets.add(definitionSet);
        }
    }
    
    @Override
    public Collection<DefinitionSet> getDefinitionSets() {
        return definitionSets;
    }
}
