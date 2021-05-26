package com.ptit.service.interceptor;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Log4j2
public class LoggableDispatcherServlet extends DispatcherServlet {


    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }
        if (!(response instanceof ContentCachingResponseWrapper)) {
            response = new ContentCachingResponseWrapper(response);
        }
        HandlerExecutionChain handler = getHandler(request);

        try {
            super.doDispatch(request, response);
        } finally {
            logRequest((ContentCachingRequestWrapper) request, (ContentCachingResponseWrapper) response, handler);
            updateResponse(response);
        }
    }

    private void logRequest(ContentCachingRequestWrapper requestToCache, ContentCachingResponseWrapper responseToCache, HandlerExecutionChain handler) throws IOException {
        LogMessage logMessage = new LogMessage();
        logMessage.setHttpStatus(responseToCache.getStatus());
        logMessage.setHttpMethod(requestToCache.getMethod());
        logMessage.setPath(requestToCache.getRequestURI());
        logMessage.setQueryString(requestToCache.getQueryString());
        String header = StreamSupport.stream(Spliterators.spliteratorUnknownSize(Collections.list(requestToCache.getHeaderNames()).iterator(),
                Spliterator.ORDERED),
                false)
                .map(name -> name + "=" + requestToCache.getHeader(name))
                .collect(Collectors.joining(";"));
        logMessage.setHeader(header);
        byte[] content = requestToCache.getContentAsByteArray();
        if (content.length > 0) {
            logMessage.setRequestBody(logContent(content, requestToCache.getContentType(), requestToCache.getCharacterEncoding()));
        }
        logMessage.setClientIp(requestToCache.getRemoteAddr());
        logMessage.setResponse(getResponsePayload(responseToCache));
        log.info(logMessage);
    }

    private static final List<MediaType> VISIBLE_TYPES = Arrays.asList(
            MediaType.valueOf("text/*"),
            MediaType.APPLICATION_FORM_URLENCODED,
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_XML,
            MediaType.valueOf("application/*+json"),
            MediaType.valueOf("application/*+xml"),
            MediaType.MULTIPART_FORM_DATA
    );

    private static String logContent(byte[] content, String contentType, String contentEncoding) {
        MediaType mediaType = MediaType.valueOf(contentType);
        boolean visible = VISIBLE_TYPES.stream().anyMatch(visibleType -> visibleType.includes(mediaType));
        if (visible) {
            try {
                String contentString = new String(content, contentEncoding);
                return String.join("", contentString.split("\r\n|\r|\n"));
            } catch (UnsupportedEncodingException e) {
                return "";
            }
        } else {
            return "";
        }
    }

    private String getResponsePayload(HttpServletResponse response) {
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (wrapper != null) {

            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                int length = Math.min(buf.length, 5120);
                try {
                    return new String(buf, 0, length, wrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException ex) {
                    // NOOP
                }
            }
        }
        return "[unknown]";
    }

    private void updateResponse(HttpServletResponse response) throws IOException {
        ContentCachingResponseWrapper responseWrapper =
                WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        assert responseWrapper != null;
        responseWrapper.copyBodyToResponse();
    }

}
