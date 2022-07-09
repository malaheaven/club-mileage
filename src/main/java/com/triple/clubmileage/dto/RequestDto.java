package com.triple.clubmileage.dto;

import com.triple.clubmileage.common.enums.PointType;
import com.triple.clubmileage.common.enums.review.ReviewType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


public class RequestDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Review {
        @NotNull
        private PointType type;

        @NotNull(message = "SELECT : [ ADD, MOD, DELETE ]")
        private ReviewType action;

        @NotNull
        private String reviewId;

        private String content;

        @Builder.Default
        private List<String> attachedPhotoIds = new ArrayList<>();

        @NotNull
        private String userId;

        @NotNull
        private String placeId;
    }

}
