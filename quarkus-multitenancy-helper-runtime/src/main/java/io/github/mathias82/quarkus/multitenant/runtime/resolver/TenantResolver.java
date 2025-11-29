package io.github.mathias82.quarkus.multitenant.runtime.resolver;

import jakarta.ws.rs.core.HttpHeaders;
import java.util.Optional;

/**
 * Strategy interface for resolving the tenant from an HTTP request.
 */
public interface TenantResolver {

    Optional<String> resolveTenant(HttpHeaders headers);
}
