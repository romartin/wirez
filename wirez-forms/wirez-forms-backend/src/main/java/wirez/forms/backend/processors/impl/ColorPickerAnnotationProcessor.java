/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
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

package wirez.forms.backend.processors.impl;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.livespark.formmodeler.renderer.backend.service.impl.FieldSetting;
import org.livespark.formmodeler.renderer.backend.service.impl.processors.AbstractFieldAnnotationProcessor;
import org.livespark.formmodeler.service.FieldManager;
import org.wirez.forms.meta.definition.ColorPicker;
import org.wirez.forms.model.ColorPickerFieldDefinition;

/**
 * @author Pere Fernandez <pefernan@redhat.com>
 */
@Dependent
public class ColorPickerAnnotationProcessor extends AbstractFieldAnnotationProcessor<ColorPickerFieldDefinition> {

    @Inject
    public ColorPickerAnnotationProcessor( FieldManager fieldManager ) {
        super( fieldManager );
    }

    @Override
    protected ColorPickerFieldDefinition buildFieldDefinition( Annotation annotation, FieldSetting setting ) {
        ColorPickerFieldDefinition field = new ColorPickerFieldDefinition();

        if ( !Arrays.asList( field.getSupportedTypes() ).contains( setting.getType().getName() )
                || !supportsAnnotation( annotation )) {
            return null;
        }

        ColorPicker colorPickerDef = (ColorPicker) annotation;

        field.setDefaultValue( colorPickerDef.defaultValue() );

        return field;
    }

    @Override
    public boolean supportsAnnotation( Annotation annotation ) {
        return annotation instanceof ColorPicker;
    }
}
