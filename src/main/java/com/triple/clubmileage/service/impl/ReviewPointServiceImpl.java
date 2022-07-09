package com.triple.clubmileage.service.impl;

import com.triple.clubmileage.common.enums.ResponseType;
import com.triple.clubmileage.common.enums.review.ReviewPointType;
import com.triple.clubmileage.common.enums.review.ReviewType;
import com.triple.clubmileage.domain.document.PlaceReviewCount;
import com.triple.clubmileage.domain.document.UserReviewHistory;
import com.triple.clubmileage.domain.entity.UserPoint;
import com.triple.clubmileage.domain.entity.UserPointHistory;
import com.triple.clubmileage.dto.RequestDto;
import com.triple.clubmileage.dto.UserPointResponseDto;
import com.triple.clubmileage.exception.DataNotFoundException;
import com.triple.clubmileage.repository.UserPointRepository;
import com.triple.clubmileage.repository.review.PlaceReviewCountRepository;
import com.triple.clubmileage.repository.review.UserPointHistoryRepository;
import com.triple.clubmileage.repository.review.UserReviewHistoryRepository;
import com.triple.clubmileage.service.ReviewPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class ReviewPointServiceImpl implements ReviewPointService {

    private final UserPointRepository userPointRepository;
    private final UserPointHistoryRepository userPointHistoryRepository;
    private final UserReviewHistoryRepository userReviewHistoryRepository;
    private final PlaceReviewCountRepository placeReviewCountRepository;

    private Boolean checkExistUserReviewHistory(RequestDto.Review requestDto) {
        return userReviewHistoryRepository
                .findByPlaceIdAndReviewIdAndUserId(requestDto.getPlaceId(), requestDto.getReviewId(), requestDto.getUserId())
                .isPresent();
    }

    private UserPoint getUserPoint(String userId) {
        return userPointRepository.findByUserId(userId)
                .orElseGet(() -> UserPoint.builder()
                        .userId(userId)
                        .accumulatedPoint(0L)
                        .build());
    }

    private PlaceReviewCount getPlaceReviewCount(String placeId) {
        return placeReviewCountRepository.findByPlaceId(placeId)
                .orElseGet(() -> PlaceReviewCount.builder()
                        .placeId(placeId)
                        .count(0L)
                        .build());
    }

    private UserReviewHistory getUserReviewHistory(RequestDto.Review requestDto) {
        return userReviewHistoryRepository
                .findByPlaceIdAndReviewIdAndUserId(requestDto.getPlaceId(), requestDto.getReviewId(), requestDto.getUserId())
                .orElseThrow(() -> DataNotFoundException.from(ResponseType.REVIEW_NOT_FOUND));
    }

    @Override
    public void addPoint(RequestDto.Review requestDto) {

        switch (requestDto.getAction()) {
            case ADD:
                addReview(requestDto);
                break;
            case MOD:
                modifyReview(requestDto);
                break;
            case DELETE:
                deleteReview(requestDto);
                break;
        }
    }

    @Transactional
    public void addReview(RequestDto.Review requestDto) {

        UserPoint userPoint = getUserPoint(requestDto.getUserId());

        if (checkExistUserReviewHistory(requestDto)) return;

        PlaceReviewCount placeReviewCount = getPlaceReviewCount(requestDto.getPlaceId());
        boolean existContents = StringUtils.hasText(requestDto.getContent());
        boolean emptyPhotos = requestDto.getAttachedPhotoIds().isEmpty();

        UserReviewHistory.UserReviewHistoryBuilder userReviewHistory = UserReviewHistory.builder()
                .reviewId(requestDto.getReviewId())
                .placeId(requestDto.getPlaceId())
                .userId(requestDto.getUserId());


        if (placeReviewCount.getCount().equals(0L)) {
            userReviewHistory.firstReview(Boolean.TRUE);
            userPoint.updatePoint(ReviewPointType.FIRST_PLACE_POINT.getPoint());
        }

        if (existContents) {
            userReviewHistory.content(Boolean.TRUE);
            userPoint.updatePoint(ReviewPointType.CONTENT_POINT.getPoint());
        }

        if (!emptyPhotos) {
            userReviewHistory.photo(Boolean.TRUE);
            userPoint.updatePoint(ReviewPointType.PHOTO_POINT.getPoint());
        }


        userPointRepository.save(userPoint);
        userPointHistoryRepository.save(UserPointHistory.builder()
                .userId(requestDto.getUserId())
                .event(userPoint.getAccumulatedPoint())
                .pointType(requestDto.getType())
                .pointTypeDetail(ReviewType.ADD.getLabel())
                .userPoint(userPoint)
                .build());

        userReviewHistoryRepository.save(userReviewHistory.build());

        placeReviewCount.increaseCount();
        placeReviewCountRepository.save(placeReviewCount);
    }

    @Transactional
    public void modifyReview(RequestDto.Review requestDto) {

        long point = 0;

        UserReviewHistory userReviewHistory = getUserReviewHistory(requestDto);

        UserPoint userPoint = getUserPoint(requestDto.getUserId());

        // 변경 이력 체크: 변경 된 이력이 없다면 return
        if (userReviewHistory.getContent() == StringUtils.hasText(requestDto.getContent())
                && userReviewHistory.getPhoto() == (!requestDto.getAttachedPhotoIds().isEmpty())) {
            return;
        }

        if (StringUtils.hasText(requestDto.getContent()) != userReviewHistory.getContent()) {
            userReviewHistory.updateContent(!userReviewHistory.getContent());

            if (StringUtils.hasText(requestDto.getContent())) {
                userReviewHistory.updateContent(Boolean.TRUE);
                point += ReviewPointType.CONTENT_POINT.getPoint();
            } else {
                userReviewHistory.updateContent(Boolean.FALSE);
                point -= ReviewPointType.CONTENT_POINT.getPoint();
            }
        }

        if ((!requestDto.getAttachedPhotoIds().isEmpty()) != userReviewHistory.getPhoto()) {
            userReviewHistory.updatePhoto(!userReviewHistory.getPhoto());

            if ((!requestDto.getAttachedPhotoIds().isEmpty())) {
                userReviewHistory.updatePhoto(Boolean.TRUE);
                point += ReviewPointType.PHOTO_POINT.getPoint();
            } else {
                userReviewHistory.updatePhoto(Boolean.FALSE);
                point -= ReviewPointType.PHOTO_POINT.getPoint();
            }
        }

        if ((userPoint.getAccumulatedPoint() + point) < 0) {
            point = -userPoint.getAccumulatedPoint();
        }

        userPoint.updatePoint(point);
        userPointRepository.save(userPoint);

        userPointHistoryRepository.save(UserPointHistory.builder()
                .userId(requestDto.getUserId())
                .event(point)
                .pointType(requestDto.getType())
                .pointTypeDetail(ReviewType.MOD.getLabel())
                .userPoint(userPoint)
                .build());

        userReviewHistoryRepository.save(userReviewHistory);
    }

    @Transactional
    public void deleteReview(RequestDto.Review requestDto) {

        long point = 0L;

        UserReviewHistory userReviewHistory = getUserReviewHistory(requestDto);

        UserPoint userPoint = getUserPoint(requestDto.getUserId());

        if (userReviewHistory.getFirstReview()) point += ReviewPointType.FIRST_PLACE_POINT.getPoint();

        if (userReviewHistory.getContent()) point += ReviewPointType.CONTENT_POINT.getPoint();

        if (userReviewHistory.getPhoto()) point += ReviewPointType.PHOTO_POINT.getPoint();

        if (userPoint.getAccumulatedPoint() < point) point = userPoint.getAccumulatedPoint();


        userPoint.updatePoint(-point);
        userPointRepository.save(userPoint);
        userPointHistoryRepository.save(UserPointHistory.builder()
                .userId(requestDto.getUserId())
                .event(-point)
                .pointType(requestDto.getType())
                .pointTypeDetail(ReviewType.DELETE.getLabel())
                .userPoint(userPoint)
                .build());


        userReviewHistoryRepository.delete(userReviewHistory);

        PlaceReviewCount placeReviewCount = getPlaceReviewCount(requestDto.getPlaceId());
        placeReviewCount.decreaseCount();
        placeReviewCountRepository.save(placeReviewCount);

    }


    @Override
    public UserPointResponseDto.UserPointDto searchUserPoint(String userId) {
        return UserPointResponseDto.UserPointDto.from(getUserPoint(userId));
    }

}
