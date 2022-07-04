package com.triple.clubmileage.domain.entity;

import com.triple.clubmileage.common.enums.PointType;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(indexes = {
        @Index(name = "unique_user_id", columnList = "userId", unique = true)
})
public class UserPointHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private Long event;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private PointType pointType;

    private String pointTypeDetail;

    @ManyToOne
    @JoinColumn(name = "userPoint", foreignKey = @ForeignKey(name = "fk_user_point_history_user_point"))
    private UserPoint userPoint;

}
