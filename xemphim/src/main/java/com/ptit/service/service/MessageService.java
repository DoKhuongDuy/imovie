package com.ptit.service.service;

import javax.servlet.http.HttpServletRequest;

public interface MessageService {
    String getMessage(String code, HttpServletRequest request);
}
