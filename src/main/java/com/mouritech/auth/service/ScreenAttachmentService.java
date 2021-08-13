package com.mouritech.auth.service;

import com.mouritech.auth.dto.ScreenAttachmentDTO;
import com.mouritech.auth.exception.RecordNotFoundException;
import com.mouritech.auth.util.GenericResponse;


public interface ScreenAttachmentService {

    GenericResponse saveScreenAttachment(ScreenAttachmentDTO screenDTO);

    GenericResponse findAllScreensAttachments();

    GenericResponse findScreenAttachment(Long screenId) throws RecordNotFoundException;
}
