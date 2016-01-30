package org.wirez.core.backend.service.diagram;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.errai.bus.server.annotations.Service;
import org.jboss.errai.bus.server.api.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uberfire.backend.server.util.Paths;
import org.uberfire.io.IOService;
import org.uberfire.java.nio.IOException;
import org.uberfire.java.nio.file.*;
import org.uberfire.java.nio.file.attribute.BasicFileAttributes;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.diagram.DiagramImpl;
import org.wirez.core.api.diagram.Settings;
import org.wirez.core.api.diagram.SettingsImpl;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.registry.DiagramRegistry;
import org.wirez.core.api.service.DefinitionSetServices;
import org.wirez.core.api.service.ResponseStatus;
import org.wirez.core.api.service.ServiceResponse;
import org.wirez.core.api.service.ServiceResponseImpl;
import org.wirez.core.api.service.definition.DefinitionService;
import org.wirez.core.api.service.definition.DefinitionSetServiceResponse;
import org.wirez.core.api.service.diagram.*;
import org.wirez.core.api.service.event.DiagramCreatedEvent;
import org.wirez.core.api.service.event.DiagramLoadedEvent;
import org.wirez.core.api.util.UUID;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import java.io.*;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;
import static org.uberfire.java.nio.file.Files.readAllBytes;
import static org.uberfire.java.nio.file.Files.walkFileTree;

/*
    See DataSetDefRegistryCDI
 */

@Service
@ApplicationScoped
public class DiagramServiceImpl implements DiagramService {

    private static final Logger LOG = LoggerFactory.getLogger(DiagramServiceImpl.class);
    
    public static final String VFS_ROOT_PATH = "default://wirez";
    public static final String VFS_DIAGRAMS_PATH = "diagrams";
    public static final String APP_DIAGRAMS_PATH = "WEB-INF/diagrams";
    
    @Inject
    DefinitionManager definitionManager;
    
    @Inject
    DefinitionService definitionService;
    
    @Inject
    DiagramRegistry<Diagram> diagramRegistry;

    @Inject
    Instance<DefinitionSetServices> definitionSetServiceInstances;

    @Inject
    @Named("ioStrategy")
    IOService ioService;

    private FileSystem fileSystem;
    private Path root;

    private final Collection<DefinitionSetServices> definitionSetServices = new LinkedList<>();
    
    @PostConstruct
    public void init() {
        for (DefinitionSetServices definitionSetService : definitionSetServiceInstances) {
            definitionSetServices.add(definitionSetService);
        }
        initFileSystem();
    }

    @Override
    public ServiceResponse registerAppDefinitions() {
        deployAppDiagrams( APP_DIAGRAMS_PATH );
        return new ServiceResponseImpl(ResponseStatus.SUCCESS);
    }

