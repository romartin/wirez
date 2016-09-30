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
package org.kie.workbench.common.stunner.bpmn.definition.property.background;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.kie.workbench.common.forms.metaModel.FieldDef;
import org.kie.workbench.common.forms.metaModel.Slider;
import org.kie.workbench.common.stunner.bpmn.definition.BPMNPropertySet;
import org.kie.workbench.common.stunner.core.definition.annotation.Name;
import org.kie.workbench.common.stunner.core.definition.annotation.propertyset.Property;
import org.kie.workbench.common.stunner.core.definition.annotation.propertyset.PropertySet;
import org.kie.workbench.common.stunner.forms.meta.definition.ColorPicker;

import javax.validation.Valid;

@Portable
@Bindable
@PropertySet
public class BackgroundSet implements BPMNPropertySet {

    @Name
    public static final transient String propertySetName = "Background Set";

    @Property
    @FieldDef(label = "Background Color", property = "value")
    @ColorPicker
    @Valid
    private BgColor bgColor;

    @Property
    @FieldDef(label = "Border Color", property = "value")
    @ColorPicker
    @Valid
    private BorderColor borderColor;

    @Property
    @FieldDef(label = "Border Size", property = "value")
    @Slider( min = 0.0, max = 5.0, step = 0.5)
    @Valid
    private BorderSize borderSize;

    public BackgroundSet() {
        this( new BgColor(),
                new BorderColor(),
                new BorderSize() );
    }

    public BackgroundSet(@MapsTo("bgColor") BgColor bgColor,
                         @MapsTo("borderColor") BorderColor borderColor,
                         @MapsTo("borderSize") BorderSize borderSize) {
        this.bgColor = bgColor;
        this.borderColor = borderColor;
        this.borderSize = borderSize;
    }

    public BackgroundSet( String bgColor,
                          String borderColor,
                          Double borderSize) {
        this.bgColor = new BgColor( bgColor );
        this.borderColor = new BorderColor( borderColor );
        this.borderSize = new BorderSize( borderSize );
    }

    public String getPropertySetName() {
        return propertySetName;
    }

    public BgColor getBgColor() {
        return bgColor;
    }

    public BorderColor getBorderColor() {
        return borderColor;
    }

    public BorderSize getBorderSize() {
        return borderSize;
    }

    public void setBgColor( BgColor bgColor ) {
        this.bgColor = bgColor;
    }

    public void setBorderColor( BorderColor borderColor ) {
        this.borderColor = borderColor;
    }

    public void setBorderSize( BorderSize borderSize ) {
        this.borderSize = borderSize;
    }
}
