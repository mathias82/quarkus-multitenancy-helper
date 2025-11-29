package io.github.mathias82.quarkus.multitenant.runtime.filter;

import io.github.mathias82.quarkus.multitenant.runtime.resolver.PrimaryTenantResolver;
import io.github.mathias82.quarkus.multitenant.runtime.resolver.TenantResolver;
import io.github.mathias82.quarkus.multitenant.runtime.config.MultiTenantConfig;
import io.github.mathias82.quarkus.multitenant.runtime.context.TenantContext;
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
    @PrimaryTenantResolver
    TenantResolver tenantResolver;

    @Inject
    MultiTenantConfig config;

    @Context
    HttpHeaders httpHeaders;

    @Override
    public void filter(ContainerRequestContext requestContext) {

        tenantContext.clear();

        tenantResolver.resolveTenant(httpHeaders)
                .ifPresent(tenantContext::setTenantId);
    }
}

