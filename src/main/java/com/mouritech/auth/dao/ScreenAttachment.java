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
@Table(name = "SCREEN_ATTACHMENT")
public class ScreenAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SCREEN_ATTACHMENT_SEQ")
    @SequenceGenerator(sequenceName = "SCREEN_ATTACHMENT_SEQ", allocationSize = 1, name = "SCREEN_ATTACHMENT_SEQ")
    @Column(name = "SCREEN_ATTACHMENT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "SCREEN_ID")
    Screen screen;

    @Column(name = "ENTITY_ID")
    private Long entityId;

    @Column(name = "ATTACHMENT_FILE_NAME")
    private String attachmentFileName;

    @Column(name = "ATTACHMENT_DMS_FILE_NAME")
    private String attachmentDmsFileName;

    @Column(name = "ATTACHMENT_FILE_VERSION")
    private String attachmentFileVersion;

    @Column(name = "ATTACHMENT_FILE_TYPE")
    private String attachmentFileType;

    @Column(name = "ATTACHMENT_UPLOAD_FROM")
    private String attachmentUploadFrom;

    @Column(name = "ATTACHMENT_DMS_NODE_ID")
    private String attachmentDmsNodeId;

    @Column(name = "ATTACHMENT_DMS_PARENT_NODE_ID")
    private String attachmentDmsParentNodeId;

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
