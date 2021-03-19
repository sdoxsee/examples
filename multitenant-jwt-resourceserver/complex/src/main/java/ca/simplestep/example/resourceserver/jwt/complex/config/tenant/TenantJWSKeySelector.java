package ca.simplestep.example.resourceserver.jwt.complex.config.tenant;

import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.proc.JWSAlgorithmFamilyJWSKeySelector;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.JWTClaimsSetAwareJWSKeySelector;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.security.Key;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TenantJWSKeySelector
    implements JWTClaimsSetAwareJWSKeySelector<SecurityContext> {

    private final TenantRepository tenantRepository;
    private final Map<String, JWSKeySelector<SecurityContext>> selectors = new ConcurrentHashMap<>();

    public TenantJWSKeySelector(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    public List<? extends Key> selectKeys(JWSHeader jwsHeader, JWTClaimsSet jwtClaimsSet, SecurityContext securityContext)
            throws KeySourceException {
        return this.selectors.computeIfAbsent(toTenant(jwtClaimsSet), this::fromTenant)
                .selectJWSKeys(jwsHeader, securityContext);
    }

    private String toTenant(JWTClaimsSet claimSet) {
        return (String) claimSet.getClaim("iss");
    }

    private JWSKeySelector<SecurityContext> fromTenant(String tenant) {
        return Optional.ofNullable(this.tenantRepository.findById(tenant))
                .map(t -> (String)t.getAttribute("jwks_uri"))
                .map(this::fromUri)
                .orElseThrow(() -> new IllegalArgumentException("unknown tenant"));
    }

    private JWSKeySelector<SecurityContext> fromUri(String uri) {
        try {
            return JWSAlgorithmFamilyJWSKeySelector.fromJWKSetURL(new URL(uri));
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}