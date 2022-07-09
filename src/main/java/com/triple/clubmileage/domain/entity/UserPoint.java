package com.triple.clubmileage.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {@Index(name = "unique_user_id", columnList = "userId", unique = true)})
public class UserPoint extends BaseTimeEntity implements Serializable {

    private static final long serialVersionUID = 4704541120013331694L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private Long accumulatedPoint;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Builder.Default
    @OneToMany(mappedBy = "userPoint")
    List<UserPointHistory> userPointHistoryList = new ArrayList<>();


    public void updatePoint(long point) {
        this.accumulatedPoint += point;
    }

}
