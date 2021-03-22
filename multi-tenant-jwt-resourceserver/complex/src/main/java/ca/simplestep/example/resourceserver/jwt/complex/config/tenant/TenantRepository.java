package ca.simplestep.example.resourceserver.jwt.complex.config.tenant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TenantRepository {

    private final Map<String, Tenant> tenants = new ConcurrentHashMap<>();

    public Tenant findById(String tenant) {
        return tenants.get(tenant);
    }

    public void save(Tenant tenant) {
        tenants.put((String)tenant.getAttribute("issuer"), tenant);
    }
}
