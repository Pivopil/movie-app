package io.github.pivopil.movieapp.filter;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Objects;

@Slf4j
public class AuthenticationJwtTokenFilter extends AbstractAuthenticationProcessingFilter {

  public static final String AUTHORIZATION = "Authorization";
  private final GoogleIdTokenVerifier googleIdTokenVerifier;

  public AuthenticationJwtTokenFilter(
      String defaultFilterProcessesUrl, GoogleIdTokenVerifier googleIdTokenVerifier) {
    super(defaultFilterProcessesUrl);
    this.googleIdTokenVerifier = googleIdTokenVerifier;
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    String token = request.getHeader(AUTHORIZATION);
    Authentication requestAuthentication = getAuthentication(token);
    return getAuthenticationManager().authenticate(requestAuthentication);
  }

  @Override
  protected void successfulAuthentication(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final FilterChain chain,
      final Authentication authResult)
      throws ServletException, IOException {
    SecurityContextHolder.getContext().setAuthentication(authResult);
    chain.doFilter(request, response);
  }

  public Authentication getAuthentication(String idTokenString) {
    if (Objects.isNull(idTokenString)) {
      log.error("Google Id Token is not presented in Authorization header");
      throw new BadCredentialsException("Invalid Google Id Token");
    }
    try {
      GoogleIdToken idToken = googleIdTokenVerifier.verify(idTokenString);
      if (idToken != null) {
        Payload payload = idToken.getPayload();
        String userId = payload.getSubject();
        return new UsernamePasswordAuthenticationToken(userId, idTokenString);
      } else {
        throw new BadCredentialsException("Invalid Google Id Token");
      }
    } catch (GeneralSecurityException | IOException e) {
      log.error("Fail to extract Google Id Token");
      throw new BadCredentialsException("Failed deserialization of Google Id Token", e);
    }
  }
}
