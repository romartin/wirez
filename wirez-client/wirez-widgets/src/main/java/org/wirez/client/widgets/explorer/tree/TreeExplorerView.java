package org.wirez.client.widgets.explorer.tree;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

public class TreeExplorerView extends Composite implements TreeExplorer.View {

    interface ViewBinder extends UiBinder<Widget, TreeExplorerView> {

    }

    private static ViewBinder uiBinder = GWT.create( ViewBinder.class );

    @UiField
    Tree tree;
    
    private TreeExplorer presenter;
    
    @Override
    public void init(final TreeExplorer presenter) {
        this.presenter = presenter;
        initWidget( uiBinder.createAndBindUi( this ) );
        tree.addSelectionHandler(selectionEvent -> { 
            final TreeItem item = selectionEvent.getSelectedItem();
            final String uuid = (String) item.getUserObject();
            presenter.onSelect(uuid);
        });

    }

    public TreeExplorer.View addItem(final String uuid, 
                                     final boolean state,
                                     final String itemText) {
        final TreeItem item = buildItem(uuid, itemText);
        tree.addItem(item);
        item.setState(state);
        return this;
    }

    public TreeExplorer.View addItem(final String uuid,
                                     final boolean state,
                                     final String itemText, 
                                     final int... parentsIds) {
        final TreeItem item = buildItem(uuid, itemText);
        final TreeItem parent = getParent(parentsIds);
        parent.addItem(item);
        parent.setState(state);
        item.setState(state);
        return this;
    }
    
    private TreeItem buildItem(final String uuid,
                               final String itemText) {
        final TreeItem item = new TreeItem();
        item.setText(itemText);
        item.setUserObject(uuid);
        item.getElement().getStyle().setCursor(Style.Cursor.POINTER);
        return item;
    }

    public TreeExplorer.View removeItem(final int index) {

        final TreeItem item = tree.getItem(index);
        tree.removeItem(item);

        return this;
    }

    public TreeExplorer.View removeItem(final int index, final int... parentsIds) {

        final TreeItem parent = getParent(parentsIds);
        final TreeItem item = parent.getChild(index);
        parent.removeItem(item);

        return this;
    }
    
    
    @Override
    public TreeExplorer.View clear() {
        tree.clear();
        return this;
    }

    private  TreeItem getParent(final int... parentsIds) {
        TreeItem parent = null;
        
        for ( int x = 0; x <  ( parentsIds.length - 1) ; x++) {
            final int parentIdx = parentsIds[x];
            
            if ( null == parent ) {
                parent = tree.getItem(parentIdx);
            } else {
                parent = parent.getChild(parentIdx);
            }
        }
        
        return parent;
    }
    
}
