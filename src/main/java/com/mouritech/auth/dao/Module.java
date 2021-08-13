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
@Table(name = "PROJECT_MODULE")
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROJECT_MODULE_SEQ")
    @SequenceGenerator(sequenceName = "PROJECT_MODULE_SEQ", allocationSize = 1, name = "PROJECT_MODULE_SEQ")
    @Column(name = "PROJECT_MODULE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PROJECT_ID")
    Project project;

    @Column(name = "MODULE_NAME")
    private String moduleName;

    @Column(name = "MODULE_DESC")
    private String moduleDesc;

    @Column(name = "MODULE_DMS_NODE_ID")
    private String moduleDmsNodeId;

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
