package io.github.pivopil.movieapp;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import io.github.pivopil.movieapp.api.MovieController;
import io.github.pivopil.movieapp.configuration.MovieAppAuthenticationProvider;
import io.github.pivopil.movieapp.configuration.MovieAppSecurityConfiguration;
import io.github.pivopil.movieapp.filter.AuthenticationJwtTokenFilter;
import io.github.pivopil.movieapp.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MovieControllerTest {

  public static final String AUTHORIZATION = "Authorization";

  @Autowired private WebApplicationContext context;

  @Autowired private AuthenticationJwtTokenFilter authenticationJwtTokenFilter;

  private MockMvc mockMvc;

  @BeforeAll
  public void setUp() {
    mockMvc =
        MockMvcBuilders.webAppContextSetup(context)
            .addFilter(authenticationJwtTokenFilter, "/api/**")
            .build();
  }

  @MockBean MovieAppSecurityConfiguration movieAppSecurityConfiguration;

  @MockBean private MovieService movieService;

  @MockBean private MovieAppAuthenticationProvider movieAppAuthenticationProvider;

  @MockBean private GoogleIdTokenVerifier googleIdTokenVerifier;

  @Mock private GoogleIdToken googleIdToken;

  @Test
  void callGetTopTenMoviesApiWithInvalidToken_gotOk() throws Exception {
    // GIVEN:
    String token = "valid_google_id_token";
    when(googleIdTokenVerifier.verify(token)).thenReturn(googleIdToken);
    GoogleIdToken.Payload payload = new GoogleIdToken.Payload();
    payload.setSubject("user1");
    when(googleIdToken.getPayload()).thenReturn(payload);
    when(movieService.getTopTenRatedMovies()).thenReturn(Collections.emptyList());
    when(movieAppAuthenticationProvider.authenticate(any()))
        .thenReturn(new UsernamePasswordAuthenticationToken("user1", token));

    // WHEN:
    mockMvc
        .perform(
            get("/api/movies/top")
                .contentType(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, token))

        // @formatter:off
        // THEN:
        // @formatter:on
        .andExpect(status().isOk());
  }

  @Test
  void callSetMovieRatingApiWithInvalidToken_gotOk() throws Exception {
    // GIVEN:
    String token = "invalid_google_id_token";
    // WHEN:
    mockMvc
        .perform(
            patch("/api/movies/123/rating")
                .contentType(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, token)
                .content("{\"score\":123}"))
        // @formatter:off
        // THEN:
        // @formatter:on
        .andExpect(status().isUnauthorized());
  }

  @Test
  void callGetTopTenMoviesApiWithInvalidToken_gotUnauthorized() throws Exception {
    // GIVEN:
    String token = "invalid_google_id_token";

    // WHEN:
    mockMvc
        .perform(
            get("/api/movies/top")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "invalid_google_id_token"))

        // @formatter:off
        // THEN:
        // @formatter:on
        .andExpect(status().isUnauthorized());
  }

  @Test
  void callGetTopTenMoviesApiWithoutToken_gotUnauthorized() throws Exception {
    // GIVEN:
    mockMvc
        .perform(
            patch("/api/movies/123/rating")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"score\":123}"))
        // @formatter:off
        // THEN:
        // @formatter:on
        .andExpect(status().isUnauthorized());
  }

  @Test
  void callSetMovieRatingWithoutToken_gotUnauthorized() throws Exception {
    // WHEN:
    // Execute the PATCH request
    mockMvc
        .perform(
            patch("/api/movies/123/rating")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"score\":123}"))
        // @formatter:off
        // THEN:
        // @formatter:on
        .andExpect(status().isUnauthorized());
  }
}
