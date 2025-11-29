package io.github.mathias82.quarkus.multitenant.runtime.resolver;

import io.github.mathias82.quarkus.multitenant.runtime.config.MultiTenantConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.HttpHeaders;

import java.util.Optional;

/**
 * TenantResolver implementation that reads the tenant from an HTTP header.
 */
@ApplicationScoped
public class HeaderTenantResolver implements TenantResolver {


    MultiTenantConfig config;

    @Inject
    public HeaderTenantResolver(MultiTenantConfig config){
        this.config = config;
    }

    @Override
    public Optional<String> resolveTenant(HttpHeaders headers) {
        String headerName = config.headerName();
        String value = headers.getHeaderString(headerName);

        if (value != null && !value.isBlank()) {
            return Optional.of(value);
        }

        return Optional.ofNullable(config.defaultTenant());
    }
}
