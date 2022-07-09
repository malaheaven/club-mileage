package com.triple.clubmileage.service;

import com.triple.clubmileage.dto.RequestDto;
import com.triple.clubmileage.dto.UserPointResponseDto;

public interface ReviewPointService {

    void addPoint(RequestDto.Review requestDto);

    UserPointResponseDto.UserPointDto searchUserPoint(String userId);

}
