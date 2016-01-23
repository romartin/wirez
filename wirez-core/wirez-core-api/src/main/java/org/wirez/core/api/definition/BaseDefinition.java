package org.wirez.core.api.definition;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.uberfire.commons.validation.PortablePreconditions;

import java.util.Set;

public abstract class BaseDefinition implements Definition {

    protected final String category;
    protected final String title;
    protected final String description;
    protected final Set<String> labels;

    public BaseDefinition(@MapsTo("category") String category,
                          @MapsTo("title") String title,
                          @MapsTo("description") String description,
                          @MapsTo("labels") Set<String> labels) {
        this.category = PortablePreconditions.checkNotNull("category",
                category);
        this.title = PortablePreconditions.checkNotNull("title",
                title);
        this.description = PortablePreconditions.checkNotNull("description",
                description);
        this.labels = PortablePreconditions.checkNotNull("labels",
                labels);
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Set<String> getLabels() {
        return labels;
    }
}
