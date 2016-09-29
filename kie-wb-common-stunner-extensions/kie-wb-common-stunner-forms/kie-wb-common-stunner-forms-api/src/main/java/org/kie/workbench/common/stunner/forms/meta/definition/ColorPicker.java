package org.kie.workbench.common.stunner.forms.meta.definition;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Pere Fernandez <pefernan@redhat.com>
 */
@java.lang.annotation.Retention( RetentionPolicy.RUNTIME )
@java.lang.annotation.Target({ ElementType.TYPE, ElementType.FIELD })
public @interface ColorPicker {
    String defaultValue() default "#000000";
}
