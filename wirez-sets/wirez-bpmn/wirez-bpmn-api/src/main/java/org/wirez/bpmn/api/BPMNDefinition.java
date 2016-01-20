package org.wirez.bpmn.api;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.uberfire.commons.validation.PortablePreconditions;
import org.wirez.core.api.definition.Definition;

import java.util.Set;

public abstract class BPMNDefinition implements Definition {

    private final String category;
    private final String title;
    private final String description;
    private final Set<String> labels;

    public BPMNDefinition(@MapsTo("category") String category,
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
