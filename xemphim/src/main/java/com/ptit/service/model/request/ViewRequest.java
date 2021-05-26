package com.ptit.service.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewRequest {
    @NotNull
    private String episodeId;
    @NotNull
    private int viewStatus;
    private long currentViewing;
}
