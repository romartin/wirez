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

package org.wirez.bpmn.client.shape;

import org.wirez.bpmn.definition.property.background.BackgroundSet;
import org.wirez.bpmn.definition.property.font.FontSet;
import org.wirez.bpmn.definition.property.general.BPMNGeneral;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.view.View;
import org.wirez.shapes.client.AbstractBasicTitledShape;
import org.wirez.shapes.client.view.BasicShapeView;

public abstract class BPMNBasicShape<W, V extends BasicShapeView>
    extends AbstractBasicTitledShape<W, V> {

    public BPMNBasicShape(final V shapeView) {
        super(shapeView);
    }

    protected abstract BPMNGeneral getBPMBpmnGeneralSet(Node<View<W>, Edge> element);

    protected abstract BackgroundSet getBackgroundSet(Node<View<W>, Edge> element);

    protected abstract FontSet getFontSet(Node<View<W>, Edge> element);

    @Override
    protected String getNamePropertyValue(final Node<View<W>, Edge> element) {
        return getBPMBpmnGeneralSet( element ).getName().getValue();
    }

    @Override
    protected String getBackgroundColor(final Node<View<W>, Edge> element) {
        return getBackgroundSet( element ).getBgColor().getValue();
    }

    @Override
    protected String getBorderColor(final Node<View<W>, Edge> element) {
        return getBackgroundSet( element ).getBorderColor().getValue();
    }

    @Override
    protected Double getBorderSize(final Node<View<W>, Edge> element) {
        return getBackgroundSet( element ).getBorderSize().getValue();
    }

    @Override
    protected String getFontFamily(final Node<View<W>, Edge> element) {
        return getFontSet( element ).getFontFamily().getValue();
    }

    @Override
    protected String getFontColor(final Node<View<W>, Edge> element) {
        return getFontSet( element ).getFontColor().getValue();
    }

    @Override
    protected Double getFontSize(final Node<View<W>, Edge> element) {
        return getFontSet( element ).getFontSize().getValue();
    }

    @Override
    protected Double getFontBorderSize(final Node<View<W>, Edge> element) {
        return getFontSet( element ).getFontBorderSize().getValue();
    }
    
}
