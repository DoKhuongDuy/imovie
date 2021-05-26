package com.ptit.service.service.impl;

import com.ptit.service.common.BaseResponse;
import com.ptit.service.model.constant.MovieType;
import com.ptit.service.model.entity.BookMark;
import com.ptit.service.model.entity.Episode;
import com.ptit.service.model.entity.Movie;
import com.ptit.service.model.exception.TypeCustomException;
import com.ptit.service.model.response.MovieResponse;
import com.ptit.service.repository.BookMarkRepository;
import com.ptit.service.repository.EpisodeRepository;
import com.ptit.service.repository.MovieRepository;
import com.ptit.service.service.MovieService;
import com.ptit.service.service.mapper.MovieMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    private final EpisodeRepository episodeRepository;

    @Override
    public BaseResponse getAll() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieResponse> movieResponses = mapToResponse(movies);
        return BaseResponse.builder()
                .data(movieResponses)
                .message("success")
                .build();
    }

    @Override
    public BaseResponse getByName(String movieName) {
        List<Movie> movies = movieRepository.findByNameLike(movieName);
        List<MovieResponse> movieResponses = mapToResponse(movies);
        return BaseResponse.builder()
                .data(movieResponses)
                .message("success")
                .build();
    }

    @Override
    public BaseResponse getByType(String type) {
        MovieType movieType = MovieType.valueOf(type);
        List<Movie> movies = movieRepository.findByTypeContains(Collections.singletonList(movieType));
        List<MovieResponse> movieResponses = mapToResponse(movies);
        return BaseResponse.builder()
                .data(movieResponses)
                .message("success")
                .build();
    }

    @Override
    public BaseResponse getTop6ByType(String type) {
        MovieType movieType = MovieType.valueOf(type);
        List<Movie> movies = movieRepository.getTop6ByTypeContainsOrderByCreatedAtDesc(Collections.singletonList(movieType));
        List<MovieResponse> movieResponses = mapToResponse(movies);
        return BaseResponse.builder()
                .data(movieResponses)
                .message("success")
                .build();
    }

    @Override
    public BaseResponse getTopView() {
        List<Movie> movies = movieRepository.getTop6ByOrderByViewDesc();
        List<MovieResponse> movieResponses = mapToResponse(movies);
        return BaseResponse.builder()
                .data(movieResponses)
                .message("success")
                .build();
    }

    @Override
    public BaseResponse getById(String id) {
        Movie movie = movieRepository.getById(id);
        if (movie == null) {
            throw TypeCustomException.MOVIE_NOT_FOUND.getDefault().get();
        }
        return BaseResponse.builder()
                .data(mapToResponse(Collections.singletonList(movie)))
                .message("success")
                .build();
    }

    private List<MovieResponse> mapToResponse (List<Movie> movies) {
        List<MovieResponse> movieResponses = new ArrayList<>();

        List<String> movieIds = movies.stream().map(Movie::getId).collect(Collectors.toList());
        List<Episode> episodes = episodeRepository.findAllByMovieIdIn(movieIds);
        Map<String, List<Episode>> mapEpisode = episodes.stream().collect(groupingBy(Episode::getMovieId));
        movies.forEach(movie -> movieResponses.add(MovieMapper.mapMovie2Response(movie, mapEpisode.get(movie.getId()))));
        return movieResponses;
    }
}
