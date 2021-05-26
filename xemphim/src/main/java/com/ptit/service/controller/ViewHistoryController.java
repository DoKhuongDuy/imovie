package com.ptit.service.controller;


import com.ptit.service.common.BaseResponse;
import com.ptit.service.model.request.ViewRequest;
import com.ptit.service.service.ViewHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/view")
@Validated
@RequiredArgsConstructor
public class ViewHistoryController {

    private final ViewHistoryService viewHistoryService;

    @GetMapping("/episode")
    public BaseResponse getViewEpisodeHistory(HttpServletRequest request,
                                       @RequestParam String episodeId) {
        return viewHistoryService.getViewHistory(request, episodeId);
    }

    @GetMapping
    public BaseResponse getViewHistory(HttpServletRequest request,
                                       @RequestParam String movieId) {
        return viewHistoryService.getViewHistoryMovie(request, movieId);
    }

    @GetMapping("/viewing")
    public BaseResponse getMovieViewing(HttpServletRequest request) {
        return viewHistoryService.getMovieViewing(request);
    }

    @PostMapping
    public BaseResponse addViewHistory(HttpServletRequest request, @Valid @RequestBody ViewRequest viewRequest) {
        return viewHistoryService.addViewMovie(request, viewRequest);
    }
}
