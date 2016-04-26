package org.wirez.core.backend.service;

import org.jboss.errai.bus.server.annotations.Service;
import org.wirez.core.api.lookup.criteria.Criteria;
import org.wirez.core.api.lookup.rule.RuleLookupManager;
import org.wirez.core.api.lookup.rule.RuleLookupRequest;
import org.wirez.core.api.rule.Rule;

import javax.inject.Inject;

@Service
public class RuleLookupService implements org.wirez.core.api.remote.RuleLookupService {

    RuleLookupManager ruleLookupManager;

    @Inject
    public RuleLookupService(@Criteria RuleLookupManager ruleLookupManager ) {
        this.ruleLookupManager = ruleLookupManager;
    }


    @Override
    public LookupResponse<Rule> lookup( RuleLookupRequest request ) {
        return ruleLookupManager.lookup( request );
    }
}
