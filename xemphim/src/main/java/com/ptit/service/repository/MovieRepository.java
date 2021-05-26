package com.ptit.service.repository;

import com.ptit.service.model.constant.MovieType;
import com.ptit.service.model.entity.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {
    Movie getById(String id);

    List<Movie> findByNameLike(String movieName);

    List<Movie> findByTypeContains(List<MovieType> type);

    List<Movie> findAllByIdIn(List<String> listId);

    List<Movie> getTop6ByTypeContainsOrderByCreatedAtDesc(List<MovieType> type);

    List<Movie> getTop6ByOrderByViewDesc();
}
