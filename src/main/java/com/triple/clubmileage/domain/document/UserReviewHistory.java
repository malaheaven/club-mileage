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
@CompoundIndex(name = "user_review_history_idx", def = "{place_id: 1, review_id : 1, user_id: 1}", unique = true)
@Document(collection = "USER_REVIEW_HISTORY")
public class UserReviewHistory implements Serializable {

    private static final long serialVersionUID = 3550288393866215542L;

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
