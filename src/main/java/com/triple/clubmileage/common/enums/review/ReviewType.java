package com.triple.clubmileage.common.enums.review;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.stream.Stream;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ReviewType {
    ADD("ADD"),
    MOD("MOD"),
    DELETE("DELETE");

    String label;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ReviewType findByLabel(String action) {
        return Stream.of(ReviewType.values())
                .filter(c -> c.label.equals(action))
                .findFirst()
                .orElse(null);
    }
}
