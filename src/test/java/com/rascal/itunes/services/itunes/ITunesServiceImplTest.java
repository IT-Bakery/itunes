package com.rascal.itunes.services.itunes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class ITunesServiceImplTest {

  private final static ObjectMapper mapper = new ObjectMapper();
  private final static Integer ID = 1;
  private final static String TERM = "TERM";

  @Mock
  private RestTemplate restTemplate;
  @Mock
  private ResponseEntity response;
  @Mock
  private JsonNode body;

  private ITunesService iTunesService;

  @BeforeEach
  void setUp() {
    when(this.restTemplate.getForEntity(anyString(), any())).thenReturn(this.response);
    when(this.response.getBody()).thenReturn(this.body);

    this.iTunesService = new ITunesServiceImpl(this.restTemplate);
  }

  @Test
  void shouldReturnListOfAlbums() {
    var album = mapper.createObjectNode()
        .put("artistName", "artistName")
        .put("wrapperType", "collection");
    var result = mapper.createArrayNode().add(album).add(album);
    when(this.body.path("results")).thenReturn(result);

    var albums = this.iTunesService.getTopAlbums(ID);

    verify(this.restTemplate).getForEntity(
        eq("https://itunes.apple.com/lookup?amgArtistId=" + ID + "&entity=album&limit=5"), any());
    assertAll(
        () -> assertEquals(2, albums.size()),
        () -> assertEquals("artistName", albums.get(0).getArtistName()),
        () -> assertEquals("artistName", albums.get(1).getArtistName())
    );
  }

  @Test
  void shouldReturnEmptyListWhenThereIsNoRecordsWithWrapperTypeCollection() {
    var album = mapper.createObjectNode()
        .put("wrapperType", "artist");
    var result = mapper.createArrayNode().add(album);
    when(this.body.path("results")).thenReturn(result);

    var albums = this.iTunesService.getTopAlbums(ID);

    verify(this.restTemplate).getForEntity(
        eq("https://itunes.apple.com/lookup?amgArtistId=" + ID + "&entity=album&limit=5"), any());
    assertThat(albums.isEmpty());
  }

  @Test
  void shouldThrowRuntimeExceptionWhenUnexpectedFieldIsProvided() {
    var album = mapper.createObjectNode()
        .put("wrapperType", "collection")
        .put("unexpectedField", "unexpectedField");
    var result = mapper.createArrayNode().add(album);
    when(this.body.path("results")).thenReturn(result);

    assertThrows(RuntimeException.class, () -> this.iTunesService.getTopAlbums(ID));

    verify(this.restTemplate).getForEntity(
        eq("https://itunes.apple.com/lookup?amgArtistId=" + ID + "&entity=album&limit=5"), any());
  }

  @Test
  void shouldReturnListOfArtists() {
    var artist = mapper.createObjectNode()
        .put("artistName", "artistName");
    var result = mapper.createArrayNode().add(artist).add(artist);
    when(this.body.path("results")).thenReturn(result);

    var artists = this.iTunesService.lookupArtists(TERM);

    verify(this.restTemplate).getForEntity(
        eq("https://itunes.apple.com/search?entity=allArtist&term=" + TERM), any());
    assertAll(
        () -> assertEquals(2, artists.size()),
        () -> assertEquals("artistName", artists.get(0).getArtistName()),
        () -> assertEquals("artistName", artists.get(1).getArtistName())
    );
  }

  @Test
  void shouldThrowRuntimeExceptionWhenUnexpectedFieldIsProvidedInArtistsResults() {
    var artist = mapper.createObjectNode()
        .put("unexpectedField", "unexpectedField");
    var result = mapper.createArrayNode().add(artist).add(artist);
    when(this.body.path("results")).thenReturn(result);

    assertThrows(RuntimeException.class, () -> this.iTunesService.lookupArtists(TERM));

    verify(this.restTemplate).getForEntity(
        eq("https://itunes.apple.com/search?entity=allArtist&term=" + TERM), any());
  }
}