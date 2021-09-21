package br.com.zupacademy.antonio.proposta.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@Configuration
@EnableWebSecurity
@Profile("dev")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    //Configuracoes de autorizacao
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests -> authorizeRequests
                .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                .antMatchers(HttpMethod.GET, "/proposta/**").hasAuthority("SCOPE_escopo-proposta")
                .antMatchers(HttpMethod.POST, "/proposta/**").hasAuthority("SCOPE_escopo-proposta")
                .antMatchers(HttpMethod.POST, "/biometrias/cartoes/**").hasAuthority("SCOPE_escopo-proposta")
                .antMatchers(HttpMethod.POST, "/bloqueios/cartoes/**").hasAuthority("SCOPE_escopo-proposta")
                .anyRequest().authenticated()
        )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }

    //Configuracoes de recursos
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2/**");
    }
}
