package app.hackoholics.api.endpoint.security;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import app.hackoholics.api.endpoint.security.matcher.SelfUserMatcher;
import app.hackoholics.api.service.UserService;
import app.hackoholics.api.service.google.firebase.FirebaseService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConf {
  private final UserService service;
  private final FirebaseService firebaseService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    var anonymousRequest =
        new NegatedRequestMatcher(
            new OrRequestMatcher(
                new AntPathRequestMatcher("/ping", GET.name()),
                new AntPathRequestMatcher("/signup", POST.name()),
                new AntPathRequestMatcher("/processing", POST.name()),
                new AntPathRequestMatcher("/**", OPTIONS.toString())));
    http.addFilterBefore(
            new AuthFilter(anonymousRequest, firebaseService, service),
            AnonymousAuthenticationFilter.class)
        .authorizeHttpRequests(
            authorize ->
                authorize
                    .requestMatchers(OPTIONS, "/**")
                    .permitAll()
                    .requestMatchers("/ping")
                    .permitAll()
                    .requestMatchers(POST, "/signup")
                    .permitAll()
                    .requestMatchers(POST, "/processing")
                    .permitAll()
                    .requestMatchers(POST, "/signin")
                    .authenticated()
                    .requestMatchers(new SelfUserMatcher(PUT, "/users/*/raw"))
                    .authenticated()
                    .requestMatchers(new SelfUserMatcher(PUT, "/users/*/requirements"))
                    .authenticated()
                    .requestMatchers(new SelfUserMatcher(GET, "/users/*/requirements"))
                    .authenticated()
                    .requestMatchers(new SelfUserMatcher(GET, "/users/*/preferences"))
                    .authenticated()
                    .requestMatchers(new SelfUserMatcher(PUT, "/users/*/preferences"))
                    .authenticated()
                    .requestMatchers(new SelfUserMatcher(PUT, "/users/*/paymentMethods"))
                    .authenticated()
                    .requestMatchers(new SelfUserMatcher(GET, "/users/*/paymentMethods"))
                    .authenticated()
                    .requestMatchers(new SelfUserMatcher(GET, "/users/*/paymentMethods/*"))
                    .authenticated()
                    .requestMatchers(PUT, "/files/*/raw")
                    .authenticated()
                    .requestMatchers(GET, "/files/*/raw")
                    .authenticated()
                    .requestMatchers(GET, "/places")
                    .authenticated()
                    .requestMatchers(GET, "/places/about")
                    .authenticated()
                    .anyRequest()
                    .denyAll())
        .csrf(AbstractHttpConfigurer::disable)
        .httpBasic(Customizer.withDefaults())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .cors(AbstractHttpConfigurer::disable);
    return http.build();
  }
}
