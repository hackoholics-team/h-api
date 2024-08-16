package app.hackoholics.api.integration;

import static app.hackoholics.api.conf.TestConf.VALID_TOKEN;
import static app.hackoholics.api.conf.TestConf.anAvailablePort;
import static app.hackoholics.api.conf.TestConf.setUpFirebaseService;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import app.hackoholics.api.AbstractContextInitializer;
import app.hackoholics.api.conf.TestConf;
import app.hackoholics.api.endpoint.mapper.PlaceRestMapper;
import app.hackoholics.api.endpoint.rest.api.PlaceApi;
import app.hackoholics.api.endpoint.rest.client.ApiClient;
import app.hackoholics.api.endpoint.rest.client.ApiException;
import app.hackoholics.api.service.FileService;
import app.hackoholics.api.service.PlaceService;
import app.hackoholics.api.service.google.GoogleConf;
import app.hackoholics.api.service.google.firebase.FirebaseService;
import app.hackoholics.api.service.google.gemini.GeminiService;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.PlacesSearchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = PlaceControllerIT.ContextInitializer.class)
@AutoConfigureMockMvc
class PlaceControllerIT {
  @MockBean GoogleConf googleConf;
  @MockBean GeminiService geminiService;
  @MockBean FileService fileService;
  @MockBean FirebaseService firebaseService;
  @MockBean PlaceRestMapper placeRestMapper;
  @MockBean PlaceService placeService;

  private ApiClient anApiClient(String token) {
    return TestConf.anApiClient(token, PlaceControllerIT.ContextInitializer.SERVER_PORT);
  }

  @BeforeEach
  void setUp() {
    setUpFirebaseService(firebaseService);
  }

  @Test
  void read_places_ok() throws ApiException {
    when(placeService.getNationalParkAndNatureReserve(any()))
        .thenReturn(new PlacesSearchResult[] {});
    when(placeRestMapper.toRest(any(PlacesSearchResult.class)))
        .thenReturn(new app.hackoholics.api.endpoint.rest.model.PlacesSearchResult());
    ApiClient client = anApiClient(VALID_TOKEN);
    PlaceApi api = new PlaceApi(client);

    var actual = api.getParkAndNReserve("dummy");

    assertTrue(actual.isEmpty());
  }

  @Test
  void read_place_details_ok() throws ApiException {
    when(placeService.getPlaceDetails(any())).thenReturn(new PlaceDetails());
    when(placeRestMapper.toRest(any(PlaceDetails.class)))
        .thenReturn(new app.hackoholics.api.endpoint.rest.model.PlaceDetails());
    ApiClient client = anApiClient(VALID_TOKEN);
    PlaceApi api = new PlaceApi(client);

    var actual = api.aboutParksOrReserve("dummy");

    assertNotNull(actual);
  }

  static class ContextInitializer extends AbstractContextInitializer {
    public static final int SERVER_PORT = anAvailablePort();

    @Override
    public int getServerPort() {
      return SERVER_PORT;
    }
  }
}
