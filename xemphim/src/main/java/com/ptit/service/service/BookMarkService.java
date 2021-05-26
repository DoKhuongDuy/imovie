package com.ptit.service.service;

import com.ptit.service.common.BaseResponse;
import com.ptit.service.model.request.BookMarkRequest;

import javax.servlet.http.HttpServletRequest;

public interface BookMarkService {
    BaseResponse addBookMark(HttpServletRequest request, BookMarkRequest bookMarkRequest);

    BaseResponse removeBookMark(HttpServletRequest request, String movieId);

    BaseResponse getBookMark(HttpServletRequest request);

    BaseResponse getBookMarkByMovieId(HttpServletRequest request, String movieId);
}
