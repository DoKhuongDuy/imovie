package com.ptit.service.config;

import com.ptit.service.common.BaseResponse;
import com.ptit.service.model.exception.CustomException;
import com.ptit.service.model.exception.TypeCustomException;
import com.ptit.service.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@ControllerAdvice
@Log4j2
@RequiredArgsConstructor
public class ExceptionAdvice {

    private final MessageService messageService;

    private String requestToString(HttpServletRequest request) {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        String header = StreamSupport.stream(Spliterators.spliteratorUnknownSize(Collections.list(request.getHeaderNames()).iterator(),
                Spliterator.ORDERED),
                false)
                .map(name -> name + "=" + request.getHeader(name))
                .collect(Collectors.joining(";"));
        StringBuilder builder = new StringBuilder();
        builder.append(method).append(" ").append(uri);
        if (!StringUtils.isEmpty(queryString)) {
            builder.append("?").append(queryString);
        }
        builder.append(";headers=[").append(header).append("]");
        return builder.toString();
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(
            CustomException e,
            HttpServletRequest request,
            HttpServletResponse response) {
        TypeCustomException typeCustomException = e.getTypeCustomException();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setErrorCode(1);
        baseResponse.setMessage(messageService.getMessage(typeCustomException.getMessage(), request));
        log.error(requestToString(request));
        log.error(typeCustomException.getMessage(), e);
        return ResponseEntity.status(typeCustomException.getHttpStatus()).body(baseResponse);
    }
}
