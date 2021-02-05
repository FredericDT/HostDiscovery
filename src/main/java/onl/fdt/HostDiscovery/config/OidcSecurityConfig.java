package onl.fdt.HostDiscovery.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Order(value = 100)
@Configuration
@EnableWebSecurity(debug = false)
public class OidcSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                    .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .ignoringAntMatchers("/api/v1/host/register/**")
                .and()
                    .authorizeRequests()
                    .antMatchers("/api/v1/host/register/**", "/api/v1/ip", "/", "/build/**").permitAll()
                .and()
                    .authorizeRequests()
                    .anyRequest().authenticated()
                .and()
                    .exceptionHandling().disable()
                    .oauth2Login()
                        .userInfoEndpoint().oidcUserService(new OidcUserService())
                        .and().defaultSuccessUrl("/")
                .and()
                    .logout(logout -> logout.logoutSuccessHandler(new OidcClientInitiatedLogoutSuccessHandler(this.clientRegistrationRepository)));
    }

}
