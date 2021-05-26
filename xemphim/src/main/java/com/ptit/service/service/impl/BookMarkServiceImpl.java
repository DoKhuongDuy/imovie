package com.ptit.service.service.impl;

import com.ptit.service.common.BaseResponse;
import com.ptit.service.interceptor.AuthInterceptor;
import com.ptit.service.model.entity.BookMark;
import com.ptit.service.model.exception.TypeCustomException;
import com.ptit.service.model.request.BookMarkRequest;
import com.ptit.service.model.response.BookMarkResponse;
import com.ptit.service.repository.BookMarkRepository;
import com.ptit.service.service.BookMarkService;
import com.ptit.service.service.mapper.BookMarkMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookMarkServiceImpl implements BookMarkService {

    private final BookMarkRepository bookMarkRepository;

    private final AuthInterceptor authInterceptor;

    @Override
    public BaseResponse addBookMark(HttpServletRequest request, BookMarkRequest bookMarkRequest) {
        String userId = authInterceptor.getAccountID(request);
        Optional<BookMark> oldBookMark = bookMarkRepository.findByUserIdAndMovieId(userId, bookMarkRequest.getMovieId());
        if (oldBookMark.isPresent()) {
            throw TypeCustomException.BOOK_MARK_EXISTED.getDefault().get();
        }
        BookMark bookMark = bookMarkRepository.save(BookMarkMapper.mapRequest2Entity(userId, bookMarkRequest));
        return BaseResponse.builder()
                .data(bookMark)
                .message("success")
                .build();
    }

    @Override
    public BaseResponse removeBookMark(HttpServletRequest request, String movieId) {
        String userId = authInterceptor.getAccountID(request);
        Optional<BookMark> bookMark = bookMarkRepository.findByUserIdAndMovieId(userId, movieId);
        if (bookMark.isEmpty()) {
            throw TypeCustomException.BOOK_MARK_NOT_FOUND.getDefault().get();
        }
        bookMarkRepository.delete(bookMark.get());
        return BaseResponse.builder()
                .message("success")
                .build();
    }

    @Override
    public BaseResponse getBookMark(HttpServletRequest request) {
        String userId = authInterceptor.getAccountID(request);
        List<BookMark> bookMarks = bookMarkRepository.findByUserId(userId);
        List<BookMarkResponse> bookMarkResponses = bookMarks.stream().map(BookMarkMapper::mapEntity2Response).collect(Collectors.toList());
        return BaseResponse.builder()
                .data(bookMarkResponses)
                .message("success")
                .build();
    }

    @Override
    public BaseResponse getBookMarkByMovieId(HttpServletRequest request, String movieId) {
        BaseResponse baseResponse = new BaseResponse();
        String userId = authInterceptor.getAccountID(request);
        Optional<BookMark> bookMark = bookMarkRepository.findByUserIdAndMovieId(userId, movieId);
        if (bookMark.isPresent()) {
            baseResponse.setData(true);
        } else {
            baseResponse.setData(false);
        }
        baseResponse.setMessage("success");
        return baseResponse;
    }
}
