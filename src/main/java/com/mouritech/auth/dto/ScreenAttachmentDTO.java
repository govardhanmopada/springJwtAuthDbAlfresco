package com.mouritech.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ScreenAttachmentDTO {
    private Long id;
    @JsonInclude(Include.NON_NULL)
    private ScreenDTO screen;
    private Long entityId;
    private MultipartFile file;
    private String attachmentFileName;
    private String attachmentDmsFileName;
    private String attachmentFileVersion;
    private String attachmentFileType;
    private String attachmentUploadFrom;
    private String attachmentDmsNodeId;
    private String attachmentDmsParentNodeId;
}
