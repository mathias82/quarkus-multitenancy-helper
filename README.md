# Quarkus Multi-Tenancy Helper

Opinionated Quarkus extension that makes it easy to build multi-tenant applications.

## Features

- `TenantContext` API to access the current tenant ID
- HTTP filter that resolves tenant from a configurable header (default: `X-Tenant-Id`)
- Configurable via `application.properties`:
  - `quarkus.multi-tenant.enabled`
  - `quarkus.multi-tenant.strategy=header`
  - `quarkus.multi-tenant.header-name`
  - `quarkus.multi-tenant.default-tenant`

## Usage

Add the dependency to your Quarkus application:

```xml
<dependency>
  <groupId>io.github.mathias82.quarkus</groupId>
  <artifactId>quarkus-multitenancy-helper-runtime</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>

## Configure it:

quarkus.multi-tenant.enabled=true
quarkus.multi-tenant.strategy=header
quarkus.multi-tenant.header-name=X-Tenant-Id
quarkus.multi-tenant.default-tenant=public

## Inject the TenantContext:

import io.github.mathias82.quarkus.multitenant.runtime.TenantContext;

@Inject
TenantContext tenantContext;

public void someMethod() {
    String tenant = tenantContext.getTenantId().orElse("unknown");
}


## Build
mvn clean install

## Configuration Reference

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `quarkus.multi-tenant.enabled` | boolean | `false` | Enables multi-tenancy |
| `quarkus.multi-tenant.strategy` | string | `header` | Strategy used (`header`, later `jwt`, etc.) |
| `quarkus.multi-tenant.header-name` | string | `X-Tenant-Id` | Header name for resolving tenant |
| `quarkus.multi-tenant.default-tenant` | string | `public` | Tenant returned when none is provided |
| `quarkus.multi-tenant.jwt-claim-name` | string | `tenant_id` | Claim name when using JWT strategy |

