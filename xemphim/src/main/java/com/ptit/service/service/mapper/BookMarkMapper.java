package com.ptit.service.service.mapper;

import com.ptit.service.model.entity.BookMark;
import com.ptit.service.model.request.BookMarkRequest;
import com.ptit.service.model.response.BookMarkResponse;

public class BookMarkMapper {

    public static BookMark mapRequest2Entity(String userId, BookMarkRequest bookMarkRequest) {
        return BookMark.builder()
                .userId(userId)
                .movieId(bookMarkRequest.getMovieId())
                .build();
    }

    public static BookMarkResponse mapEntity2Response(BookMark bookMark) {
        return BookMarkResponse.builder()
                .id(bookMark.getId())
                .userId(bookMark.getUserId())
                .movieId(bookMark.getMovieId())
                .build();
    }
}
