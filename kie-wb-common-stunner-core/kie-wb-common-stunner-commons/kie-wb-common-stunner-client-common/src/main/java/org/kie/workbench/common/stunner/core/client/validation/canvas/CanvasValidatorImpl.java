package org.kie.workbench.common.stunner.core.client.validation.canvas;

import org.kie.workbench.common.stunner.core.client.canvas.Canvas;
import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.kie.workbench.common.stunner.core.client.shape.Shape;
import org.kie.workbench.common.stunner.core.client.shape.ShapeState;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Element;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.rule.graph.GraphRulesManager;
import org.kie.workbench.common.stunner.core.validation.AbstractValidator;
import org.kie.workbench.common.stunner.core.validation.graph.AbstractGraphValidatorCallback;
import org.kie.workbench.common.stunner.core.validation.graph.GraphValidationViolation;
import org.kie.workbench.common.stunner.core.validation.graph.GraphValidator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.LinkedList;

@ApplicationScoped
public class CanvasValidatorImpl
        extends AbstractValidator<CanvasHandler, CanvasValidatorCallback>
        implements CanvasValidator {

    GraphValidator graphValidator;

    private GraphRulesManager rulesManager = null;

    @Inject
    public CanvasValidatorImpl( final GraphValidator graphValidator ) {
        this.graphValidator = graphValidator;
    }

    @Override
    public CanvasValidator withRulesManager( final GraphRulesManager rulesManager ) {
        this.rulesManager = rulesManager;
        return this;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public void validate( final CanvasHandler canvasHandler,
                          final CanvasValidatorCallback callback ) {

        final Graph graph = canvasHandler.getDiagram().getGraph();

        graphValidator
                .withRulesManager( rulesManager )
                .validate( graph, new AbstractGraphValidatorCallback() {

                    @Override
                    public void afterValidateNode( final Node node,
                                                   final Iterable<GraphValidationViolation> violations ) {
                        super.afterValidateNode( node, violations );

                        checkViolations( canvasHandler, node, violations );

                    }

                    @Override
                    public void afterValidateEdge( final Edge edge,
                                                   final Iterable<GraphValidationViolation> violations ) {
                        super.afterValidateEdge( edge, violations );

                        checkViolations( canvasHandler, edge, violations );

                    }

                    @Override
                    public void onSuccess() {

                        callback.onSuccess();

                    }

                    @Override
                    public void onFail( final Iterable<GraphValidationViolation> violations ) {

                        final Collection<CanvasValidationViolation> canvasValidationViolations = new LinkedList<CanvasValidationViolation>();

                        for ( final GraphValidationViolation violation : violations ) {

                            final CanvasValidationViolation canvasValidationViolation =
                                    new CanvasValidationViolationImpl( canvasHandler, violation );

                            canvasValidationViolations.add( canvasValidationViolation );

                        }

                        callback.onFail( canvasValidationViolations );

                    }

                }  );



    }

    private void checkViolations( final CanvasHandler canvasHandler,
                                  final Element element,
                                  final Iterable<GraphValidationViolation> violations ) {

        if ( hasViolations( violations ) ) {

            final Canvas canvas = canvasHandler.getCanvas();

            final Shape shape = getShape( canvasHandler, element.getUUID() );

            shape.applyState( ShapeState.INVALID );

            canvas.draw();

        }

    }

    private Shape getShape( final CanvasHandler canvasHandler,
                           final String uuid ) {

        return canvasHandler.getCanvas().getShape( uuid );

    }

    private boolean hasViolations( final Iterable<GraphValidationViolation> violations ) {
        return violations != null && violations.iterator().hasNext();
    }

}
