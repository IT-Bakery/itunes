package com.rascal.itunes.persistence.artist;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistsRepository extends CrudRepository<ArtistEntity, Integer> {

}
