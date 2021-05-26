package com.ptit.service.service.impl;

import com.ptit.service.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageSource messageSource;

    @Override
    public String getMessage(String code, HttpServletRequest request) {
        return messageSource.getMessage(code, null, Locale.ENGLISH);
    }
}
