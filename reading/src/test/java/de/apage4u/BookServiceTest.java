package de.apage4u;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BookServiceTest {
  private static final String HOST = "http://localhost:";
  private static final String PATH = "/my/resource";
  private static final int PORT = 8089;

  @Rule
  public WireMockRule wireMockRule = new WireMockRule(PORT);

  // For example a jersey client
  private Client client;

  @Before
  public void setUp() {
    client = ClientBuilder.newClient();
  }

  @Test
  public void shouldGetXml() {
    stubFor(buildWireMockMappingFor("<response>cake</response>"));

    Response response = buildWebTargetFor(PATH).request(MediaType.TEXT_XML).get();

    assertThat(response.getStatus(), is(SC_OK));
    assertThat(response.readEntity(String.class), containsString(">cake<"));
  }

  private MappingBuilder buildWireMockMappingFor(String body) {
    return get(urlEqualTo(PATH))
        .withHeader("Accept", equalTo("text/xml"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "text/xml")
            .withBody(body));
  }

  private WebTarget buildWebTargetFor(String path) {
    return client.target(HOST + PORT).path(path);
  }
}