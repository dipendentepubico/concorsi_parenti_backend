package it.dipendentepubico.concorsiparenti.spring;

import it.dipendentepubico.concorsiparenti.jpa.auth.entity.RoleEnumEntity;
import it.dipendentepubico.concorsiparenti.jwt.AuthEntryPointJwt;
import it.dipendentepubico.concorsiparenti.jwt.AuthTokenFilter;
import it.dipendentepubico.concorsiparenti.usecase.auth.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Profile("!SECURITY_MOCK")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true)
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/test/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/v3/api-docs/**").permitAll()
                // websocket heartbeat
                .antMatchers("/websocket/**").permitAll()
                // get per tutti
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                // post/put solo per ruolo editor e admin
                .antMatchers(HttpMethod.POST,"/api/**").hasAnyAuthority(RoleEnumEntity.ROLE_MODERATOR.toString(), RoleEnumEntity.ROLE_ADMIN.toString())
                .antMatchers(HttpMethod.PUT,"/api/**").hasAnyAuthority(RoleEnumEntity.ROLE_MODERATOR.toString(), RoleEnumEntity.ROLE_ADMIN.toString())
                // blocco tutto il resto
                .anyRequest().authenticated()
        ;
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);


    }
}