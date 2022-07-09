package com.triple.clubmileage.repository.review;

import com.triple.clubmileage.domain.document.PlaceReviewCount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceReviewCountRepository extends MongoRepository<PlaceReviewCount, String> {

    Optional<PlaceReviewCount> findByPlaceId(String placeId);

}
