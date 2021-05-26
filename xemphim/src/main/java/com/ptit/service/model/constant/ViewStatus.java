package com.ptit.service.model.constant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum ViewStatus {
    NOT_VIEWED(0), VIEWED(1), VIEWING(2);

    private static final Map<Integer, ViewStatus> cached;

    static {
        Map<Integer, ViewStatus> map = new ConcurrentHashMap<>();
        for (ViewStatus viewStatus : ViewStatus.values()) {
            map.put(viewStatus.getCode(), viewStatus);
        }
        cached = map;
    }

    private final int code;

    ViewStatus(int code) {
        this.code = code;
    }

    public static ViewStatus getViewStatus(int code) {
        return cached.get(code);
    }

    public int getCode() {
        return code;
    }
}
