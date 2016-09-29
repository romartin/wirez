package org.kie.workbench.common.stunner.backend.service;

import org.jboss.errai.bus.server.annotations.Service;
import org.kie.workbench.common.stunner.core.lookup.criteria.Criteria;
import org.kie.workbench.common.stunner.core.lookup.definition.DefinitionLookupManager;
import org.kie.workbench.common.stunner.core.lookup.definition.DefinitionLookupRequest;
import org.kie.workbench.common.stunner.core.lookup.definition.DefinitionRepresentation;

import javax.inject.Inject;

@Service
public class DefinitionLookupService implements org.kie.workbench.common.stunner.core.remote.DefinitionLookupService {

    DefinitionLookupManager lookupManager;

    @Inject
    public DefinitionLookupService( @Criteria DefinitionLookupManager lookupManager ) {
        this.lookupManager = lookupManager;
    }

    @Override
    public LookupResponse<DefinitionRepresentation> lookup( DefinitionLookupRequest request ) {
        return lookupManager.lookup( request );
    }
    
}
