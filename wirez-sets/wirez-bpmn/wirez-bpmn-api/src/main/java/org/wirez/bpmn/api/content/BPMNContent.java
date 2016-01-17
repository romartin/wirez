/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wirez.bpmn.api.content;

import org.jboss.errai.codegen.meta.HasAnnotations;
import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.uberfire.commons.validation.PortablePreconditions;
import org.wirez.core.api.definition.Content;

import java.util.Set;

public class BPMNContent implements Content {

    private final String category;
    private final String title;
    private final String description;
    private final Set<String> labels;

    public BPMNContent(@MapsTo("category") String category,
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
