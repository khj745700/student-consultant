package com.consultant.application.entity.staff;

import com.consultant.application.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "staff")
@NoArgsConstructor
@SuperBuilder
@Getter
public class Staff extends BaseEntity {
    @Id
    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StaffSort sort;

    @Column
    @Enumerated(EnumType.STRING)
    private StaffStatus staffStatus;


    public boolean isActive() {
        return staffStatus.equals(StaffStatus.ACTIVE);
    }
}
