package com.mouritech.auth.controller;

import com.mouritech.auth.dto.ScreenAttachmentDTO;
import com.mouritech.auth.service.ScreenAttachmentService;
import com.mouritech.auth.util.GenericResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController("/dms3")
public class DmsAttachmentFileController {

    @Autowired
    ScreenAttachmentService screenAttachmentService;

    @PostMapping("screenAttachment")
    public ResponseEntity<GenericResponse> createScreen(@ModelAttribute ScreenAttachmentDTO screenAttachmentDTO){
        GenericResponse response= screenAttachmentService.saveScreenAttachment(screenAttachmentDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
