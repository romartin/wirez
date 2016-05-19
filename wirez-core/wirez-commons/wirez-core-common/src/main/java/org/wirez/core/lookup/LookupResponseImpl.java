package org.wirez.core.lookup;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;

import java.util.List;

@Portable
public final class LookupResponseImpl<T> implements LookupManager.LookupResponse<T> {
    
    private final List<T> results;
    private final int total;
    private final boolean hasNextPage;
    private final String lookupCriteria;
    private final int page;
    private final int pageSize;
    
    public LookupResponseImpl(@MapsTo("results") List<T> results,
                              @MapsTo("total")  int total,
                              @MapsTo("hasNextPage")  boolean hasNextPage,
                              @MapsTo("lookupCriteria")  String lookupCriteria,
                              @MapsTo("page")  int page,
                              @MapsTo("pageSize")  int pageSize) {
        this.results = results;
        this.total = total;
        this.hasNextPage = hasNextPage;
        this.lookupCriteria = lookupCriteria;
        this.page = page;
        this.pageSize = pageSize;
    }

    @Override
    public List<T> getResults() {
        return results;
    }

    @Override
    public int getTotal() {
        return total;
    }

    @Override
    public boolean hasNextPage() {
        return hasNextPage;
    }

    @Override
    public String getLookupCriteria() {
        return lookupCriteria;
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }
}
