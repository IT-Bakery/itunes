package com.rascal.itunes.persistence.artist;

import com.rascal.itunes.api.artist.Artist;
import com.rascal.itunes.persistence.album.AlbumEntity;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "artist")
public class ArtistEntity {

  public ArtistEntity() {
  }

  @Id
  private Integer Id;
  @Column
  private String name;
  @Column
  private String linkUrl;
  @Column
  private Integer amgArtistId;
  @OneToMany
  @JoinColumn(name = "artist_id")
  private List<AlbumEntity> albums;

  private ArtistEntity(Integer id, String name, String linkUrl, Integer amgArtistId) {
    this.Id = Objects.requireNonNull(id);
    this.name = Objects.requireNonNull(name);
    this.linkUrl = Objects.requireNonNull(linkUrl);
    this.amgArtistId = amgArtistId;
  }

  public static ArtistEntity of(Artist artist) {
    return new ArtistEntity(
        artist.getArtistId(),
        artist.getArtistName(),
        artist.getArtistLinkUrl(),
        artist.getAmgArtistId()
    );
  }

  public Integer getAmgArtistId() {
    return this.amgArtistId;
  }

  public List<AlbumEntity> getAlbums() {
    return this.albums;
  }

  public void setAlbums(List<AlbumEntity> albums) {
    this.albums = albums;
  }
}
