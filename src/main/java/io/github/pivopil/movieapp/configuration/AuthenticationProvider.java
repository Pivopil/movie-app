package io.github.pivopil.movieapp.configuration;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

  @Override
  protected void additionalAuthenticationChecks(
      UserDetails userDetails,
      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken)
      throws AuthenticationException {}

  @Override
  protected UserDetails retrieveUser(
      String userName, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken)
      throws AuthenticationException {
    return new User(
        userName,
        String.valueOf(usernamePasswordAuthenticationToken.getCredentials()),
        Collections.emptyList());
  }
}