    // TODO: Search query & pagination.
    @Override
    public DiagramsServiceResponse search(DiagramServiceSearchRequest request) {
        
        String query = request.getSearchQuery();
        boolean isAll = query == null || query.trim().length() == 0;
        
        if ( isAll ) {

            final Collection<Diagram> result = new ArrayList<Diagram>();

            if (ioService.exists(root)) {
                walkFileTree(checkNotNull("root", root),
                        new SimpleFileVisitor<Path>() {
                            @Override
                            public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
                                    checkNotNull("file", file);
                                checkNotNull("attrs", attrs);
                                
                                String name = file.getFileName().toString();
                                if ( isAccepted( name ) ) {
                                    
                                    Diagram diagram = doLoad( file );

                                    if ( null != diagram ) {
                                        result.add(diagram);
                                    }
                                    
                                }
                                    
                                return FileVisitResult.CONTINUE;
                            }
                        });
            }
            
            return new DiagramsServiceResponseImpl(ResponseStatus.SUCCESS, result);
            
        } else {
            
            throw new UnsupportedOperationException("Search with criteria is not supported yet.");
            
        }

    }

    @Override
    public DiagramServiceResponse create(DiagramServiceCreateRequest request) {
        final String defSetId = request.getDefinitionSetId();
        final String shapeSetId = request.getShapeSetId();
        final String title = request.getTitle();

        final DefinitionSetServiceResponse definitionSetServiceResponse = definitionService.getDefinitionSet(defSetId);
        final Definition graphDefinition = definitionSetServiceResponse.getGraphElement();
        final String uuid = UUID.uuid();
        final Graph graph = (Graph) definitionService.buildGraphElement(graphDefinition.getId());
        final Settings diagramSettings = new SettingsImpl(title, defSetId, shapeSetId);
        final Diagram diagram = new DiagramImpl( uuid, graph, diagramSettings );

        diagramRegistry.add(diagram);

        return new DiagramServiceResponseImpl(ResponseStatus.SUCCESS, diagram);
        
    }

    @Override
    public DiagramServiceResponse load(DiagramServiceLoadRequest request) {

        final String fileName = request.getPath();
        
        Diagram diagram = doLoad( fileName );
        
        if ( null != diagram ) {
            
            return new DiagramServiceResponseImpl(ResponseStatus.SUCCESS, diagram);
            
        } else {
            
            return new DiagramServiceResponseImpl(ResponseStatus.FAIL, null);
            
        }

    }

    // TODO
    @Override
    public ServiceResponse save(DiagramServiceSaveRequest request) {

        final Diagram diagram = request.getDiagram();

        // Update registry, if necessary.
        if (diagramRegistry.contains(diagram)) {
            diagramRegistry.update(diagram);
        } else {
            diagramRegistry.add(diagram);
        }

        return new ServiceResponseImpl(ResponseStatus.SUCCESS);
    }

    // TODO
    @Override
    public ServiceResponse delete(DiagramServiceDeleteRequest request) {

        final String uuid = request.getUUID();

        final Diagram diagram = diagramRegistry.get(uuid);
        if (null != diagram) {
            diagramRegistry.remove(diagram);
            return new ServiceResponseImpl(ResponseStatus.SUCCESS);
        }

        return new ServiceResponseImpl(ResponseStatus.FAIL);
    }

    private Diagram doLoad(final String fileName) {

        Path path = getDiagramsPath().resolve( fileName );
        return doLoad(path);

    }

    private Diagram doLoad(final Path file) {

        final String fileName = file.getFileName().toString();

        Diagram diagram = getDiagramInRegistry( fileName );

        // If not cached in the registry, load it.
        if ( null == diagram ) {

            // Obtain the concrete definition set service for this kind of diagram.
            DefinitionSetServices services = getServices( fileName );

            if ( null != services ) {

                // Parse and load the diagram.
                final byte[] bytes = ioService.readAllBytes( file );
                final InputStream is = new ByteArrayInputStream(bytes);
                diagram = doLoad( fileName, services, is );
                
                // Add it into registry cache.
                diagramRegistry.add( diagram );
                
                return diagram;
            }

            throw new UnsupportedOperationException( "Diagram format not supported [" + file.getFileName().toString() + "]" );

        }

        return diagram;

    }
    
    private Diagram doLoad(String fileName, DefinitionSetServices services, InputStream is) {

        if ( null != services ) {

            // Parse and load the diagram.
            final DiagramMarshaller marshaller = services.getDiagramMarshaller();
            Diagram diagram = marshaller.unmarhsall( is );
            
            if ( null != diagram ) {
                
                diagram.getSettings().setPath( fileName );
                
                return diagram;
                
            }

        }
        
        return null;

    }
    
    protected void initFileSystem() {
        try {
            fileSystem = ioService.newFileSystem(URI.create(VFS_ROOT_PATH),
                    new HashMap<String, Object>() {{
                        put( "init", Boolean.TRUE );
                        put( "internal", Boolean.TRUE );
                    }});
        } catch ( FileSystemAlreadyExistsException e ) {
            fileSystem = ioService.getFileSystem(URI.create(VFS_ROOT_PATH));
        }
        this.root = fileSystem.getRootDirectories().iterator().next();
    }

    private void deployAppDiagrams(String path) {
        ServletContext servletContext = RpcContext.getHttpSession().getServletContext();

        if ( null != servletContext ) {

            String dir = servletContext.getRealPath( path );
            if (dir != null && new File(dir).exists()) {
                dir = dir.replaceAll("\\\\", "/");
                findAndDeployDiagrams( dir );
            }

        } else {

            LOG.warn("No servlet context available. Cannot deploy the application diagrams.");

        }

    }

    private void findAndDeployDiagrams(String directory) {

        if (!StringUtils.isBlank(directory)) {
            // Look for data sets deploy
            File[] files = new File(directory).listFiles(_deployFilter);
            if (files != null) {
                for (File f : files) {
                    try {

                        String name = f.getName();

                        if ( isAccepted( name) ) {

                            // Register it into VFS storage.
                            registerIntoVFS(f);

                            // Delete file after added into app's vfs.
                            f.delete();
                            
                        }

                    } catch (Exception e) {

                        LOG.error("Error loading the application default diagrams.", e);

                    }
                }
            }
        }

    }

    private void registerIntoVFS(File file) {
        String name = file.getName();
        Path actualPath = getDiagramsPath().resolve( name );
        boolean exists = ioService.exists( actualPath );

        if ( !exists ) {

            ioService.startBatch(fileSystem);

            try {

                String content = FileUtils.readFileToString( file );
                Path diagramPath = getDiagramsPath().resolve( file.getName() );
                ioService.write( diagramPath, content );

            } catch (Exception e) {

                LOG.error("Error registering diagram into app's VFS", e);

            } finally {

                ioService.endBatch();

            }
            
        } else {
            
            LOG.warn("Diagram [" + name + "] already exists on VFS storage. This file should not be longer present here.");
            
        }

    }

    FilenameFilter _deployFilter = new FilenameFilter() {
        public boolean accept(File dir, String name) {
            return true;
        }
    };

    protected Path getDiagramsPath() {
        return root.resolve(VFS_DIAGRAMS_PATH);
    }

    protected Path getTempPath() {
        return root.resolve("tmp");
    }

    protected Path resolveTempPath(String fileName) {
        return getTempPath().resolve(fileName);
    }

    protected org.uberfire.backend.vfs.Path convert(Path path) {
        return Paths.convert(path);
    }

    protected Path convert(org.uberfire.backend.vfs.Path path) {
        return Paths.convert(path);
    }
    
    protected InputStream getInputStream(final String path) {
        return getClass().getClassLoader().getResourceAsStream(path);
    }

    protected Diagram getDiagramInRegistry(final String path) {
        final Collection<Diagram> diagrams = diagramRegistry.getItems();
        if ( null != diagrams ) {
            for (final Diagram diagram : diagrams) {
                final String _path = diagram.getSettings().getPath();
                
                if ( null != _path && _path.equals(path)) {
                    return diagram;
                }

            }
        }
        
        return null;
        
    }

    protected boolean isAccepted(final String path) {
        if (path != null && path.trim().length() > 0) {
            
            for (DefinitionSetServices definitionSetService : definitionSetServices) {
                String ext = definitionSetService.getFileExtension();
                if ( path.toLowerCase().endsWith(ext.toLowerCase()) ) {
                    return true;
                }
            }
            
        }

        return false;
    }
    
    protected DefinitionSetServices getServices(final String path) {
        for (DefinitionSetServices definitionSetService : definitionSetServices) {
            if (definitionSetService.accepts( path )) {
                return definitionSetService;
            }
        }
        
        return null;
    }

    
    
}
