package com.mouritech.auth.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_ROLE")
public class UserRole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ROLE_SEQ")
    @SequenceGenerator(sequenceName = "USER_ROLE_SEQ", allocationSize = 1, name = "USER_ROLE_SEQ")
    @Column(name = "USER_ROLE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "APP_USER_ID")
    User User;

    @ManyToOne
    @JoinColumn(name = "APP_ROLE_ID")
    Role role;

    @Column(name = "IS_DELETED")
    private Integer isDeleted;

    @Column(name = "CREATED_BY")
    private Integer createdBy;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @Column(name = "CREATION_DATE")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "UPDATE_DATE")
    @UpdateTimestamp
    private LocalDateTime updateDate;
}
