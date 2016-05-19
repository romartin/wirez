package org.wirez.backend.service;

import org.jboss.errai.bus.server.annotations.Service;
import org.wirez.core.lookup.criteria.Criteria;
import org.wirez.core.lookup.definition.DefinitionLookupManager;
import org.wirez.core.lookup.definition.DefinitionLookupRequest;
import org.wirez.core.lookup.definition.DefinitionRepresentation;

import javax.inject.Inject;

@Service
public class DefinitionLookupService implements org.wirez.core.remote.DefinitionLookupService {

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
