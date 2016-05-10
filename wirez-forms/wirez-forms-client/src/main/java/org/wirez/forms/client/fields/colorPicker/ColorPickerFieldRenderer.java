package org.wirez.forms.client.fields.colorPicker;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.google.gwt.user.client.ui.IsWidget;
import org.livespark.formmodeler.renderer.client.rendering.FieldRenderer;
import org.wirez.forms.model.ColorPickerFieldDefinition;

/**
 * @author Pere Fernandez <pefernan@redhat.com>
 */
@Dependent
public class ColorPickerFieldRenderer extends FieldRenderer<ColorPickerFieldDefinition> {

    private ColorPickerWidget colorPicker;

    @Inject
    public ColorPickerFieldRenderer( ColorPickerWidget colorPicker ) {
        this.colorPicker = colorPicker;
    }

    @Override
    public String getName() {
        return ColorPickerFieldDefinition.CODE;
    }

    @Override
    public void initInputWidget() {

    }

    @Override
    public IsWidget getInputWidget() {
        return colorPicker;
    }

    @Override
    public String getSupportedFieldDefinitionCode() {
        return ColorPickerFieldDefinition.CODE;
    }
}
