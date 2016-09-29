package org.kie.workbench.common.stunner.basicset.definition.icon.statics;

import org.kie.workbench.common.stunner.core.definition.adapter.binding.BindableAdapterUtils;
import org.kie.workbench.common.stunner.shapes.proxy.icon.statics.Icons;

public class StaticIcons {

    public static String getIconDefinitionId( final Icons icon ) {

        Class<?> type = null;

        switch ( icon ) {

            case BUSINESS_RULE:
                type = BusinessRuleIcon.class;
                break;

            case USER:
                type = UserIcon.class;
                break;

            case SCRIPT:
                type = ScriptIcon.class;
                break;

            case TIMER:
                type = TimerIcon.class;
                break;

        }

        if ( null != type ) {

            return getDefinitionId( type );

        }

        return null;

    }

    private static String getDefinitionId( final Class<?> type ) {
        return BindableAdapterUtils.getDefinitionId( type );
    }

}
