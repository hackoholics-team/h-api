package app.hackoholics.api.endpoint.security;

import java.util.Collection;
import java.util.Objects;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class BearerAuthentication extends AbstractAuthenticationToken {
  private final Principal principal;

  public BearerAuthentication(
      Principal principal, Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.principal = principal;
    setAuthenticated(true);
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return principal;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    BearerAuthentication that = (BearerAuthentication) o;
    return Objects.equals(principal, that.principal);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), principal);
  }
}
