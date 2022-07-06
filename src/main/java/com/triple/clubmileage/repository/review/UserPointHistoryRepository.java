package com.triple.clubmileage.repository.review;

import com.triple.clubmileage.domain.entity.UserPointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPointHistoryRepository extends JpaRepository<UserPointHistory, Long> {
}
