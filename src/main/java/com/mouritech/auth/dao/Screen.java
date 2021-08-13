package com.mouritech.auth.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SCREEN")
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROJECT_MODULE_SCREEN_SEQ")
    @SequenceGenerator(sequenceName = "PROJECT_MODULE_SCREEN_SEQ", allocationSize = 1, name = "PROJECT_MODULE_SCREEN_SEQ")
    @Column(name = "SCREEN_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PROJECT_MODULE_ID")
    Module module;

    @Column(name = "SCREEN_NAME")
    private String screenName;

    @Column(name = "SCREEN_DESC")
    private String screenDesc;

    @Column(name = "SCREEN_DMS_PARENT_NODE_ID")
    private String screenDmsParentNodeId;

    @Column(name = "SCREEN_DMS_NODE_ID")
    private String screenDmsNodeId;

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
