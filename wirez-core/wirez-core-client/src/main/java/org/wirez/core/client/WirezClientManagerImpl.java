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

package org.wirez.core.client;

import org.jboss.errai.ioc.client.container.IOCBeanDef;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.wirez.core.api.WirezManagerImpl;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.client.factory.ShapeFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ApplicationScoped
public class WirezClientManagerImpl extends WirezManagerImpl implements WirezClientManager {
    
    private final List<ShapeSet> shapeSets = new ArrayList<ShapeSet>();

    @Inject
    public WirezClientManagerImpl(final SyncBeanManager beanManager) {
        super(beanManager);
    }

    @PostConstruct
    public void init() {
        super.init();
        initShapeSets();
    }

    private void initShapeSets() {
        shapeSets.clear();
        Collection<IOCBeanDef<ShapeSet>> beanDefs = beanManager.lookupBeans(ShapeSet.class);
        for (IOCBeanDef<ShapeSet> beanDef : beanDefs) {
            ShapeSet shapeSet = beanDef.getInstance();
            shapeSets.add(shapeSet);
        }
    }

    @Override
    public Collection<ShapeSet> getShapeSets() {
        return shapeSets;
    }

    @Override
    public ShapeFactory getFactory(final Definition definition) {
        final Collection<ShapeSet> shapeSets = getShapeSets();
        for (final ShapeSet wirezShapeSet : shapeSets) {
            final Collection<ShapeFactory<? extends Definition, ? extends Shape>> factories = wirezShapeSet.getFactories();
            for (final ShapeFactory<? extends Definition, ? extends Shape> factory : factories) {
                if (factory.accepts(definition)) {
                    return factory;
                }
            }
        }

        return null;
    }

}
