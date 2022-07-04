package com.triple.clubmileage.domain.entity;

import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(indexes = {@Index(name = "unique_user_id", columnList = "userId", unique = true)})
public class UserPoint extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private Long accumulatedPoint;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "userPoint")
    List<UserPointHistory> userPointHistoryList = new ArrayList<>();

}
