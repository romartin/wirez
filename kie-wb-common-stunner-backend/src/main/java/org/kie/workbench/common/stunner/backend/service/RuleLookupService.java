package org.kie.workbench.common.stunner.backend.service;

import org.jboss.errai.bus.server.annotations.Service;
import org.kie.workbench.common.stunner.core.lookup.criteria.Criteria;
import org.kie.workbench.common.stunner.core.lookup.rule.RuleLookupManager;
import org.kie.workbench.common.stunner.core.lookup.rule.RuleLookupRequest;
import org.kie.workbench.common.stunner.core.rule.Rule;

import javax.inject.Inject;

@Service
public class RuleLookupService implements org.kie.workbench.common.stunner.core.remote.RuleLookupService {

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
