package org.wirez.forms.service.fieldProviders;

import org.livespark.formmodeler.model.FieldTypeInfo;
import org.livespark.formmodeler.service.impl.fieldProviders.BasicTypeFieldProvider;
import org.wirez.forms.model.ColorPickerFieldDefinition;

/**
 * @author Pere Fernandez <pefernan@redhat.com>
 */
public class ColorPickerFieldProvider extends BasicTypeFieldProvider<ColorPickerFieldDefinition> {
    @Override
    public int getPriority() {
        return 100;
    }

    @Override
    protected void doRegisterFields() {
        registerPropertyType( String.class );
    }

    @Override
    public ColorPickerFieldDefinition createFieldByType( FieldTypeInfo typeInfo ) {
        return getDefaultField();
    }

    @Override
    public String getProviderCode() {
        return ColorPickerFieldDefinition.CODE;
    }

    @Override
    public ColorPickerFieldDefinition getDefaultField() {
        return new ColorPickerFieldDefinition();
    }
}
