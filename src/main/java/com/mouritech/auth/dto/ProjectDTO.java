package com.mouritech.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectDTO {

    private Long id;
    @JsonInclude(Include.NON_NULL)
    private String projectName;
    @JsonInclude(Include.NON_NULL)
    private String projectDmsNodeId;

}
