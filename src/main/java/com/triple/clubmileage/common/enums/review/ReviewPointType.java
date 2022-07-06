package com.triple.clubmileage.common.enums.review;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ReviewPointType {

    CONTENT_POINT("CONTENT_POINT", 1L),
    PHOTO_POINT("PHOTO_POINT", 1L),
    FIRST_PLACE_POINT("FIRST_PLACE_POINT", 1L);

    String label;
    Long point;

}
