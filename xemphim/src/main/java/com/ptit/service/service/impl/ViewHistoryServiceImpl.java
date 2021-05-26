package com.ptit.service.service.impl;

import com.ptit.service.common.BaseResponse;
import com.ptit.service.interceptor.AuthInterceptor;
import com.ptit.service.model.constant.ViewStatus;
import com.ptit.service.model.entity.Episode;
import com.ptit.service.model.entity.Movie;
import com.ptit.service.model.entity.ViewHistory;
import com.ptit.service.model.exception.TypeCustomException;
import com.ptit.service.model.request.ViewRequest;
import com.ptit.service.model.response.MovieResponse;
import com.ptit.service.model.response.ViewHistoryResponse;
import com.ptit.service.repository.EpisodeRepository;
import com.ptit.service.repository.MovieRepository;
import com.ptit.service.repository.ViewHistoryRepository;
import com.ptit.service.service.ViewHistoryService;
import com.ptit.service.service.mapper.MovieMapper;
import com.ptit.service.service.mapper.ViewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class ViewHistoryServiceImpl implements ViewHistoryService {

    private final ViewHistoryRepository viewHistoryRepository;

    private final MovieRepository movieRepository;

    private final EpisodeRepository episodeRepository;

    private final AuthInterceptor authInterceptor;

    @Override
    public BaseResponse addViewMovie(HttpServletRequest request, ViewRequest viewRequest) {
        String userId = authInterceptor.getAccountID(request);
        Episode episode = episodeRepository.getById(viewRequest.getEpisodeId());
        Movie movie = movieRepository.getById(episode.getMovieId());
        if (ViewStatus.getViewStatus(viewRequest.getViewStatus()) == ViewStatus.VIEWED) {
            movie.setView(movie.getView() + 1);
            movieRepository.save(movie);
        }
        ViewHistory viewHistory = ViewMapper.mapRequest2Entity(userId, movie.getId(), viewRequest);
        viewHistoryRepository.save(viewHistory);
        return BaseResponse.builder()
                .data(ViewMapper.mapEntity2Response(viewHistory))
                .message("success")
                .build();
    }

    @Override
    public BaseResponse getViewHistory(HttpServletRequest request, String episodeId) {
        String userId = authInterceptor.getAccountID(request);
        Episode episode = episodeRepository.getById(episodeId);
        Movie movie = movieRepository.getById(episode.getMovieId());
        ViewHistory viewHistory = viewHistoryRepository.findFirstByUserIdAndMovieIdAndEpisodeIdOrderByCreatedAtDesc(userId, movie.getId(), episodeId);
        if (viewHistory == null) {
            throw TypeCustomException.VIEW_HISTORY_NOT_FOUND.getDefault().get();
        }
        return BaseResponse.builder()
                .data(ViewMapper.mapEntity2Response(viewHistory))
                .message("success")
                .build();
    }

    @Override
    public BaseResponse getViewHistoryMovie(HttpServletRequest request, String movieId) {
        String userId = authInterceptor.getAccountID(request);
        List<ViewHistory> viewHistories = viewHistoryRepository.findFirstByUserIdAndMovieIdOrderByCreatedAtDesc(userId, movieId);
        List<ViewHistoryResponse> viewHistoryResponses = viewHistories.stream().map(ViewMapper::mapEntity2Response).collect(Collectors.toList());
        return BaseResponse.builder()
                .data(viewHistoryResponses)
                .message("success")
                .build();
    }

    @Override
    public BaseResponse getMovieViewing(HttpServletRequest request) {
        String userId = authInterceptor.getAccountID(request);
        List<ViewHistory> viewHistories = viewHistoryRepository.findAllByUserIdAndViewStatusIs(userId, ViewStatus.VIEWING.getCode());
        List<String> movieIds = viewHistories.stream().map(ViewHistory::getMovieId).collect(Collectors.toList());
        List<MovieResponse> movieResponses = mapToResponse(movieIds);
        return BaseResponse.builder()
                .data(movieResponses)
                .message("success")
                .build();
    }

    private List<MovieResponse> mapToResponse (List<String> movieIds) {
        List<MovieResponse> movieResponses = new ArrayList<>();

        List<Movie> movies = movieRepository.findAllByIdIn(movieIds);
        List<Episode> episodes = episodeRepository.findAllByMovieIdIn(movieIds);
        Map<String, List<Episode>> mapEpisode = episodes.stream().collect(groupingBy(Episode::getMovieId));
        movies.forEach(movie -> movieResponses.add(MovieMapper.mapMovie2Response(movie, mapEpisode.get(movie.getId()))));
        return movieResponses;
    }


}
