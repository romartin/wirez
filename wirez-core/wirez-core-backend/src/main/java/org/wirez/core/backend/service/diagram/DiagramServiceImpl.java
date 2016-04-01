package org.wirez.core.backend.service.diagram;

import org.apache.commons.io.FileUtils;
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
import org.wirez.core.api.FactoryManager;
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
import org.wirez.core.api.service.diagram.*;
import org.wirez.core.api.util.UUID;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.net.URI;
import java.util.*;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;
import static org.uberfire.java.nio.file.Files.walkFileTree;

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
    FactoryManager factoryManager;
    
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

            try {

                final Collection<DiagramRepresentation> result = new ArrayList<DiagramRepresentation>();
    
                if (ioService.exists(root)) {
                    walkFileTree(checkNotNull("root", root),
                            new SimpleFileVisitor<Path>() {
                                @Override
                                public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
                                        checkNotNull("file", file);
                                    checkNotNull("attrs", attrs);
                                    
                                    String name = file.getFileName().toString();
                                    if ( isAccepted( name ) ) {
                                        
                                        // TODO: Do not load & process the whole bpmn file. Just the necessary to build the
                                        // portable diagram representation.
                                        Diagram diagram = doLoad( file );
    
                                        if ( null != diagram ) {
    
                                            Settings settings = diagram.getSettings();
                                            DiagramRepresentation representation =
                                                    new DiagramRepresentationImpl(diagram.getUUID(), 
                                                            settings.getTitle(),
                                                            settings.getDefinitionSetId(),
                                                            settings.getShapeSetId(), 
                                                            settings.getPath());
                                            
                                            result.add(representation);
                                        }
                                        
                                    }
                                        
                                    return FileVisitResult.CONTINUE;
                                }
                            });
                }
                
                    
                return new DiagramsServiceResponseImpl(ResponseStatus.SUCCESS, result);
                
            } catch (Exception e) {
                LOG.error("Error during diagram search operation.", e);
                throw e;
            }

        } else {
            
            throw new UnsupportedOperationException("Search with criteria is not supported yet.");
            
        }

    }

    @Override
    public DiagramServiceResponse create(DiagramServiceCreateRequest request) {
        final String defSetId = request.getDefinitionSetId();
        final String shapeSetId = request.getShapeSetId();
        final String title = request.getTitle();

        final Graph graph = factoryManager.graph( UUID.uuid(), defSetId );
        final Settings diagramSettings = new SettingsImpl(title, defSetId, shapeSetId);
        
        // TODO: Externalize
        diagramSettings.setPath(buildDiagramPath());
        final Diagram diagram = new DiagramImpl( UUID.uuid(), graph, diagramSettings );

        diagramRegistry.add(diagram);

        return new DiagramServiceResponseImpl(ResponseStatus.SUCCESS, diagram);
        
    }

    @Override
    public DiagramServiceResponse load(DiagramServiceLoadRequest request) {

        Diagram diagram = null;
        try {
                
            final String fileName = request.getPath();
            
            diagram = doLoad( fileName );

        } catch (Exception e) {

            LOG.error("Error during diagram load operation.", e);
            throw e;
            
        }
        
        if ( null != diagram ) {
            
            return new DiagramServiceResponseImpl(ResponseStatus.SUCCESS, diagram);
            
        } else {
            
            return new DiagramServiceResponseImpl(ResponseStatus.FAIL, null);
            
        }

    }

    @Override
    public ServiceResponse save(DiagramServiceSaveRequest request) {

        try {
        
            final Diagram diagram = request.getDiagram();
    
            // Update registry, if necessary.
            // Disabled while coding
            /*if (diagramRegistry.contains(diagram)) {
                diagramRegistry.update(diagram);
            } else {
                diagramRegistry.add(diagram);
            }*/
            
            if ( null == diagram ) {
                doLog("Diagram is null!");
            } else {
                Graph graph = diagram.getGraph();
                if ( null == graph ) {
                    doLog("Graph is null!");
                } else {
    
                    String path = diagram.getSettings().getPath();
                    if ( path == null || path.trim().length() == 0 ) {
                        path = buildDiagramPath(diagram);
                    }
                    
                    DefinitionSetServices services = getServices( path );
                    
                    if ( null == services ) {
                        throw new RuntimeException("No service for diagram [" + path + "]");
                    } else {
                        
                        
                        try {
                            
                            ioService.startBatch(fileSystem);
    
                            doLog("Saving diagram with UUID [" + diagram.getUUID() + "] using path [" + path + "]");
                            DiagramMarshaller marshaller = services.getDiagramMarshaller();
                            String result = marshaller.marshall( diagram );
                            doLog("Result" + result);
                            Path defPath = getDiagramsPath().resolve( path );
                            ioService.write(defPath, result);
                            
                        } catch (Exception e) {
                            
                            LOG.error("Error saving diagram.", e);
                            
                        } finally {
                            
                            ioService.endBatch();
                        }
                       
                    }
                    
                }
            }
    
            return new ServiceResponseImpl(ResponseStatus.SUCCESS);

        } catch (Exception e) {

            LOG.error("Error during diagram save operation.", e);
            throw e;
            
        }
    }
    
    private void doLog(String m) {
        System.out.println(m);
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

        Diagram diagram = null;
        // TODO: Forget cache while development phase...
        if ( false) {
            diagram = getDiagramInRegistry( fileName );
        }

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

    private String buildDiagramPath() {
        return UUID.uuid() + ".bpmn";
    }

    private String buildDiagramPath(Diagram diagram) {
        return diagram.getUUID() + ".bpmn";
    }
    
}
