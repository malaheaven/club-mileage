package com.triple.clubmileage.common.enums.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ReviewType {
    ADD("ADD"),
    MOD("MOD"),
    DELETE("DELETE");

    String label;
}
