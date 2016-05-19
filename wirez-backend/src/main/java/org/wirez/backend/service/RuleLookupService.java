package org.wirez.backend.service;

import org.jboss.errai.bus.server.annotations.Service;
import org.wirez.core.lookup.criteria.Criteria;
import org.wirez.core.lookup.rule.RuleLookupManager;
import org.wirez.core.lookup.rule.RuleLookupRequest;
import org.wirez.core.rule.Rule;

import javax.inject.Inject;

@Service
public class RuleLookupService implements org.wirez.core.remote.RuleLookupService {

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
