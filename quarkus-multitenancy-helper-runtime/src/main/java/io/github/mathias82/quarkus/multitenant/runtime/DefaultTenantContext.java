package io.github.mathias82.quarkus.multitenant.runtime;

import jakarta.enterprise.context.RequestScoped;

import java.util.Optional;

/**
 * Default implementation of TenantContext.
 * RequestScoped so each HTTP request has its own instance.
 */
@RequestScoped
public class DefaultTenantContext implements TenantContext {

    private String tenantId;

    @Override
    public Optional<String> getTenantId() {
        return Optional.ofNullable(tenantId);
    }

    @Override
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public void clear() {
        this.tenantId = null;
    }
}

