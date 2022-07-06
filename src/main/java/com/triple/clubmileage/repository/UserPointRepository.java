package com.triple.clubmileage.repository;

import com.triple.clubmileage.domain.entity.UserPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPointRepository extends JpaRepository<UserPoint, Long> {

   Optional<UserPoint> findByUserId(String userId);
}
