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

import com.ait.lienzo.client.core.shape.MultiPath;
import org.wirez.client.shapes.proxy.icon.ICONS;
import org.wirez.client.shapes.view.IconsBuilder;

public class IconGlyph extends AbstractGlyph {

    private static final double WIDTH = 50;
    private static final double HEIGHT = 50;

    public IconGlyph( final ICONS icon ) {
        this( icon, WIDTH, HEIGHT, "#000000" );
    }
    
    public IconGlyph(final ICONS icon,
                     final double width,
                     final double height,
                     final String color ) {
        
        super( width, height );
        
        build( icon, width, height, color );
    }
    
    
    private void build(final ICONS icon,
                       final double width, 
                       final double height, 
                       final String color) {
        
        group.removeAll();
        
        final MultiPath path = IconsBuilder.build( new MultiPath(), 
                icon, 
                0,  
                0, 
                width, 
                height, 
                color, 
                5 );
        
        group.add( path );
    }

}
