package io.github.mathias82.quarkus.multitenant.runtime;

import io.github.mathias82.quarkus.multitenant.runtime.config.MultiTenantConfig;
import io.github.mathias82.quarkus.multitenant.runtime.resolver.*;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TenantResolverProducer {

    @Inject
    MultiTenantConfig config;

    @Inject
    HeaderTenantResolver headerResolver;

    @Inject
    JwtTenantResolver jwtResolver;

    @Produces
    @ApplicationScoped
    @PrimaryTenantResolver
    TenantResolver tenantResolver() {

        List<TenantResolver> activeResolvers = new ArrayList<>();

        for (Strategy strategy : config.strategy()) {
            switch (strategy) {
                case header:
                    activeResolvers.add(headerResolver);
                    break;
                case jwt:
                    activeResolvers.add(jwtResolver);
                    break;
            }
        }

        if (activeResolvers.size() == 1) {
            return activeResolvers.get(0);
        }

        return new CompositeTenantResolver(activeResolvers);
    }
}
