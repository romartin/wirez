package org.wirez.shapes.proxy.icon.statics;

public enum Icons {

    USER( "user" ),

    SCRIPT( "script" ),

    BUSINESS_RULE( "businessRule" ),

    TIMER( "timer" );

    private final String id;

    Icons(final String id ) {
        this.id = id;
    }

    public static Icons parse(final String id ) {

        if ( USER.id.equals( id ) ) {
            return USER;
        }

        if ( SCRIPT.id.equals( id ) ) {
            return SCRIPT;
        }

        if ( BUSINESS_RULE.id.equals( id ) ) {
            return BUSINESS_RULE;
        }

        return null;
    }
    
}
