package com.ptit.service.service.mapper;

import com.ptit.service.model.constant.ViewStatus;
import com.ptit.service.model.entity.ViewHistory;
import com.ptit.service.model.exception.TypeCustomException;
import com.ptit.service.model.request.ViewRequest;
import com.ptit.service.model.response.ViewHistoryResponse;

public class ViewMapper {

    public static ViewHistory mapRequest2Entity(String userId, String movieId, ViewRequest viewRequest) {

        ViewStatus viewStatus = ViewStatus.getViewStatus(viewRequest.getViewStatus());
        if (viewStatus == null) {
            throw TypeCustomException.VIEW_STATUS_NOT_FOUND.getDefault().get();
        }

        return ViewHistory.builder()
                .userId(userId)
                .movieId(movieId)
                .episodeId(viewRequest.getEpisodeId())
                .viewStatus(viewRequest.getViewStatus())
                .currentViewing(viewRequest.getCurrentViewing())
                .build();
    }

    public static ViewHistoryResponse mapEntity2Response(ViewHistory viewHistory) {
        ViewStatus viewStatus = ViewStatus.getViewStatus(viewHistory.getViewStatus());
        if (viewStatus == null) {
            throw TypeCustomException.VIEW_STATUS_NOT_FOUND.getDefault().get();
        }
        return ViewHistoryResponse.builder()
                .userId(viewHistory.getUserId())
                .movieId(viewHistory.getMovieId())
                .episodeId(viewHistory.getEpisodeId())
                .viewStatus(viewStatus.toString())
                .currentViewing(viewHistory.getCurrentViewing())
                .build();
    }
}
