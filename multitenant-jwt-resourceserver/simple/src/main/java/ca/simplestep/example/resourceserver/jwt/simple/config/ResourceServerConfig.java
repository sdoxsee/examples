package ca.simplestep.example.resourceserver.jwt.simple.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;


@EnableWebSecurity
@EnableConfigurationProperties(ApplicationProperties.class)
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    private ApplicationProperties properties;

    public ResourceServerConfig(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests((requests) -> requests.anyRequest().authenticated());
        http.oauth2ResourceServer(rs -> rs.authenticationManagerResolver(
                new JwtIssuerAuthenticationManagerResolver(properties.getIssuers())));
    }
}
