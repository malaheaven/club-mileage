package com.triple.clubmileage.domain.entity;

import com.triple.clubmileage.common.enums.PointType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(indexes = {@Index(name = "user_id", columnList = "userId")})
public class UserPointHistory extends BaseTimeEntity implements Serializable {

    private static final long serialVersionUID = -5426506274202081362L;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userPoint", foreignKey = @ForeignKey(name = "fk_user_point_history_user_point"))
    private UserPoint userPoint;

}
