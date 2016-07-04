package org.wirez.core.definition.adapter.binding;

public interface HasInheritance {

    /**
     * Returns the definition's base type identifier for a given type, if any.
     */
    String getBaseType( Class<?> type );

    String[] getTypes( String baseType );

}
