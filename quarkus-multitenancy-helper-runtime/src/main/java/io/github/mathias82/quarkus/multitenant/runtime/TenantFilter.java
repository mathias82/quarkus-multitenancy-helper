package io.github.mathias82.quarkus.multitenant.runtime;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.ext.Provider;

/**
 * JAX-RS request filter that resolves and sets the tenant for each request.
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
@ApplicationScoped
public class TenantFilter implements ContainerRequestFilter {

    @Inject
    TenantContext tenantContext;

    @Inject
    TenantResolver tenantResolver;

    @Inject
    MultiTenantConfig config;

    @Context
    HttpHeaders httpHeaders;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        if (!config.enabled()) {
            return;
        }

        tenantContext.clear();

        tenantResolver.resolveTenant(httpHeaders)
                .ifPresent(tenantContext::setTenantId);
    }
}

