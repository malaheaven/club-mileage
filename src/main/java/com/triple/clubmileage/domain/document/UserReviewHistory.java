package com.triple.clubmileage.domain.document;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;

@Getter
@CompoundIndex(name = "user_review_history_idx", def = "{review_id : 1, place_id: 1}")
@Document
public class UserReviewHistory {

    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;

    @Field
    private String reviewId;

    @Field
    private String placeId;

    @Field
    private String userId;

    @Field
    private Boolean content;

    @Field
    private Boolean photo;

    @Field
    private Boolean firstReview;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;
}
