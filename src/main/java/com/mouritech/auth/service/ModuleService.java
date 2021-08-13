package com.mouritech.auth.service;

import com.mouritech.auth.dto.ModuleDTO;
import com.mouritech.auth.exception.RecordNotFoundException;
import com.mouritech.auth.util.GenericResponse;

public interface ModuleService {

    GenericResponse saveModule(ModuleDTO moduleDTO);

    GenericResponse findAllModules();

    GenericResponse findByModule(Long moduleId) throws RecordNotFoundException;

}
