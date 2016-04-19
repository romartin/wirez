package org.wirez.core.backend.diagram.vfs;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.errai.bus.server.api.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uberfire.backend.server.util.Paths;
import org.uberfire.io.IOService;
import org.uberfire.java.nio.IOException;
import org.uberfire.java.nio.file.*;
import org.uberfire.java.nio.file.attribute.BasicFileAttributes;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.DiagramManager;
import org.wirez.core.api.FactoryManager;
import org.wirez.core.api.definition.DefinitionSetServices;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.diagram.marshall.DiagramMarshaller;
import org.wirez.core.api.util.UUID;
import org.wirez.core.backend.Application;
import org.wirez.core.backend.VFS;

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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;
import static org.uberfire.java.nio.file.Files.walkFileTree;

@ApplicationScoped
@VFS
public class VFSDiagramManagerImpl implements DiagramManager<Diagram> {

    private static final Logger LOG = LoggerFactory.getLogger(VFSDiagramManagerImpl.class);
    private static final String VFS_ROOT_PATH = "default://wirez";
    private static final String VFS_DIAGRAMS_PATH = "diagrams";
    private static final String APP_DIAGRAMS_PATH = "WEB-INF/diagrams";

    @Inject
    DefinitionManager definitionManager;

    @Inject
    @Application
    FactoryManager factoryManager;

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

        // Initialize the application's VFS.
        initFileSystem();
        
        // Register packaged diagrams into VFS.
        registerAppDefinitions();
        
    }

    public void registerAppDefinitions() {
        
        deployAppDiagrams( APP_DIAGRAMS_PATH );
        
    }
    
    @Override
    public void update(Diagram diagram) {
        
        save( diagram );
        
    }

    @Override
    public void add(Diagram diagram) {
        
        save( diagram );
        
    }

    @Override
    public boolean contains(Diagram diagram) {
        
        return get( diagram.getUUID() ) != null;
        
    }

    @Override
    public Diagram get(String diagramPath) {

        try {

            return doLoad( diagramPath );

        } catch (Exception e) {

            LOG.error("Error during diagram load operation.", e);
            throw e;

        }
        
    }

    @Override
    public Collection<Diagram> getItems() {
        try {

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

                                    // TODO: Do not load & process the whole bpmn file. Just the necessary to build the
                                    // portable diagram representation.
                                    Diagram diagram = doLoad( file );

                                    if ( null != diagram ) {

                                        result.add( diagram );
                                    }

                                }

                                return FileVisitResult.CONTINUE;
                            }
                        });
            }


            return result;

        } catch (Exception e) {
            LOG.error("Error while obtaining diagrams.", e);
            throw e;
        }
    }

    @Override
    public void remove(Diagram diagram) {
        // TODO
    }
    
    @Override
    public void clear() {
        // TODO
    }

    private void save( Diagram diagram ) {

        try {

            String path = diagram.getSettings().getVFSPath();
            if ( path == null || path.trim().length() == 0 ) {
                path = buildDiagramPath(diagram);
            }

            DefinitionSetServices services = getServices( path );

            if ( null == services ) {
                throw new RuntimeException("No service for diagram [" + path + "]");
            } else {


                try {

                    LOG.info("Saving diagram with UUID [" + diagram.getUUID() + "] using path [" + path + "]");

                    ioService.startBatch(fileSystem);
                    DiagramMarshaller<Diagram, InputStream, String> marshaller = services.getDiagramMarshaller();
                    
                    String result = marshaller.marshall( diagram );
                    LOG.debug("Serialized diagram: " + result);
                    
                    Path defPath = getDiagramsPath().resolve( path );
                    ioService.write(defPath, result);

                } catch (Exception e) {

                    LOG.error("Error while saving diagram.", e);

                } finally {

                    ioService.endBatch();
                }

            }

        } catch (Exception e) {

            LOG.error("Error while saving diagram.", e);
            throw e;

        }
    }

    private Diagram doLoad(final String fileName) {

        Path path = getDiagramsPath().resolve( fileName );
        return doLoad(path);

    }

    private Diagram doLoad(final Path file) {

        final String fileName = file.getFileName().toString();

        // Obtain the concrete definition set service for this kind of diagram.
        DefinitionSetServices services = getServices( fileName );

        if ( null != services ) {

            // Parse and load the diagram.
            final byte[] bytes = ioService.readAllBytes( file );
            final InputStream is = new ByteArrayInputStream(bytes);
            Diagram diagram = doLoad( fileName, services, is );

            return diagram;
        }

        throw new UnsupportedOperationException( "Diagram format not supported [" + file.getFileName().toString() + "]" );

    }

    private Diagram doLoad(String fileName, DefinitionSetServices services, InputStream is) {

        if ( null != services ) {

            // Parse and load the diagram.
            final DiagramMarshaller marshaller = services.getDiagramMarshaller();
            Diagram diagram = marshaller.unmarhsall( is );

            if ( null != diagram ) {

                diagram.getSettings().setVFSPath( fileName );

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
        ServletContext servletContext = RpcContext.getServletRequest().getServletContext();

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
