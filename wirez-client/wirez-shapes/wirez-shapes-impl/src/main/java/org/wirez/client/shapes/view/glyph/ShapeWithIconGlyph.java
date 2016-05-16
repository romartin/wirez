/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
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

package org.wirez.client.shapes.view.glyph;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.MultiPath;
import org.wirez.client.shapes.proxy.icon.ICONS;
import org.wirez.client.shapes.view.IconsBuilder;
import org.wirez.core.client.shape.view.ShapeGlyph;

public class ShapeWithIconGlyph extends AbstractGlyph {

    private static final String BLACK = "#000000";

    private final ShapeGlyph<Group> shapeGlyph;
    private final ICONS icon;
    
    public ShapeWithIconGlyph(final double width,
                              final double height, 
                              final ShapeGlyph<Group> shapeGlyph, 
                              final ICONS icon) {
        super(width, height);
        this.shapeGlyph = shapeGlyph;
        this.icon = icon;
        build();
    }

    private void build() {

        group.removeAll();
        
        group.add( shapeGlyph.getGroup() );
        
        final MultiPath path = IconsBuilder.build( new MultiPath(),
                ICONS.PLUS,
                width / 4,
                height / 4,
                width / 2,
                height / 2,
                BLACK,
                3 );

        group.add(path);
    }

}
