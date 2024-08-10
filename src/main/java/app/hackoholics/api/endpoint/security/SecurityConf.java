package app.hackoholics.api.endpoint.security;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import app.hackoholics.api.endpoint.security.matcher.SelfUserMatcher;
import app.hackoholics.api.service.UserService;
import app.hackoholics.api.service.google.firebase.FirebaseService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
public class SecurityConf {
  private final UserService service;
  private final FirebaseService firebaseService;
  private final AuthenticationEntryPoint authEntryPoint;

  public SecurityConf(
      UserService service,
      FirebaseService firebaseService,
      @Qualifier("delegatedAuthenticationEntryPoint") AuthenticationEntryPoint authEntryPoint) {
    this.service = service;
    this.firebaseService = firebaseService;
    this.authEntryPoint = authEntryPoint;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    var anonymousAuth =
        new NegatedRequestMatcher(
            new OrRequestMatcher(
                new AntPathRequestMatcher("/ping", GET.name()),
                new AntPathRequestMatcher("/signup", POST.name()),
                new AntPathRequestMatcher("/**", OPTIONS.toString())));
    http.addFilterBefore(bearerFilter(anonymousAuth), AnonymousAuthenticationFilter.class)
        .authorizeHttpRequests(
            authorize ->
                authorize
                    .requestMatchers(OPTIONS, "/**")
                    .permitAll()
                    .requestMatchers("/ping")
                    .permitAll()
                    .requestMatchers(POST, "/signup")
                    .permitAll()
                    .requestMatchers(POST, "/signin")
                    .authenticated()
                    .requestMatchers(POST, "/chat")
                    .authenticated()
                    .requestMatchers(new SelfUserMatcher(PUT, "/users/*/raw"))
                    .authenticated()
                    .requestMatchers(PUT, "/files/*/raw")
                    .authenticated()
                    .requestMatchers(GET, "/files/*/raw")
                    .authenticated()
                    .anyRequest()
                    .denyAll())
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .cors(AbstractHttpConfigurer::disable)
        .exceptionHandling(handler -> handler.authenticationEntryPoint(authEntryPoint));
    return http.build();
  }

  private AuthFilter bearerFilter(RequestMatcher requestMatcher) {
    return new AuthFilter(requestMatcher, firebaseService, service);
  }

  @Component("delegatedAuthenticationEntryPoint")
  public static class EntryPoint implements AuthenticationEntryPoint {
    private final HandlerExceptionResolver resolver;

    public EntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
      this.resolver = resolver;
    }

    @Override
    public void commence(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException authException)
        throws IOException, ServletException {
      resolver.resolveException(request, response, null, authException);
    }
  }
}
