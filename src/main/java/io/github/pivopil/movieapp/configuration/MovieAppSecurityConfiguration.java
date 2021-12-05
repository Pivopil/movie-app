package io.github.pivopil.movieapp.configuration;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import io.github.pivopil.movieapp.filter.AuthenticationJwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class MovieAppSecurityConfiguration extends WebSecurityConfigurerAdapter {

  public static final String API = "/api/**";

  @Value("${google-client-id}")
  private String googleClientId;

  private final AuthenticationProvider provider;

  @Autowired
  public MovieAppSecurityConfiguration(final AuthenticationProvider authenticationProvider) {
    super();
    this.provider = authenticationProvider;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(provider);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .exceptionHandling()
        .and()
        .authenticationProvider(provider)
        .addFilterBefore(authenticationFilter(), AnonymousAuthenticationFilter.class)
        .authorizeRequests()
        .requestMatchers(new AntPathRequestMatcher(API))
        .authenticated()
        .and()
        .csrf()
        .disable()
        .formLogin()
        .disable()
        .httpBasic()
        .disable()
        .logout()
        .disable();
  }

  @Bean
  protected AuthenticationJwtTokenFilter authenticationFilter() throws Exception {
    final AuthenticationJwtTokenFilter filter =
        new AuthenticationJwtTokenFilter(API, googleIdTokenVerifier());
    filter.setAuthenticationManager(authenticationManager());
    return filter;
  }

  @Bean
  protected GsonFactory gsonFactory() {
    return new GsonFactory();
  }

  @Bean
  protected NetHttpTransport netHttpTransport() {
    return new NetHttpTransport();
  }

  @Bean
  protected GoogleIdTokenVerifier googleIdTokenVerifier() {
    return new GoogleIdTokenVerifier.Builder(netHttpTransport(), gsonFactory())
        .setAudience(Collections.singletonList(googleClientId))
        .build();
  }

  @Bean
  protected AuthenticationEntryPoint forbiddenEntryPoint() {
    return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
  }
}
