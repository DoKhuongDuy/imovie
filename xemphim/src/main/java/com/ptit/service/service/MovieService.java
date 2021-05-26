package com.ptit.service.service;

import com.ptit.service.common.BaseResponse;

public interface MovieService {
    BaseResponse getAll();

    BaseResponse getByName(String movieName);

    BaseResponse getByType(String type);

    BaseResponse getTop6ByType(String type);

    BaseResponse getTopView();

    BaseResponse getById(String id);
}
