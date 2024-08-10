package app.hackoholics.api.endpoint.security;

import app.hackoholics.api.service.UserService;
import app.hackoholics.api.service.google.firebase.FirebaseService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

@Slf4j
@AllArgsConstructor
public class AuthFilter extends GenericFilterBean {
  private final RequestMatcher requestMatcher;
  private final FirebaseService firebaseService;
  private final UserService userService;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    var httpRequest = (HttpServletRequest) request;
    if (requestMatcher.matches(httpRequest)) {
      String token = AuthProvider.getBearer(httpRequest);
      var fUser = firebaseService.getUserByBearer(token);
      var authUser = userService.getByEmailAndAuthenticationId(fUser.email(), fUser.id());
      var principal = new Principal(token, authUser);
      var authentication = new BearerAuthentication(principal, List.of());
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    chain.doFilter(request, response);
  }
}
