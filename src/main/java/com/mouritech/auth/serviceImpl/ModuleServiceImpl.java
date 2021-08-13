package com.mouritech.auth.serviceImpl;

import com.mouritech.auth.dao.Module;
import com.mouritech.auth.dto.ModuleDTO;
import com.mouritech.auth.dto.ProjectDTO;
import com.mouritech.auth.exception.RecordNotFoundException;
import com.mouritech.auth.repo.ModuleRepo;
import com.mouritech.auth.service.FileService;
import com.mouritech.auth.service.ModuleService;
import com.mouritech.auth.util.Constants;
import com.mouritech.auth.util.GenericResponse;
import com.mouritech.auth.util.Util;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    FileService fileService;

    @Autowired
    Constants constants;

    @Autowired
    ModuleRepo moduleRepo;

    @Autowired
    GenericResponse response ;

    @Autowired
    Util util;

    @Autowired
    RestTemplate restTemplate;

    private ModelMapper modelMapper = new ModelMapper();


    @Override
    public GenericResponse saveModule(ModuleDTO moduleDTO) {

         response = new GenericResponse();
        HashMap<String , String > error = new HashMap<>();
        try{

            String moduleFolderName = moduleDTO.getModuleName();
            JSONObject folderCreateJson = fileService.generateFolderCreateJson(moduleFolderName);
            log.info( "Folder create json {} ",folderCreateJson );
            String dmsFolderUrl = fileService.folderUrl(constants.sharedNodeID);
            HttpEntity<String> request = new HttpEntity<>(folderCreateJson.toString(), fileService.dmsAuthHeader());

            String nodeId = "", parentNodeId= "";

            try {
                String responseBody = restTemplate.postForEntity(dmsFolderUrl, request, String.class).getBody();
                JSONObject json = new JSONObject(responseBody);

                nodeId = json.getJSONObject(constants.entry).getString(constants.id).toString();
                parentNodeId = json.getJSONObject(constants.entry).getString(constants.parentId).toString();

            }catch (Exception ee){
                log.error("Exception while creating Module folder in DMS {}",ee.getMessage());
                response = util.generateErrorRespose("moduleKey", ee.getMessage());
            }

            moduleDTO.setModuleDmsNodeId(nodeId);
            moduleDTO = saveModuleRecordIntoDb(moduleDTO);

            response.setData(moduleDTO);

        }catch (Exception e){
            log.error("Exception while saving Module record {}",e.getMessage());
            error.put("Module",e.getMessage());
            response = util.generateErrorRespose("moduleKey", e.getMessage());

        }

        response = util.generateSuccessRespose();
        return response;
    }


    ModuleDTO saveModuleRecordIntoDb(ModuleDTO moduleDTO){

        try{
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setId(1L);
            moduleDTO.setProjectDTO(projectDTO);
            Module module = convertToEntity(moduleDTO);
            module.setIsDeleted(0);
            module = moduleRepo.save(module);

            log.info("========>>>>>=====>>>>===>>>>{}",module.getId());

            moduleDTO.setId(module.getId());

        }catch (Exception e){
            log.error("Exception while inserting record into db {}", e.getMessage() );
        }
        return moduleDTO;
    }


    @Override
    public GenericResponse findAllModules() {
        response = new GenericResponse();
        try{

            List<Module> modules = moduleRepo.findAll();
            List<ModuleDTO> modulesDTO = new ArrayList<>();
            response = util.generateSuccessRespose();
            modules.stream().forEach(module -> {
                modulesDTO.add(convertToDto(module));
            });
            response.setData(modulesDTO);
        }catch (Exception e){
            log.error("Exception while fetching module records{}",e.getMessage());
            response = util.generateErrorRespose("moduleKey", e.getMessage());
        }
        return response;
    }


    @Override
    public GenericResponse findByModule(Long moduleId) throws RecordNotFoundException {
        response = new GenericResponse();

        try{
            ModuleDTO moduleDTO = new ModuleDTO();
            Optional<Module> module = moduleRepo.findById(moduleId);

            if(module.isPresent()){
                moduleDTO = convertToDto(module.get());
                response.setData(moduleDTO);
            }else{
                response = util.generateErrorRespose("module Key", "Record not found");
                throw new RecordNotFoundException("ModelKey not found");

            }

        }catch (Exception e){
            log.error("Exception while fetching module records{}",e.getMessage());
            response = util.generateErrorRespose("modelKey", e.getMessage());
        }
        return response;
    }


    private Module convertToEntity(ModuleDTO moduleDTO) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(moduleDTO, Module.class);
    }

    private ModuleDTO convertToDto(Module module) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(module, ModuleDTO.class);
    }
}
