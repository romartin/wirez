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
import org.wirez.core.api.graph.processing.traverse.content.AbstractContentTraverseCallback;
import org.wirez.core.api.graph.processing.traverse.content.ChildrenTraverseProcessor;
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
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO: Use incremental updates, do not visit whole graph on each model update.
@Dependent
public class TreeExplorer implements IsWidget {
    
    private static Logger LOGGER = Logger.getLogger("org.wirez.client.widgets.explorer.tree.TreeExplorer");

    public interface View extends UberView<TreeExplorer> {

        View addItem(String uuid, IsWidget itemView, boolean state);

        View addItem(String uuid, IsWidget itemView, boolean state, int... parentIdx);

        View removeItem(int index);

        View removeItem(int index, int... parentIdx);
        
        View clear();
    }
    
    ClientDefinitionServices clientDefinitionServices;
    DefinitionManager definitionManager;
    ChildrenTraverseProcessor childrenTraverseProcessor;
    Instance<TreeExplorerItem> treeExplorerItemInstances;
    View view;

    private CanvasHandler canvasHandler;
    private CanvasModelListener canvasListener;

    @Inject
    public TreeExplorer(final ClientDefinitionServices clientDefinitionServices,
                        final DefinitionManager definitionManager,
                        final ChildrenTraverseProcessor childrenTraverseProcessor,
                        final Instance<TreeExplorerItem> treeExplorerItemInstances,
                        final View view) {
        this.definitionManager = definitionManager;
        this.clientDefinitionServices = clientDefinitionServices;
        this.childrenTraverseProcessor = childrenTraverseProcessor;
        this.treeExplorerItemInstances = treeExplorerItemInstances;
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
        traverseChildrenEdges(graph, true);
    }

    private void traverseChildrenEdges(final Graph<org.wirez.core.api.graph.content.view.View, Node<org.wirez.core.api.graph.content.view.View, Edge>> graph,
                                       final boolean expand) {
        assert graph != null;

        clear();

        childrenTraverseProcessor.traverse(graph, new AbstractContentTraverseCallback<Child, Node<org.wirez.core.api.graph.content.view.View, Edge>, Edge<Child, Node>>() {

            Node parent = null;
            int level = 0;
            final List<Integer> levelIdx = new LinkedList<Integer>();
            
            @Override
            public void startEdgeTraversal(final Edge<Child, Node> edge) {
                super.startEdgeTraversal(edge);
                final Node newParent = edge.getSourceNode();
                assert newParent != null;
                
                if ( null == parent || ( !parent.equals(newParent) ) ) {
                    level++;
                }
                
                this.parent = edge.getSourceNode();
            }

            @Override
            public void endEdgeTraversal(final Edge<Child, Node> edge) {
                super.endEdgeTraversal(edge);

                final Node newParent = edge.getSourceNode();
                assert newParent != null;
                
                if ( !parent.equals(newParent) ) {
                    level--;
                    this.parent = newParent;
                }
            }

            @Override
            public void startGraphTraversal(final Graph<org.wirez.core.api.graph.content.view.View, Node<org.wirez.core.api.graph.content.view.View, Edge>> graph) {
                super.startGraphTraversal(graph);
                levelIdx.clear();
                levelIdx.add(-1);
            }

            @Override
            public void startNodeTraversal(final Node<org.wirez.core.api.graph.content.view.View, Edge> node) {
                super.startNodeTraversal(node);
                // LOGGER.log(Level.FINE, " Start of Traverse View Node " + node.getUUID());

                inc(levelIdx, level);
                
                if ( null == parent ) {

                    // LOGGER.log(Level.FINE, " Traverse for View Node " + node.getUUID() + " with no parent");

                    final TreeExplorerItem item = treeExplorerItemInstances.get();
                    view.addItem(node.getUUID(), item.asWidget(), expand);
                    item.show(node);

                } else {
                    
                    final String parentUUID = parent.getUUID();
                    int[] parentsIdx = getParentsIdx(levelIdx, level);

                    //  LOGGER.log(Level.FINE, " Traverse for View Node " + node.getUUID() + " with parent " + parentUUID 
                    //         + " and parentsIdx=" + parentsIdx + " / level=" + level);

                    final TreeExplorerItem item = treeExplorerItemInstances.get();
                    view.addItem(node.getUUID(), item.asWidget(), expand, parentsIdx);
                    item.show(node);
                    
                }

                // LOGGER.log(Level.FINE, " End of Traverse View Node " + node.getUUID());
            }
          
        });

    }
    
    private void inc(final List<Integer> levels, final int level) {
        if ( levels.size() < ( level + 1 ) ) {
            levels.add(0);
        } else {
            final int idx = levels.get(level);
            levels.set(level, idx + 1);
        }
    }

    private int[] getParentsIdx(final List<Integer> idxList, final int maxLevel) {
        if ( !idxList.isEmpty() ) {
            final int targetPos = ( idxList.size() - ( idxList.size() - maxLevel ) ) + 1;
            final int[] resultArray = new int[targetPos];
            for (int x = 0; x < targetPos ; x++) {
                resultArray[x] = idxList.get(x);
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
                doShow(getGraph());
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
