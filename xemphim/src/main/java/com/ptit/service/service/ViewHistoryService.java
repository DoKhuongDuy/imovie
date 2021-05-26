package com.ptit.service.service;

import com.ptit.service.common.BaseResponse;
import com.ptit.service.model.request.ViewRequest;

import javax.servlet.http.HttpServletRequest;

public interface ViewHistoryService {
    BaseResponse addViewMovie(HttpServletRequest request, ViewRequest viewRequest);

    BaseResponse getViewHistory(HttpServletRequest request, String episodeId);

    BaseResponse getViewHistoryMovie(HttpServletRequest request, String movieId);

    BaseResponse getMovieViewing(HttpServletRequest request);
}
