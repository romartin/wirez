package org.wirez.client.widgets.explorer.tree;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.uberfire.client.mvp.UberView;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.processing.visitor.AbstractChildrenVisitorCallback;
import org.wirez.core.api.graph.processing.visitor.ChildrenVisitorCallback;
import org.wirez.core.api.graph.processing.visitor.Visitor;
import org.wirez.core.api.graph.processing.visitor.VisitorPolicy;
import org.wirez.core.api.graph.processing.visitor.tree.TreeWalkChildrenVisitor;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.listener.AbstractCanvasModelListener;
import org.wirez.core.client.canvas.listener.CanvasModelListener;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.*;
import java.util.logging.Logger;

// TODO: Use incremental updates, do not visit whole graph on each model update.
@Dependent
public class TreeExplorer implements IsWidget {
    
    private static Logger LOGGER = Logger.getLogger("org.wirez.client.widgets.explorer.tree.TreeExplorer");

    public interface View extends UberView<TreeExplorer> {

        View addItem(final String itemText);

        View addItem(final String itemText, int... parentIdx);

        View removeItem(final int index);

        View removeItem(final int index, int... parentIdx);
        
        View clear();
    }

    TreeWalkChildrenVisitor visitor;
    View view;

    private CanvasModelListener canvasListener;

    @Inject
    public TreeExplorer(final TreeWalkChildrenVisitor visitor, 
                        final View view) {
        this.visitor = visitor;
        this.view = view;
    }

    @PostConstruct
    public void init() {
        view.init(this);
    }
    
    public void show(final CanvasHandler canvasHandler) {
        assert canvasHandler != null;
        addCanvasListener(canvasHandler);
        doShow(canvasHandler.getDiagram().getGraph());
    }

    private void doShow(final Graph<?, Node> graph) {
        assert graph != null;

        clear();

        visitor.visit(graph, new AbstractChildrenVisitorCallback() {

            final Map<String, Integer> parents = new LinkedHashMap<String, Integer>();
            final Map<Integer, List<String>> indexes = new LinkedHashMap<Integer, List<String>>();

            @Override
            public void visitGraph(Graph<?, Node> graph) {
                super.visitGraph(graph);
            }

            @Override
            public void visitNode(final Node node) {
                super.visitNode(node);
                parents.put(node.getUUID(), 0);
                // indexes.get(0).add(node.getUUID());
                view.addItem(node.getUUID());
            }

            @Override
            public void visitChildNode(final Node parent, Node child) {
                super.visitChildNode(parent, child);
                final int parentIdx = parents.get(parent.getUUID());
                parents.put( child.getUUID(), parentIdx + 1 );
                // indexes.get(parentIdx).add(child.getUUID());
                view.addItem(child.getUUID(), parentIdx);
            }

        }, VisitorPolicy.VISIT_EDGE_BEFORE_TARGET_NODE);
    }
    
    public void clear() {
        view.clear();
    }

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
