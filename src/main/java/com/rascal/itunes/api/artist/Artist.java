package com.rascal.itunes.api.artist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Objects;

@JsonIgnoreProperties(value = {"wrapperType", "artistType", "primaryGenreId", "primaryGenreName"})
public class Artist {

  private final static ObjectMapper mapper = new ObjectMapper();

  private Integer artistId;
  private String artistName;
  private String artistLinkUrl;
  private Integer amgArtistId;

  public Artist() {
  }

  public static Artist of(JsonNode jsonNode) throws JsonProcessingException {
    return mapper.treeToValue(Objects.requireNonNull(jsonNode), Artist.class);
  }

  public Integer getArtistId() {
    return artistId;
  }

  public void setArtistId(Integer artistId) {
    this.artistId = artistId;
  }

  public String getArtistName() {
    return artistName;
  }

  public void setArtistName(String artistName) {
    this.artistName = artistName;
  }

  public String getArtistLinkUrl() {
    return artistLinkUrl;
  }

  public void setArtistLinkUrl(String artistLinkUrl) {
    this.artistLinkUrl = artistLinkUrl;
  }

  public Integer getAmgArtistId() {
    return amgArtistId;
  }

  public void setAmgArtistId(Integer amgArtistId) {
    this.amgArtistId = amgArtistId;
  }
}
