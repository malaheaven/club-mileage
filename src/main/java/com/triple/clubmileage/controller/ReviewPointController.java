package com.triple.clubmileage.controller;

import com.triple.clubmileage.dto.RequestDto;
import com.triple.clubmileage.dto.ResponseDto;
import com.triple.clubmileage.service.ReviewPointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ReviewPointController {

    private final ReviewPointService reviewPointService;

    @PostMapping("/events")
    public ResponseDto<?> addPoint(@Valid @RequestBody RequestDto.Review requestDto) {
        reviewPointService.addPoint(requestDto);
        return ResponseDto.success();
    }

    @GetMapping("/events/{userId}/point")
    public ResponseDto<?> searchUserPoint(@PathVariable("userId") String userId) {
        return ResponseDto.success(reviewPointService.searchUserPoint(userId));
    }

}
