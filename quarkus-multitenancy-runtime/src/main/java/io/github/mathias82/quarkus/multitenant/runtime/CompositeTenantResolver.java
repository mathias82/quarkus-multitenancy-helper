package io.github.mathias82.quarkus.multitenant.runtime;

import io.github.mathias82.quarkus.multitenant.runtime.resolver.TenantResolver;
import jakarta.ws.rs.core.HttpHeaders;

import java.util.List;
import java.util.Optional;

public class CompositeTenantResolver implements TenantResolver {

    private final List<TenantResolver> resolvers;

    public CompositeTenantResolver(List<TenantResolver> resolvers) {
        this.resolvers = resolvers;
    }

    @Override
    public Optional<String> resolveTenant(HttpHeaders headers) {
        for (TenantResolver resolver : resolvers) {
            Optional<String> tenant = resolver.resolveTenant(headers);
            if (tenant.isPresent()) {
                return tenant;
            }
        }
        return Optional.empty();
    }
}
