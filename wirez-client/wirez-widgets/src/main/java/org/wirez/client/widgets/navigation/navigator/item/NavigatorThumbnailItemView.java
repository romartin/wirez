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

package org.wirez.client.widgets.navigation.navigator.item;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import org.gwtbootstrap3.client.shared.event.HideEvent;
import org.gwtbootstrap3.client.shared.event.HideHandler;
import org.gwtbootstrap3.client.shared.event.ShowEvent;
import org.gwtbootstrap3.client.shared.event.ShowHandler;
import org.gwtbootstrap3.client.ui.*;
import org.wirez.client.widgets.navigation.navigator.NavigatorItem;
import org.wirez.client.widgets.navigation.navigator.NavigatorItemView;

import javax.enterprise.context.Dependent;

@Dependent
public class NavigatorThumbnailItemView
        extends Composite implements NavigatorItemView<NavigatorItem> {

    interface ViewBinder extends UiBinder<Widget, NavigatorThumbnailItemView> {

    }

    private static ViewBinder uiBinder = GWT.create( ViewBinder.class );

    @UiField
    FlowPanel mainPanel;

    @UiField
    Panel panel;

    @UiField
    Heading heading;

    @UiField
    ThumbnailLink item;

    @UiField
    Popover popover;

    @UiField
    Image thumbImage;

    NavigatorItem presenter;

    @Override
    public void init(final NavigatorItem presenter) {
        this.presenter = presenter;
        initWidget( uiBinder.createAndBindUi( this ) );
        item.addClickHandler( clickEvent -> presenter.onItemSelected() );
        popover.addShowHandler( showEvent -> panel.getElement().getStyle().setBorderWidth( 3, Style.Unit.PX ) );
        popover.addHideHandler( hideEvent -> panel.getElement().getStyle().setBorderWidth( 1, Style.Unit.PX ) );
    }


    @Override
    public NavigatorThumbnailItemView setUUID( final String uuid ) {
        popover.setContent( uuid );
        return this;
    }

    @Override
    public NavigatorThumbnailItemView setItemTitle( final String title ) {
        heading.setText( title );
        heading.setTitle( title );
        popover.setTitle( title );
        return this;
    }

    @Override
    public NavigatorThumbnailItemView setThumbData( final String thumbData ) {
        thumbImage.setUrl( thumbData );
        return this;
    }

    @Override
    public NavigatorThumbnailItemView setThumbUri( final SafeUri safeUri ) {
        thumbImage.setUrl( safeUri );
        return this;
    }

    @Override
    public NavigatorThumbnailItemView setSize( final int width,
                                              final int height,
                                              final Style.Unit unit ) {
        int p1 = 0;
        int p2 = 0;

        if ( thumbImage.getWidth() >= thumbImage.getHeight() ) {

            p1 = width;
            p2 = thumbImage.getHeight() * width / thumbImage.getWidth();

        } else {

            p1 = height;
            p2 = thumbImage.getWidth() * height / thumbImage.getHeight();

        }

        thumbImage.setPixelSize( p1, p2 );
        // mainPanel.setHeight( height + unit.getType() );

        return this;
    }

}