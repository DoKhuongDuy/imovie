package com.ptit.service.controller;

import com.ptit.service.common.BaseResponse;
import com.ptit.service.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
@Validated
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public BaseResponse getAll() {
        return movieService.getAll();
    }

    @GetMapping("/name")
    public BaseResponse getByName(@RequestParam String movieName) {
        return movieService.getByName(movieName);
    }

    @GetMapping("/type")
    public BaseResponse getByType(@RequestParam String type) {
        return movieService.getByType(type);
    }

    @GetMapping("/type/top")
    public BaseResponse getTop6ByType(@RequestParam String type) {
        return movieService.getTop6ByType(type);
    }

    @GetMapping("/top")
    public BaseResponse getTop6View() {
        return movieService.getTopView();
    }

    @GetMapping("/{id}")
    public BaseResponse getById(@PathVariable String id) {
        return movieService.getById(id);
    }
}
