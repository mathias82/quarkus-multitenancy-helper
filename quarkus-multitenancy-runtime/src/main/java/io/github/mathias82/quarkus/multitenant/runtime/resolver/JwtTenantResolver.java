package io.github.mathias82.quarkus.multitenant.runtime.resolver;

import io.github.mathias82.quarkus.multitenant.runtime.config.MultiTenantConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.HttpHeaders;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class JwtTenantResolver implements TenantResolver {

    private final MultiTenantConfig config;

    @Inject
    public JwtTenantResolver(MultiTenantConfig config) {
        this.config = config;
    }

    @Override
    public Optional<String> resolveTenant(HttpHeaders headers) {
        String auth = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (auth == null || !auth.startsWith("Bearer ")) {
            return Optional.empty();
        }

        String token = auth.substring("Bearer ".length()).trim();
        String[] parts = token.split("\\.");
        if (parts.length < 2) {
            return Optional.empty();
        }

        try {
            String payloadJson = new String(
                    Base64.getUrlDecoder().decode(parts[1]),
                    StandardCharsets.UTF_8);

            JsonObject payload = new JsonObject(payloadJson);

            String claimName = config.jwtClaimName();
            String tenantId = payload.getString(claimName);

            if (tenantId == null || tenantId.isBlank()) {
                return Optional.empty();
            }
            return Optional.of(tenantId);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
