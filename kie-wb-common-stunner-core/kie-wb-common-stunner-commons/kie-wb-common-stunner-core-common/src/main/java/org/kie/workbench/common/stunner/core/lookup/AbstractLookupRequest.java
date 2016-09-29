package org.kie.workbench.common.stunner.core.lookup;

import org.kie.workbench.common.stunner.core.lookup.LookupManager;

public abstract class AbstractLookupRequest implements LookupManager.LookupRequest {
    
    private final String criteria;
    private final int page;
    private final int pageSize;

    public AbstractLookupRequest(final String criteria, 
                                 final int page, 
                                 final int pageSize) {
        this.criteria = criteria;
        this.page = page;
        this.pageSize = pageSize;
    }

    @Override
    public String getCriteria() {
        return criteria;
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
