package com.mouritech.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;


@Data
public class ModuleDTO {

    private Long id;
    @JsonInclude(Include.NON_NULL)
    private ProjectDTO projectDTO;
    private String moduleName;
    private String moduleDesc;
    private String moduleDmsNodeId;

}
