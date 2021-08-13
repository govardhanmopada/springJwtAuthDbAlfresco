package com.mouritech.auth.controller;

import com.mouritech.auth.dto.ScreenDTO;
import com.mouritech.auth.exception.RecordNotFoundException;
import com.mouritech.auth.service.ScreenService;
import com.mouritech.auth.util.GenericResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController("/dms2")
public class DmsScreenController {

    @Autowired
    ScreenService screenService;

    @PostMapping("screen")
    public ResponseEntity<GenericResponse> createScreen(@RequestBody ScreenDTO screenDTO){
        GenericResponse response= screenService.saveScreen(screenDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("screen")
    public ResponseEntity<GenericResponse> getAllScreens(){
        GenericResponse response= screenService.findAllScreens();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("screen/{screenId}")
    public ResponseEntity<GenericResponse> getScreen(@PathVariable("screenId") Long screenId)
            throws RecordNotFoundException {
        GenericResponse response= screenService.findScreen(screenId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("screen/module/{moduleId}")
    public ResponseEntity<GenericResponse> getAllModules(@PathVariable("moduleId") Long moduleId)
            throws RecordNotFoundException {
        GenericResponse response= screenService.findScreensByModule(moduleId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
