package com.rascal.itunes.api.favourite;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rascal.itunes.persistence.album.AlbumEntity;
import java.util.Objects;

@JsonIgnoreProperties(value = {"wrapperType", "collectionType", "artistId", "collectionId",
    "amgArtistId", "collectionCensoredName", "artworkUrl60", "artworkUrl100",
    "collectionExplicitness", "country", "artist"})
public class Album {

  private final static ObjectMapper mapper = new ObjectMapper();

  private String artistName;
  private String collectionName;
  private String artistViewUrl;
  private String collectionViewUrl;
  private Double collectionPrice;
  private Integer trackCount;
  private String copyright;
  private String currency;
  private String releaseDate;
  private String primaryGenreName;

  public Album() {
  }

  public static Album of(JsonNode jsonNode) throws JsonProcessingException {
    return mapper.treeToValue(Objects.requireNonNull(jsonNode), Album.class);
  }

  public static Album of(AlbumEntity artistEntity) {
    return new Album(
        artistEntity.getArtistName(),
        artistEntity.getCollectionName(),
        artistEntity.getArtistViewUrl(),
        artistEntity.getCollectionViewUrl(),
        artistEntity.getCollectionPrice(),
        artistEntity.getTrackCount(),
        artistEntity.getCopyright(),
        artistEntity.getCurrency(),
        artistEntity.getReleaseDate(),
        artistEntity.getPrimaryGenreName()
    );
  }

  private Album(String artistName, String collectionName, String artistViewUrl,
      String collectionViewUrl, Double collectionPrice, Integer trackCount,
      String copyright, String currency, String releaseDate, String primaryGenreName) {
    this.artistName = artistName;
    this.collectionName = collectionName;
    this.artistViewUrl = artistViewUrl;
    this.collectionViewUrl = collectionViewUrl;
    this.collectionPrice = collectionPrice;
    this.trackCount = trackCount;
    this.copyright = copyright;
    this.currency = currency;
    this.releaseDate = releaseDate;
    this.primaryGenreName = primaryGenreName;
  }

  public String getArtistName() {
    return artistName;
  }

  public void setArtistName(String artistName) {
    this.artistName = artistName;
  }

  public String getCollectionName() {
    return collectionName;
  }

  public void setCollectionName(String collectionName) {
    this.collectionName = collectionName;
  }

  public String getArtistViewUrl() {
    return artistViewUrl;
  }

  public void setArtistViewUrl(String artistViewUrl) {
    this.artistViewUrl = artistViewUrl;
  }

  public String getCollectionViewUrl() {
    return collectionViewUrl;
  }

  public void setCollectionViewUrl(String collectionViewUrl) {
    this.collectionViewUrl = collectionViewUrl;
  }

  public Double getCollectionPrice() {
    return collectionPrice;
  }

  public void setCollectionPrice(Double collectionPrice) {
    this.collectionPrice = collectionPrice;
  }

  public Integer getTrackCount() {
    return trackCount;
  }

  public void setTrackCount(Integer trackCount) {
    this.trackCount = trackCount;
  }

  public String getCopyright() {
    return copyright;
  }

  public void setCopyright(String copyright) {
    this.copyright = copyright;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public String getPrimaryGenreName() {
    return primaryGenreName;
  }

  public void setPrimaryGenreName(String primaryGenreName) {
    this.primaryGenreName = primaryGenreName;
  }
}
