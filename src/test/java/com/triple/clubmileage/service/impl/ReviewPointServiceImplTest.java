package com.triple.clubmileage.service.impl;

import com.triple.clubmileage.common.enums.PointType;
import com.triple.clubmileage.common.enums.review.ReviewType;
import com.triple.clubmileage.dto.RequestDto;
import com.triple.clubmileage.repository.UserPointRepository;
import com.triple.clubmileage.repository.review.PlaceReviewCountRepository;
import com.triple.clubmileage.repository.review.UserPointHistoryRepository;
import com.triple.clubmileage.repository.review.UserReviewHistoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReviewPointServiceImplTest {

    @Autowired
    ReviewPointServiceImpl reviewPointService;

    @Autowired
    UserPointRepository userPointRepository;

    @Autowired
    UserPointHistoryRepository userPointHistoryRepository;

    @Autowired
    UserReviewHistoryRepository userReviewHistoryRepository;

    @Autowired
    PlaceReviewCountRepository placeReviewCountRepository;

    @AfterEach
    void delete() {
        userPointHistoryRepository.deleteAll();
        userPointRepository.deleteAll();
        userReviewHistoryRepository.deleteAll();
        placeReviewCountRepository.deleteAll();
    }

    @Test
    @DisplayName("포인트 증감이 있을 때마다 이력이 남아야한다: 리뷰 등록 추가, 첫리뷰")
    void pointTest1() {
        String aUser = "user1";

        RequestDto.Review review = RequestDto.Review.builder()
                .userId(aUser)
                .type(PointType.REVIEW)
                .action(ReviewType.ADD)
                .reviewId("review1")
                .attachedPhotoIds(Arrays.asList("image1"))
                .content("content")
                .placeId("place1")
                .build();

        reviewPointService.addPoint(review);
        assertThat(userPointRepository.findByUserId(aUser).isPresent()).isEqualTo(true);
        assertThat(userPointRepository.findByUserId(aUser).get().getAccumulatedPoint()).isEqualTo(3L);
    }

    @Test
    @DisplayName("포인트 증감이 있을 때마다 이력이 남아야한다: 리뷰 등록 추가, 글 보너스 X")
    void pointTest2() {
        String aUser = "user1";

        RequestDto.Review review1 = RequestDto.Review.builder()
                .userId(aUser)
                .type(PointType.REVIEW)
                .action(ReviewType.ADD)
                .reviewId("review1")
                .attachedPhotoIds(Arrays.asList("image1"))
                .placeId("place1")
                .build();

        reviewPointService.addPoint(review1);
        assertThat(userPointRepository.findByUserId(aUser).get().getAccumulatedPoint()).isEqualTo(2L);

    }

    @Test
    @DisplayName("포인트 증감이 있을 때마다 이력이 남아야한다: 리뷰 등록 추가, 사진 보너스 X")
    void pointTest3() {
        String aUser = "user1";

        RequestDto.Review review1 = RequestDto.Review.builder()
                .userId(aUser)
                .type(PointType.REVIEW)
                .action(ReviewType.ADD)
                .reviewId("review1")
                .content("content")
                .placeId("place1")
                .build();

        reviewPointService.addPoint(review1);
        assertThat(userPointRepository.findByUserId(aUser).get().getAccumulatedPoint()).isEqualTo(2L);
    }

    @Test
    @DisplayName("포인트 증감이 있을 때마다 이력이 남아야한다: 리뷰 등록 추가 글, 사진 보너스 X")
    void pointTest4() {
        String aUser = "user1";

        RequestDto.Review review1 = RequestDto.Review.builder()
                .userId(aUser)
                .type(PointType.REVIEW)
                .action(ReviewType.ADD)
                .reviewId("review1")
                .placeId("place1")
                .build();

        reviewPointService.addPoint(review1);
        assertThat(userPointRepository.findByUserId(aUser).get().getAccumulatedPoint()).isEqualTo(1L);
    }

    @Test
    @DisplayName("포인트 증감이 있을 때마다 이력이 남아야한다: 리뷰 등록 추가, 첫리뷰가 아님")
    void pointTest5() {
        String aUser = "user1";

        RequestDto.Review review1 = RequestDto.Review.builder()
                .userId(aUser)
                .type(PointType.REVIEW)
                .action(ReviewType.ADD)
                .reviewId("review1")
                .attachedPhotoIds(Arrays.asList("image1"))
                .content("content")
                .placeId("place1")
                .build();

        reviewPointService.addPoint(review1);

        String bUser = "user2";
        RequestDto.Review review2 = RequestDto.Review.builder()
                .userId(bUser)
                .type(PointType.REVIEW)
                .action(ReviewType.ADD)
                .reviewId("review1")
                .attachedPhotoIds(Arrays.asList("image1"))
                .content("content")
                .placeId("place1")
                .build();

        reviewPointService.addPoint(review2);
        assertThat(userPointRepository.findByUserId(bUser).get().getAccumulatedPoint()).isEqualTo(2L);
    }


    @Test
    @DisplayName("리뷰를 작성했다가 삭제하면 해당 리뷰로 부여한 내용 점수와 보너스 점수는 회수한다.")
    void withdrawPointTest() {
        String aUser = "user1";
        RequestDto.Review addReview = RequestDto.Review.builder()
                .userId(aUser)
                .type(PointType.REVIEW)
                .action(ReviewType.ADD)
                .reviewId("review1")
                .attachedPhotoIds(Arrays.asList("image1"))
                .content("content")
                .placeId("place1")
                .build();

        reviewPointService.addPoint(addReview);
        assertThat(userPointRepository.findByUserId(aUser).get().getAccumulatedPoint()).isEqualTo(3L);


        RequestDto.Review deleteReview = RequestDto.Review.builder()
                .userId(aUser)
                .type(PointType.REVIEW)
                .action(ReviewType.DELETE)
                .reviewId("review1")
                .attachedPhotoIds(Arrays.asList("image1"))
                .content("content")
                .placeId("place1")
                .build();
        reviewPointService.addPoint(deleteReview);
        assertThat(userPointRepository.findByUserId(aUser).get().getAccumulatedPoint()).isEqualTo(0L);
    }

    @Test
    @DisplayName("리뷰 수정: 글만 작성한 리뷰에 사진을 추가하면 1점 부여")
    void updateReviewTest1() {
        String aUser = "user1";
        RequestDto.Review addReview = RequestDto.Review.builder()
                .userId(aUser)
                .type(PointType.REVIEW)
                .action(ReviewType.ADD)
                .reviewId("review1")
                .content("content")
                .placeId("place1")
                .build();

        reviewPointService.addPoint(addReview);
        assertThat(userPointRepository.findByUserId(aUser).get().getAccumulatedPoint()).isEqualTo(2L);


        RequestDto.Review modifyReview = RequestDto.Review.builder()
                .userId(aUser)
                .type(PointType.REVIEW)
                .action(ReviewType.MOD)
                .reviewId("review1")
                .attachedPhotoIds(Arrays.asList("image1"))
                .content("content")
                .placeId("place1")
                .build();
        reviewPointService.addPoint(modifyReview);
        assertThat(userPointRepository.findByUserId(aUser).get().getAccumulatedPoint()).isEqualTo(3L);
    }

    @Test
    @DisplayName("리뷰 수정: 글과 사진이 있는 리뷰에서 사진을 모두 삭제하면 1점 회수")
    void updateReviewTest2() {

        String aUser = "user1";
        RequestDto.Review addReview = RequestDto.Review.builder()
                .userId(aUser)
                .type(PointType.REVIEW)
                .action(ReviewType.ADD)
                .reviewId("review1")
                .attachedPhotoIds(Arrays.asList("image1"))
                .content("content")
                .placeId("place1")
                .build();

        reviewPointService.addPoint(addReview);

        assertThat(userPointRepository.findByUserId(aUser).get().getAccumulatedPoint()).isEqualTo(3L);


        RequestDto.Review modifyReview = RequestDto.Review.builder()
                .userId(aUser)
                .type(PointType.REVIEW)
                .action(ReviewType.MOD)
                .reviewId("review1")
                .content("content")
                .placeId("place1")
                .build();
        reviewPointService.addPoint(modifyReview);
        assertThat(userPointRepository.findByUserId(aUser).get().getAccumulatedPoint()).isEqualTo(2L);
    }

    @Test
    @DisplayName("리뷰 삭제: 사용자 입장테서 첫 리뷰 일때: 어떤 장소에 사용자가 A 점수를 남겼다가 삭제하고, " +
            "삭제된 이후 사용자 B가 리뷰를 남기면 사용자 B에게 보너스 점수를 부여")
    void deleteReviewTest1() {
        String aUser = "user1";
        RequestDto.Review addReviewOfaUser = RequestDto.Review.builder()
                .userId(aUser)
                .type(PointType.REVIEW)
                .action(ReviewType.ADD)
                .reviewId("review1")
                .attachedPhotoIds(Arrays.asList("image1"))
                .content("content")
                .placeId("place1")
                .build();

        reviewPointService.addPoint(addReviewOfaUser);
        assertThat(userPointRepository.findByUserId(aUser).get().getAccumulatedPoint()).isEqualTo(3L);

        RequestDto.Review deleteReviewOfaUser = RequestDto.Review.builder()
                .userId(aUser)
                .type(PointType.REVIEW)
                .action(ReviewType.DELETE)
                .reviewId("review1")
                .attachedPhotoIds(Arrays.asList("image1"))
                .content("content")
                .placeId("place1")
                .build();

        reviewPointService.addPoint(deleteReviewOfaUser);
        assertThat(userPointRepository.findByUserId(aUser).get().getAccumulatedPoint()).isEqualTo(0L);


        String bUser = "user2";
        RequestDto.Review addReviewOfbUser = RequestDto.Review.builder()
                .userId(bUser)
                .type(PointType.REVIEW)
                .action(ReviewType.ADD)
                .reviewId("review2")
                .attachedPhotoIds(Arrays.asList("image1"))
                .content("content")
                .placeId("place1")
                .build();
        reviewPointService.addPoint(addReviewOfbUser);
        assertThat(userPointRepository.findByUserId(bUser).get().getAccumulatedPoint()).isEqualTo(3L);
    }

    @Test
    @DisplayName("리뷰 삭제: 어떤 장소에 사용자 A가 리뷰를 남겼다가 삭제했는데, " +
            "삭제되기 이전 사용자 B가 리뷰를 남기면 사용자 B에게 보너스 점수를 부여하지 않음")
    void deleteReviewTest2() {

        String aUser = "user1";
        RequestDto.Review addReviewOfaUser = RequestDto.Review.builder()
                .userId(aUser)
                .type(PointType.REVIEW)
                .action(ReviewType.ADD)
                .reviewId("review1")
                .attachedPhotoIds(Arrays.asList("image1"))
                .content("content")
                .placeId("place1")
                .build();

        reviewPointService.addPoint(addReviewOfaUser);
        assertThat(userPointRepository.findByUserId(aUser).get().getAccumulatedPoint()).isEqualTo(3L);

        String bUser = "user2";
        RequestDto.Review addReviewOfbUser = RequestDto.Review.builder()
                .userId(bUser)
                .type(PointType.REVIEW)
                .action(ReviewType.ADD)
                .reviewId("review2")
                .attachedPhotoIds(Arrays.asList("image1"))
                .content("content")
                .placeId("place1")
                .build();
        reviewPointService.addPoint(addReviewOfbUser);
        assertThat(userPointRepository.findByUserId(bUser).get().getAccumulatedPoint()).isEqualTo(2L);

        RequestDto.Review deleteReviewOfaUser = RequestDto.Review.builder()
                .userId(aUser)
                .type(PointType.REVIEW)
                .action(ReviewType.DELETE)
                .reviewId("review1")
                .attachedPhotoIds(Arrays.asList("image1"))
                .content("content")
                .placeId("place1")
                .build();

        reviewPointService.addPoint(deleteReviewOfaUser);
        assertThat(userPointRepository.findByUserId(aUser).get().getAccumulatedPoint()).isEqualTo(0L);

        // 여전히 bUser의 point는 2
        assertThat(userPointRepository.findByUserId(bUser).get().getAccumulatedPoint()).isEqualTo(2L);
    }

}
