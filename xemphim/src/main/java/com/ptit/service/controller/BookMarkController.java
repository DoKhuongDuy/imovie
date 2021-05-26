package com.ptit.service.controller;

import com.ptit.service.common.BaseResponse;
import com.ptit.service.model.request.BookMarkRequest;
import com.ptit.service.service.BookMarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/bookmarks")
@Validated
@RequiredArgsConstructor
public class BookMarkController {

    private final BookMarkService bookMarkService;

    @PostMapping
    public BaseResponse addBookMark(HttpServletRequest request, @Valid @RequestBody BookMarkRequest bookMarkRequest) {
        return bookMarkService.addBookMark(request, bookMarkRequest);
    }

    @DeleteMapping
    public BaseResponse removeBookMark(HttpServletRequest request, @RequestParam String movieId) {
        return bookMarkService.removeBookMark(request, movieId);
    }

    @GetMapping
    public BaseResponse getBookMark(HttpServletRequest request) {
        return bookMarkService.getBookMark(request);
    }

    @GetMapping("/movieId")
    public BaseResponse getBookMarkByMovieId(HttpServletRequest request, @RequestParam String movieId) {
        return bookMarkService.getBookMarkByMovieId(request, movieId);
    }
}
