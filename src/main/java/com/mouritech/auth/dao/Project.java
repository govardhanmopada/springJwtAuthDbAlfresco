package com.mouritech.auth.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PROJECT")
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROJECT_SEQ")
    @SequenceGenerator(sequenceName = "PROJECT_SEQ", allocationSize = 1, name = "PROJECT_SEQ")
    @Column(name = "PROJECT_ID")
    private Long id;

    @Column(name = "PROJECT_NAME")
    private String projectName;

    @Column(name = "PROJECT_DMS_NODE_ID")
    private String projectDmsNodeId;

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
