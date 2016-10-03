package org.kie.workbench.common.stunner.core.lookup;

import java.util.List;

public interface LookupManager<T, R extends LookupManager.LookupRequest> {

    /**
     * Looks-up for entities using some criteria.
     * @param request The lookup request constraints. Depends on each implementation.
     * @return List of resulting entities from the lookup result. Depends on each implementation.
     */
    LookupResponse<T> lookup( R request );

    /**
     * <p>The request criteria for a lookup operation.</p>
     */
    interface LookupRequest {

        /**
         * <p>The lookup string criteria.</p>
         * @return The lookup pattern.
         */
        String getCriteria();

       /**
         * <p>The page number for the lookup cursor.</p>
         * @return The page for the returned results.
         */
        int getPage();

        /**
         * <p>The number of items for each page.</p>
         * @return The page size.
         */
        int getPageSize();
    }

    /**
     * <p>The response values for a lookup operation.</p>
     * @param <T> The entity type.
     */
    interface LookupResponse<T> {
        /**
         * <p>The entities resulting from the lookup operation.</p>
         * @return The entities resulting from the lookup operation.
         */
        List<T> getResults();

        /**
         * <p>The total entities count.</p>
         * @return
         *  <p>By convention, if the implementation class is not able to get the row count, this method should return <code>-1</code>.</p>
         *  <p>Otherwise, returns lookup results count for this entity type.</p>
         */
        int getTotal();

        /**
         * <p>Indicates if there are more results (next pages).</p>
         * <p>If the service provider implementation class is not able to return a value for <code>getTotal</code>, this method can be used to find out if there are more pages.</p>
         * @return Indicates if there are more results (next pages).
         */
        boolean hasNextPage();

        /**
         * <p>The lookup criteria string.</p>
         * @return The lookup pattern.
         */
        String getLookupCriteria();

        /**
         * <p>The page number for the lookup cursor.</p>
         * <p>IMPORTANT NOTE: Page number starts with value <code>1</code>.</p>
         * @return The page for the returned results.
         */
        int getPage();

        /**
         * <p>The number of items for each page.</p>
         * @return The page size.
         */
        int getPageSize();
    }
}
