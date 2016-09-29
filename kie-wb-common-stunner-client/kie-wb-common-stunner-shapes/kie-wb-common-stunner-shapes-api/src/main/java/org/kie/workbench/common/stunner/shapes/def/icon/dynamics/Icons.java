package org.kie.workbench.common.stunner.shapes.def.icon.dynamics;

public enum Icons {

    PLUS( "plus" ),

    MINUS( "minus" ),

    XOR( "xor" );

    private final String id;

    Icons(final String id ) {
        this.id = id;
    }

    public static Icons parse(final String id ) {

        if ( PLUS.id.equals( id ) ) {
            return PLUS;
        }

        if ( MINUS.id.equals( id ) ) {
            return MINUS;
        }

        if ( XOR.id.equals( id ) ) {
            return XOR;
        }

        return null;
    }
    
}
