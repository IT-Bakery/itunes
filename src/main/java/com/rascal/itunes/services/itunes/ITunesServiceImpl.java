package com.rascal.itunes.services.itunes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.rascal.itunes.api.artist.Artist;
import com.rascal.itunes.api.favourite.Album;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.web.client.RestTemplate;

/**
 * iTunes API https://itunes.apple.com/ service
 */
public class ITunesServiceImpl implements ITunesService {

  private final static ObjectMapper mapper = new ObjectMapper();
  private final static String ARTISTS_URI = "https://itunes.apple.com/search?entity=allArtist&term={term}";
  private final static String ALBUMS_URI = "https://itunes.apple.com/lookup?amgArtistId={artistId}&entity=album&limit=5";

  private final RestTemplate restTemplate;

  public ITunesServiceImpl(RestTemplate restTemplate) {
    this.restTemplate = Objects.requireNonNull(restTemplate);
  }

  /**
   * Search for all artists using iTunes endpoint https://itunes.apple.com/search?entity=allArtist&term={term}
   * Result is deserialized to list of artists
   *
   * @param term
   * @return artists List<Artist>
   */
  @Override
  public List<Artist> lookupArtists(String term) {
    var response = this.restTemplate
        .getForEntity(ARTISTS_URI.replace("{term}", term),
            JsonNode.class);
    var result = response.getBody().path("results");
    var artists = deserializeResult(result);

    return artists;
  }

  private List<Artist> deserializeResult(JsonNode result) {
    var reader = mapper.readerFor(new TypeReference<List<Artist>>() {
    });

    try {
      return reader.readValue(result);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Gets top 5 albums from https://itunes.apple.com/lookup?amgArtistId={artistId}&entity=album&limit=5
   * endpoint and deserialize them to list of albums
   *
   * @param amgArtistId - identification used as lookup parameter in iTunes API
   * @return list of albums List<Album>
   */
  @Override
  public List<Album> getTopAlbums(Integer amgArtistId) {
    var response = this.restTemplate
        .getForEntity(ALBUMS_URI.replace("{artistId}", amgArtistId.toString()),
            JsonNode.class);
    var result = (ArrayNode) response.getBody().path("results");
    return deserializeAlbums(result);
  }

  private List<Album> deserializeAlbums(ArrayNode result) {
    var albums = new ArrayList<Album>();
    result.forEach(node -> {
      if ("collection".equals(node.path("wrapperType").textValue())) {
        try {
          albums.add(Album.of(node));
        } catch (JsonProcessingException e) {
          throw new RuntimeException();
        }
      }
    });
    return albums;
  }
}
