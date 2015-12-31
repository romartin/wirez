/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wirez.core.backend.service;

import com.google.gson.Gson;
import org.jboss.errai.bus.server.annotations.Service;
import org.jboss.errai.marshalling.client.api.Marshaller;
import org.jboss.errai.marshalling.client.api.MarshallerFramework;
import org.jboss.errai.security.shared.api.identity.User;
import org.uberfire.backend.vfs.Path;
import org.uberfire.io.IOService;
import org.uberfire.java.nio.base.options.CommentedOption;
import org.uberfire.java.nio.file.FileSystem;
import org.uberfire.java.nio.file.FileSystemAlreadyExistsException;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.service.GraphVfsServices;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import java.net.URI;
import java.util.HashMap;

import static org.uberfire.backend.server.util.Paths.convert;

@ApplicationScoped
@Service
public class GraphVfsServicesImpl implements GraphVfsServices {

    @Inject
    protected User identity;

    @Inject
    @Named("ioStrategy")
    protected IOService ioService;

    private FileSystem fileSystem;
    private org.uberfire.java.nio.file.Path root;
    
    
    @PostConstruct
    protected void init() {
        initFileSystem();
    }


    protected void initFileSystem() {
        try {
            fileSystem = ioService.newFileSystem(URI.create("default://wirez"),
                    new HashMap<String, Object>() {{
                        put( "init", Boolean.TRUE );
                        put( "internal", Boolean.TRUE );
                    }});
        } catch ( FileSystemAlreadyExistsException e ) {
            fileSystem = ioService.getFileSystem(URI.create("default://wirez"));
        }
        this.root = fileSystem.getRootDirectories().iterator().next();
    }
    
    @Override
    public DefaultGraph get(Path path) {
        return loadGraph(path);
    }

    public DefaultGraph loadGraph(org.uberfire.backend.vfs.Path path) {
        org.uberfire.java.nio.file.Path nioPath = convert(path);
        if (ioService.exists(nioPath)) {
            try {
                
                String json = ioService.readAllString(nioPath);
                Gson gson = new Gson();
                DefaultGraph graph = gson.fromJson(json, DefaultGraph.class);
                return graph;
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Path save(DefaultGraph graph, String commitMessage) {
        return registerGraph(graph, identity != null ? identity.getIdentifier() : "system",
                commitMessage);
        
    }

    public Path registerGraph(DefaultGraph graph, String subjectId, String message) {

        // TODO: org.uberfire.java.nio.file.Path graphPath = getGraphsPath().resolve(graph.getUUID() + ".wirez");
        org.uberfire.java.nio.file.Path graphPath = getGraphsPath().resolve("test.wirez");
        
        if (subjectId == null || message == null) {
            ioService.startBatch(fileSystem);
        } else {
            ioService.startBatch(fileSystem, new CommentedOption(subjectId, message));
        }
                
        try {
            Gson gson = new Gson();
            String json = gson.toJson(graph);
            ioService.write(graphPath, json);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            ioService.endBatch();
        }
        
        return convert(graphPath);
    }

    private org.uberfire.java.nio.file.Path getGraphsPath() {
        return root.resolve("graphs");
    }

        @Override
    public Path copy(Path path, String newName, String comment) {
        // TODO
        return null;
    }

    @Override
    public void delete(Path path, String comment) {
        // TODO
    }
    
}
