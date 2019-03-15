package com.keycloakapp.demo;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoApplicationTests {

    private static String USERNAME = "test";
    private static String PASSWORD = "test";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Value("${keycloak.credentials.secret}")
    private String secret;

    @Value("${keycloak.auth-server-url}")
    private String url;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Autowired
    private TestRestTemplate restTemplate;


    private String retrieveToken(String username,String password) throws Exception{
            String targetUrl ="http://localhost:8180/auth/realms/dev/protocol/openid-connect/token";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("username", username);
            body.add("password", password);
            body.add("client_id", clientId);
            body.add("client_secret", secret);
            body.add("grant_type", "password");

            HttpEntity<?> httpEntity = new HttpEntity<Object>(body, headers);
            RestTemplate template = new RestTemplate();
            AccessTokenResponse response = template.exchange(targetUrl, HttpMethod.POST, httpEntity, AccessTokenResponse.class).getBody();
            return response.getToken();
    }

    private HttpHeaders createHeaders(String username, String password) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        String header = "Authorization";
        String token = retrieveToken(username, password);
        headers.add(header, "Bearer " + token);
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
        return headers;
    }

    /**
     * Tests /search endpoint when caller has role user
     */
    @Test
    public void testGetMoviesList_User() throws Exception {

        String url = "http://127.0.0.1:8081/service/movies/search/batman";
        HttpHeaders header = createHeaders(USERNAME, PASSWORD);
        ResponseEntity<String> data = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(header), String.class);
        assertEquals(Response.Status.OK.getStatusCode(),data.getStatusCode().value());
        assertNotNull(data.getBody());
    }

    /**
     * Tests /details endpoint when caller doesn't have necessary role
     */
    @Test
    public void testGetMovieDetails_User() throws Exception{

        String url = "http://localhost:8081/service/movies/details/tt2975590";
        HttpHeaders header = createHeaders(USERNAME, PASSWORD);
        ResponseEntity<String> data = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(header), String.class);
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), data.getStatusCodeValue());
    }

    /**
     * Tests /getMovies endpoint when caller uses wrong credentials
     */
    @Test
    public void testBadCredentials() throws Exception {
        thrown.expect(Exception.class);
        String url = "http://localhost:8081/service/movies/search/batman";
        HttpHeaders header = createHeaders("test", "t5est");
        ResponseEntity<String> data = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(header), String.class);
//        assertEquals(data.getStatusCode().value(), Response.Status.UNAUTHORIZED.getStatusCode());

    }

    /**
     * Tests /details endpoint call when caller has necessary role
     */
    @Test
    public void testGetMovieDetails_Admin() throws Exception{
        String url = "http://localhost:8081/service/movies/details/tt2975590";
        HttpHeaders header = createHeaders("user", "test");
        ResponseEntity<String> data = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(header), String.class);
        assertEquals(Response.Status.OK.getStatusCode(),data.getStatusCode().value());
    }

    /**
     * Test /getMovies endpoint call when caller has necessary role
     */
    @Test
    public void testGetMoviesList_Admin() throws Exception {
        String url = "http://localhost:8081/service/movies/search/Superman";
        HttpHeaders header = createHeaders("user", "test");
        ResponseEntity<String> data = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(header), String.class);
        assertEquals(Response.Status.OK.getStatusCode(),data.getStatusCode().value());
    }

}

