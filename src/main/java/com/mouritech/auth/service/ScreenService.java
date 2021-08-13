package com.mouritech.auth.service;

import com.mouritech.auth.dto.ScreenDTO;
import com.mouritech.auth.exception.RecordNotFoundException;
import com.mouritech.auth.util.GenericResponse;

public interface ScreenService {

    GenericResponse saveScreen(ScreenDTO screenDTO);

    GenericResponse findAllScreens();

    GenericResponse findScreen(Long screenId) throws RecordNotFoundException;

    GenericResponse findScreensByModule(Long moduleId) throws RecordNotFoundException;
}
