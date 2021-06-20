package com.rascal.itunes.persistence.favourite;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavouriteArtistRepository extends CrudRepository<FavouriteArtistEntity, String> {

}
