package org.wirez.backend.definition.marshall;

import org.jboss.errai.marshalling.server.ServerMarshalling;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.FactoryManager;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.diagram.marshall.DiagramMarshaller;
import org.wirez.core.api.graph.command.GraphCommandManager;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.graph.util.GraphUtils;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;

@Dependent
public class DefaultDiagramMarshaller implements DiagramMarshaller<Diagram, InputStream, String>  {

    DefinitionManager definitionManager;
    GraphUtils graphUtils;
    FactoryManager factoryManager;
    GraphCommandManager graphCommandManager;
    GraphCommandFactory commandFactory;

    @Inject
    public DefaultDiagramMarshaller(DefinitionManager definitionManager, 
                                    GraphUtils graphUtils, 
                                    FactoryManager factoryManager, 
                                    GraphCommandManager graphCommandManager, 
                                    GraphCommandFactory commandFactory) {
        this.definitionManager = definitionManager;
        this.graphUtils = graphUtils;
        this.factoryManager = factoryManager;
        this.graphCommandManager = graphCommandManager;
        this.commandFactory = commandFactory;
    }

    @Override
    public Diagram unmarhsall(InputStream input) throws IOException  {
        Diagram result = (Diagram) ServerMarshalling.fromJSON( input );
        return result;
    }

    @Override
    public String marshall(Diagram diagram) throws IOException {
        String result = ServerMarshalling.toJSON( diagram );
        return result;
    }
    
}
