package com.triple.clubmileage.repository.review;

import com.triple.clubmileage.domain.document.UserReviewHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserReviewHistoryRepository extends MongoRepository<UserReviewHistory, String> {

    Optional<UserReviewHistory> findByPlaceIdAndReviewIdAndUserId(String placeId, String reviewId, String userId);

}
