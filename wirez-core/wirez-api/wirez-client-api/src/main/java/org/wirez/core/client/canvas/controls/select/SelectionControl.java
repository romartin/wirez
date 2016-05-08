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

package org.wirez.core.client.canvas.controls.select;

import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.controls.CanvasRegistationControl;
import org.wirez.core.client.shape.Shape;

import java.util.Collection;

/**
 * Mediator for shape selection operations in a canvas.
 */
public interface SelectionControl<C extends Canvas, S extends Shape> extends CanvasRegistationControl<C, S> {

    SelectionControl<C, S> select(final S item);

    SelectionControl<C, S> deselect(final S item);

    boolean isSelected(final S item);

    Collection<S> getSelectedItems();

    SelectionControl<C, S> clearSelection();

}
