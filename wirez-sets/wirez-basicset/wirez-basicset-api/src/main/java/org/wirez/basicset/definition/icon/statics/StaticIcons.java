package org.wirez.basicset.definition.icon.statics;

import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.shapes.proxy.icon.statics.Icons;

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
