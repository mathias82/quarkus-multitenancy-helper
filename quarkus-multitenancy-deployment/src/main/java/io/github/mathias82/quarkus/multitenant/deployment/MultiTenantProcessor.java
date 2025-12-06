package io.github.mathias82.quarkus.multitenant.deployment;

import io.github.mathias82.quarkus.multitenant.runtime.context.DefaultTenantContext;
import io.github.mathias82.quarkus.multitenant.runtime.resolver.HeaderTenantResolver;
import io.github.mathias82.quarkus.multitenant.runtime.filter.TenantFilter;

import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;

/**
 * Quarkus deployment processor for the extension.
 */
public class MultiTenantProcessor {

    /**
     * Register runtime beans as unremovable so Quarkus keeps them.
     */
    @BuildStep
    AdditionalBeanBuildItem registerBeans() {
        return AdditionalBeanBuildItem.builder()
                .addBeanClass(DefaultTenantContext.class)
                .addBeanClass(HeaderTenantResolver.class)
                .addBeanClass(TenantFilter.class)
                .build();
    }

    /**
     * Call recorder at runtime init.
     */
    @BuildStep
    @Record(ExecutionTime.RUNTIME_INIT)
    void init(MultiTenantRecorder recorder) {
        recorder.init();
    }
}
