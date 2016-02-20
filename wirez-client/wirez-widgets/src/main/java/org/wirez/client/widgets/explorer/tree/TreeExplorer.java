package org.wirez.client.widgets.explorer.tree;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.uberfire.client.mvp.UberView;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.defaults.Name;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.processing.visitor.AbstractChildrenVisitorCallback;
import org.wirez.core.api.graph.processing.visitor.VisitorPolicy;
import org.wirez.core.api.graph.processing.visitor.tree.TreeWalkChildrenVisitor;
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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
    TreeWalkChildrenVisitor visitor;
    View view;

    private CanvasHandler canvasHandler;
    private CanvasModelListener canvasListener;

    @Inject
    public TreeExplorer(final ClientDefinitionServices clientDefinitionServices,
                        final DefinitionManager definitionManager,
                        final TreeWalkChildrenVisitor visitor, 
                        final View view) {
        this.definitionManager = definitionManager;
        this.clientDefinitionServices = clientDefinitionServices;
        this.visitor = visitor;
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

    private void doShow(final Graph<?, Node> graph) {
        assert graph != null;

        clear();

        final Canvas canvas = canvasHandler.getCanvas();
        
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
                
                List<String> parentIdxList = indexes.get(0);
                if ( null == parentIdxList ) {
                    parentIdxList = new ArrayList<String>();
                    indexes.put(0, parentIdxList);
                }
                parentIdxList.add(node.getUUID());
                view.addItem(node.getUUID(), getItemText(node));
            }

            @Override
            public void visitChildNode(final Node parent, Node child) {
                super.visitChildNode(parent, child);
                
                String parentUUID = parent.getUUID();
                int parentIdx = parents.get(parentUUID);
                parents.put( child.getUUID(), parentIdx + 1 );

                List<String> parentIdxList = indexes.get(parentIdx);
                if ( null == parentIdxList ) {
                    parentIdxList = new ArrayList<String>();
                    indexes.put(parentIdx, parentIdxList);
                }
                
                parentIdxList.add(child.getUUID());
                
                // TODO: Calculate parents recursively.
                view.addItem(child.getUUID(), getItemText(child), parentIdx);
            }

        }, VisitorPolicy.VISIT_EDGE_BEFORE_TARGET_NODE);
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
