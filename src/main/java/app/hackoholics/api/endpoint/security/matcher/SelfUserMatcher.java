package app.hackoholics.api.endpoint.security.matcher;

import app.hackoholics.api.endpoint.security.AuthProvider;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class SelfUserMatcher extends SelfMatcher {
  public SelfUserMatcher(HttpMethod method, String antPattern) {
    super(method, antPattern);
  }

  @Override
  public boolean matches(HttpServletRequest request) {
    AntPathRequestMatcher antMatcher = new AntPathRequestMatcher(antPattern, method.toString());
    if (!antMatcher.matches(request)) {
      return false;
    }
    return Objects.equals(getId(request), AuthProvider.getUser().getId());
  }
}
