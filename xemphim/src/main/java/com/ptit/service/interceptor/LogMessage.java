package com.ptit.service.interceptor;

import lombok.Data;

@Data
public class LogMessage {
    private int httpStatus;
    private String httpMethod;
    private String path;
    private String clientIp;
    private String response;
    private String queryString;
    private String header;
    private String requestBody;
}
