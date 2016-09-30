/*
 * Copyright 2016  Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wirez.bpmn.definition.property.gateway;

import javax.validation.Valid;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.kie.workbench.common.forms.metaModel.FieldDef;
import org.kie.workbench.common.forms.metaModel.ListBox;
import org.kie.workbench.common.forms.metaModel.SelectorDataProvider;
import org.wirez.bpmn.definition.BPMNPropertySet;
import org.wirez.bpmn.definition.property.task.RuleFlowGroup;
import org.wirez.core.definition.annotation.Name;
import org.wirez.core.definition.annotation.propertyset.Property;
import org.wirez.core.definition.annotation.propertyset.PropertySet;

@Portable
@Bindable
@PropertySet
public class ExclusiveGatewayExecutionSet implements BPMNPropertySet {

    @Name
    public static final transient String propertySetName = "Implementation/Execution";

    @Property
    @FieldDef( label = "Default route", property = "value" )
    @ListBox
    @SelectorDataProvider(
            type = SelectorDataProvider.ProviderType.CLIENT,
            className = "org.wirez.forms.client.fields.exclusiveGateway.DefaultRouteFormProvider")
    @Valid
    protected DefaultRoute defaultRoute;

    public ExclusiveGatewayExecutionSet() {
        this(new DefaultRoute());
    }

    public ExclusiveGatewayExecutionSet(@MapsTo("defaultRoute") DefaultRoute defaultRoute) {
        this.defaultRoute = defaultRoute;
    }

    public ExclusiveGatewayExecutionSet(String defaultRoute) {
        this.defaultRoute = new DefaultRoute(defaultRoute);
    }

    public String getPropertySetName() {
        return propertySetName;
    }

    public DefaultRoute getDefaultRoute() {
        return defaultRoute;
    }

    public void setDefaultRoute(DefaultRoute defaultRoute) {
        this.defaultRoute = defaultRoute;
    }
}
