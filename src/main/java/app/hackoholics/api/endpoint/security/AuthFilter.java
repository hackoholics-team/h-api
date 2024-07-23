package app.hackoholics.api.endpoint.security;

import app.hackoholics.api.service.UserService;
import app.hackoholics.api.service.google.firebase.FirebaseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class AuthFilter extends AbstractAuthenticationProcessingFilter {
  private final FirebaseService firebaseService;
  private final UserService userService;

  protected AuthFilter(
      RequestMatcher requiresAuthenticationRequestMatcher,
      FirebaseService firebaseService,
      UserService userService) {
    super(requiresAuthenticationRequestMatcher);
    this.firebaseService = firebaseService;
    this.userService = userService;
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    String token = AuthProvider.getBearer(request);
    var fUser = firebaseService.getUserByBearer(token);
    var authUser = userService.getByEmailAndAuthenticationId(fUser.getEmail(), fUser.getUid());
    var principal = new Principal(token, authUser);
    UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(principal, token);
    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return getAuthenticationManager().authenticate(authentication);
  }
}
