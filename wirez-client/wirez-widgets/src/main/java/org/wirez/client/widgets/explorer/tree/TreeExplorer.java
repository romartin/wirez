package org.wirez.client.widgets.explorer.tree;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.uberfire.client.mvp.UberView;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.defaults.Name;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.Child;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.graph.processing.traverse.content.AllEdgesTraverseCallback;
import org.wirez.core.api.graph.processing.traverse.content.AllEdgesTraverseProcessor;
import org.wirez.core.api.graph.processing.traverse.content.ChildrenTraverseProcessor;
import org.wirez.core.api.graph.processing.traverse.content.ContentTraverseCallback;
import org.wirez.core.api.util.ElementUtils;
import org.wirez.core.client.Shape;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.control.SelectionManager;
import org.wirez.core.client.canvas.listener.AbstractCanvasModelListener;
import org.wirez.core.client.canvas.listener.CanvasModelListener;
import org.wirez.core.client.service.ClientDefinitionServices;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO: Use incremental updates, do not visit whole graph on each model update.
@Dependent
public class TreeExplorer implements IsWidget {
    
    private static Logger LOGGER = Logger.getLogger("org.wirez.client.widgets.explorer.tree.TreeExplorer");

    public interface View extends UberView<TreeExplorer> {

        View addItem(final String uuid, final String itemText);

        View addItem(final String uuid, final String itemText, int... parentIdx);

        View removeItem(final int index);

        View removeItem(final int index, int... parentIdx);
        
        View clear();
    }
    
    ClientDefinitionServices clientDefinitionServices;
    DefinitionManager definitionManager;
    ChildrenTraverseProcessor childrenTraverseProcessor;
    View view;

    private CanvasHandler canvasHandler;
    private CanvasModelListener canvasListener;

    @Inject
    public TreeExplorer(final ClientDefinitionServices clientDefinitionServices,
                        final DefinitionManager definitionManager,
                        final ChildrenTraverseProcessor childrenTraverseProcessor,
                        final View view) {
        this.definitionManager = definitionManager;
        this.clientDefinitionServices = clientDefinitionServices;
        this.childrenTraverseProcessor = childrenTraverseProcessor;
        this.view = view;
    }

    @PostConstruct
    public void init() {
        view.init(this);
    }
    
    public void show(final CanvasHandler canvasHandler) {
        this.canvasHandler = canvasHandler;
        assert canvasHandler != null;
        addCanvasListener(canvasHandler);
        doShow(canvasHandler.getDiagram().getGraph());
    }

    private void doShow(final Graph<org.wirez.core.api.graph.content.view.View, Node<org.wirez.core.api.graph.content.view.View, Edge>> graph) {
        traverseChildrenEdges(graph);
    }

    private void traverseChildrenEdges(final Graph<org.wirez.core.api.graph.content.view.View, Node<org.wirez.core.api.graph.content.view.View, Edge>> graph) {
        assert graph != null;

        clear();

        childrenTraverseProcessor.traverse(graph, new ContentTraverseCallback<Child, Node<org.wirez.core.api.graph.content.view.View, Edge>, Edge<Child, Node>>() {

            final Stack<Integer> idxStack = new Stack<Integer>();
            Node parent = null;
            
            @Override
            public void traverse(final Edge<Child, Node> edge) {
                final Node newParent = edge.getSourceNode();
                assert newParent != null;
                
                if ( null == parent || ( !parent.equals(newParent) ) ) {
                    idxStack.push(-1);
                } 
                
                this.parent = edge.getSourceNode();
            }

            @Override
            public void traverseView(final Graph<org.wirez.core.api.graph.content.view.View, Node<org.wirez.core.api.graph.content.view.View, Edge>> graph) {
                idxStack.clear();
                idxStack.push(-1);
            }

            @Override
            public void traverseView(final Node<org.wirez.core.api.graph.content.view.View, Edge> node) {
                LOGGER.log(Level.INFO, " Start of Traverse View Node " + node.getUUID());

                inc(idxStack);
                
                if ( null == parent ) {

                    LOGGER.log(Level.INFO, " Traverse for View Node " + node.getUUID() + " with no parent");

                    view.addItem(node.getUUID(), getItemText(node));


                } else {
                    
                    final String parentUUID = parent.getUUID();

                    LOGGER.log(Level.INFO, " Traverse for View Node " + node.getUUID() + " with parent " + parentUUID);

                    view.addItem(node.getUUID(), getItemText(node), getParentsIds(idxStack));

                }

                LOGGER.log(Level.INFO, " End of Traverse View Node " + node.getUUID());
            }

            @Override
            public void traverseCompleted() {

            }
        });

    }
    
    private void inc(final Stack<Integer> stack) {
        final Integer currentIdx = stack.pop();
        stack.push(currentIdx + 1);
    }
    
    private int[] getParentsIds(final Stack<Integer> stack) {
        final int parentsLength = stack.size();
        List<Integer> result = new LinkedList<>();
        for ( int x = 0; x < parentsLength; x++) {
            result.add( stack.get(x) );
        }
        
        if ( !result.isEmpty() ) {
            final int[] resultArray = new int[result.size()];
            for (int x = 0; x < result.size(); x++) {
                resultArray[x] = result.get(x);
            }
            return resultArray;
        }
        
        return new int[] { };
    }
    
    public void clear() {
        view.clear();
    }
    
    void onSelect(final String uuid) {
        selectShape(canvasHandler.getCanvas(), uuid);
    }

    private String getItemText(final Node item) {
        final String uuid = item.getUUID();
        final Property property = ElementUtils.getProperty(item, Name.ID);
        final String name= (String) definitionManager.getPropertyAdapter(property).getValue(property);
        return   ( name != null ? name : "- No name -" ) + " [" + uuid + "]";
    }

    
    private void selectShape(final Canvas canvas, final String uuid) {
        SelectionManager<Shape> selectionManager = canvas instanceof SelectionManager ?
                (SelectionManager<Shape>) (canvas) : null;

        if ( null != selectionManager ) {
            selectionManager.clearSelection();
            final Shape shape = canvas.getShape(uuid);
            selectionManager.select(shape);
        }
    }

    // TODO: Fix - Several calls here for a single command...
    private void addCanvasListener(final CanvasHandler canvasHandler) {
        removeCanvasListener();

        canvasListener = new AbstractCanvasModelListener(canvasHandler) {
            @Override
            public void onElementAdded(final Element element) {
                super.onElementAdded(element);
                doShow(getGraph());
            }

            @Override
            public void onElementModified(final Element _element) {
                super.onElementModified(_element);
                // doShow(getGraph());
            }

            @Override
            public void onElementDeleted(final Element _element) {
                final String _elementUUID = _element.getUUID();
                doShow(getGraph());
            }

            @Override
            public void onClear() {
                clear();
            }
            
            private Graph getGraph() {
                return canvasHandler.getDiagram().getGraph();
            }
        };
        
        canvasHandler.addListener(canvasListener);
    }

    private void removeCanvasListener() {
        if (canvasListener != null) {
            canvasListener.detach();
            canvasListener = null;
        }
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }

}
