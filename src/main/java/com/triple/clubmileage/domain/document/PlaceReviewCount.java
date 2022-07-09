package com.triple.clubmileage.domain.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndex(name = "place_id_idx", def = "{place_id: 1}", unique = true)
@Document(collection = "PLACE_REVIEW_COUNT")
public class PlaceReviewCount implements Serializable {

    private static final long serialVersionUID = 414377654271917269L;

    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;

    @Field
    private String placeId;

    @Field
    private Long count;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public void increaseCount() {
        this.count += 1;
    }

    public void decreaseCount() {
        this.count -= 1;
    }
}
