package com.rascal.itunes.persistence.album;

import com.rascal.itunes.api.favourite.Album;
import com.rascal.itunes.persistence.artist.ArtistEntity;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "album")
public class AlbumEntity {

  public AlbumEntity() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer Id;
  @Column
  private String artistName;
  @Column
  private String collectionName;
  @Column
  private String artistViewUrl;
  @Column
  private String collectionViewUrl;
  @Column
  private Double collectionPrice;
  @Column
  private Integer trackCount;
  @Column
  private String copyright;
  @Column
  private String currency;
  @Column
  private String releaseDate;
  @Column
  private String primaryGenreName;
  @Column
  @CreationTimestamp
  private Timestamp created;
  @ManyToOne
  @JoinColumn(name = "artist_id", insertable = false, updatable = false)
  private ArtistEntity artist;

  private AlbumEntity(String artistName, String collectionName, String artistViewUrl,
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

  public static AlbumEntity of(Album album) {
    return new AlbumEntity(
        album.getArtistName(),
        album.getCollectionName(),
        album.getArtistViewUrl(),
        album.getCollectionViewUrl(),
        album.getCollectionPrice(),
        album.getTrackCount(),
        album.getCopyright(),
        album.getCurrency(),
        album.getReleaseDate(),
        album.getPrimaryGenreName()
    );
  }

  public String getArtistName() {
    return artistName;
  }

  public String getCollectionName() {
    return collectionName;
  }

  public String getArtistViewUrl() {
    return artistViewUrl;
  }

  public String getCollectionViewUrl() {
    return collectionViewUrl;
  }

  public Double getCollectionPrice() {
    return collectionPrice;
  }

  public Integer getTrackCount() {
    return trackCount;
  }

  public String getCopyright() {
    return copyright;
  }

  public String getCurrency() {
    return currency;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public String getPrimaryGenreName() {
    return primaryGenreName;
  }

  public Timestamp getCreated() {
    return this.created;
  }

  public AlbumEntity withArtist(ArtistEntity artist) {
    this.artist = artist;
    return this;
  }
}
