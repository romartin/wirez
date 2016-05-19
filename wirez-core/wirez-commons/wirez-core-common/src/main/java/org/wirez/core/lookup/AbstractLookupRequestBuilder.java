package org.wirez.core.lookup;

import java.util.Set;

public abstract class AbstractLookupRequestBuilder<T> {

    protected int page = 0;
    protected int pageSize = 10;

    public T page( final int page ) {
        this.page = page;
        return (T) this;
    }

    public T pageSize( final int pageSize ) {
        this.pageSize = pageSize;
        return (T) this;
    }

    protected String fromSet( final Set<String> set ) {

        if ( null != set && !set.isEmpty() ) {

            final StringBuilder builder = new StringBuilder( "[" );
            int x = 0;
            for ( final String s : set ) {
                builder.append( s );
                if ( x < ( set.size() - 1 ) ) {
                    builder.append( "," );
                }
                x++;
            }

            return builder.append( "]" ).toString();

        }

        return null;
    }

}
