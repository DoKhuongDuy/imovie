package com.ptit.service.repository;

import com.ptit.service.model.entity.BookMark;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookMarkRepository extends MongoRepository<BookMark, String> {
    Optional<BookMark> findByUserIdAndMovieId(String userId, String movieId);

    List<BookMark> findByUserId(String userId);

    List<BookMark> findAllByMovieIdIn(List<String> movieIds);
}
