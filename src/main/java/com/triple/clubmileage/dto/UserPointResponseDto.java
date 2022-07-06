package com.triple.clubmileage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.triple.clubmileage.domain.entity.UserPoint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserPointResponseDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserPointDto {

        private String userId;

        @JsonProperty("point")
        private Long accumulatedPoint;

        public static UserPointDto from(UserPoint userPoint) {
            return UserPointDto.builder()
                    .userId(userPoint.getUserId())
                    .accumulatedPoint(userPoint.getAccumulatedPoint())
                    .build();
        }
    }

}
