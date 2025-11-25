package io.github.mathias82.quarkus.multitenant.runtime;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

/**
 * Configuration mapping for multi-tenancy.
 *
 * quarkus.multi-tenant.enabled=true
 * quarkus.multi-tenant.strategy=header
 * quarkus.multi-tenant.header-name=X-Tenant-Id
 * quarkus.multi-tenant.default-tenant=public
 */
@ConfigMapping(prefix = "quarkus.multi-tenant")
public interface MultiTenantConfig {

    @WithDefault("false")
    boolean enabled();

    /**
     * Strategy to resolve tenant: "header", later "jwt", "path" etc.
     */
    @WithDefault("header")
    String strategy();

    /**
     * Header name used when strategy=header.
     */
    @WithDefault("X-Tenant-Id")
    String headerName();

    /**
     * Default tenant id if none is found.
     */
    @WithDefault("public")
    String defaultTenant();

    /**
     * Claim name for JWT strategy (future use).
     */
    @WithDefault("tenant_id")
    String jwtClaimName();
}

