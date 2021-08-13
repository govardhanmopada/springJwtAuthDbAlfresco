package com.mouritech.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
public class ScreenDTO {

    private Long id;
    @JsonInclude(Include.NON_NULL)
    private ModuleDTO module;
    private String screenName;
    private String screenDesc;
    private String screenDmsParentNodeId;
    private String screenDmsNodeId;

}
