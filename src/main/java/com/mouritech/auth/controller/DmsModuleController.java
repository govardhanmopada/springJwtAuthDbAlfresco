package com.mouritech.auth.controller;

import com.mouritech.auth.dto.ModuleDTO;
import com.mouritech.auth.exception.RecordNotFoundException;
import com.mouritech.auth.service.ModuleService;
import com.mouritech.auth.util.GenericResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController()
public class DmsModuleController {

    @Autowired
    ModuleService moduleService;

    @PostMapping("module")
    public ResponseEntity<GenericResponse> createModule(@RequestBody ModuleDTO moduleDTO){
        GenericResponse response= moduleService.saveModule(moduleDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("module")
    public ResponseEntity<GenericResponse> getAllModules(){
        GenericResponse response= moduleService.findAllModules();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("module/{moduleId}")
    public ResponseEntity<GenericResponse> getModule(@PathVariable("moduleId") Long moduleId)
            throws RecordNotFoundException {
        GenericResponse response= moduleService.findByModule(moduleId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
