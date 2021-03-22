package ca.simplestep.example.resourceserver.jwt.complex.config.tenant;

import ca.simplestep.example.resourceserver.jwt.complex.config.tenant.utils.OidcProviderConfigurationUtils;

import java.util.Map;

public class Tenant {

    private final Map<String, Object> attributes;

    public Tenant(String issuerUri) {
        attributes = OidcProviderConfigurationUtils.getConfigurationForOidcIssuerLocation(issuerUri);
    }

    public Object getAttribute(String attribute) {
        return attributes.get(attribute);
    }
}
