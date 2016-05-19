package org.wirez.shapes.proxy.icon;

public enum ICONS {

    PLUS( "plus" ),

    MINUS( "minus" ),

    XOR( "xor" );

    private final String id;

    ICONS( final String id ) {
        this.id = id;
    }

    public static ICONS parse(final String id ) {

        if ( PLUS.id.endsWith( id ) ) {
            return PLUS;
        }

        if ( MINUS.id.endsWith( id ) ) {
            return MINUS;
        }

        if ( XOR.id.endsWith( id ) ) {
            return XOR;
        }

        return null;
    }
    
}
