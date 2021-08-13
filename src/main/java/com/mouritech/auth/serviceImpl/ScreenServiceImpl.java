package com.mouritech.auth.serviceImpl;

import com.mouritech.auth.dao.Screen;
import com.mouritech.auth.dto.ScreenDTO;
import com.mouritech.auth.exception.RecordNotFoundException;
import com.mouritech.auth.repo.ScreenRepo;
import com.mouritech.auth.service.FileService;
import com.mouritech.auth.service.ScreenService;
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
public class ScreenServiceImpl implements ScreenService {

    @Autowired
    FileService fileService;

    @Autowired
    Constants constants;

    @Autowired
    ScreenRepo screenRepo;

    @Autowired
    GenericResponse response ;

    @Autowired
    Util util;

    private ModelMapper modelMapper = new ModelMapper();

//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }

    @Autowired
    RestTemplate restTemplate;

    @Override
    public GenericResponse saveScreen(ScreenDTO screenDTO) {

        response = new GenericResponse();
        HashMap<String , String > error = new HashMap<>();
        try{

            String screenFolderName = screenDTO.getScreenName();
            JSONObject folderCreateJson = fileService.generateFolderCreateJson(screenFolderName);
            log.info( "Folder create json {} ",folderCreateJson );
            String dmsFolderUrl = fileService.folderUrl(screenDTO.getModule().getModuleDmsNodeId());
            HttpEntity<String> request = new HttpEntity<>(folderCreateJson.toString(), fileService.dmsAuthHeader());

            String nodeId = "", parentNodeId= "";

            try {
                String responseBody = restTemplate.postForEntity(dmsFolderUrl, request, String.class).getBody();
                JSONObject json = new JSONObject(responseBody);

                nodeId = json.getJSONObject(constants.entry).getString(constants.id).toString();
                parentNodeId = json.getJSONObject(constants.entry).getString(constants.parentId).toString();

                screenDTO.setScreenDmsNodeId(nodeId);
                screenDTO.setScreenDmsParentNodeId(parentNodeId);
                screenDTO = saveScreenRecordIntoDb(screenDTO);

                response = util.generateSuccessRespose();

            }catch (Exception ee){
                log.error("Exception while creating Module folder in DMS {}",ee.getMessage());
                response = util.generateErrorRespose("screenKey", ee.getMessage());
            }

        }catch (Exception e){
            log.error("Exception while saving Module record {}",e.getMessage());
            error.put("Module",e.getMessage());
            response = util.generateErrorRespose("screenKey", e.getMessage());

        }
        response.setData(screenDTO);

        return response;
    }

    ScreenDTO saveScreenRecordIntoDb(ScreenDTO screenDTO){

            try{
                Screen screen = convertToEntity(screenDTO);
                screen.setIsDeleted(0);
                screen = screenRepo.save(screen);

                screenDTO.setId(screen.getId());

            }catch (Exception e){
                log.error("###### Exception while inserting record into db {}", e.getMessage() );
                response = util.generateErrorRespose("screenKey", e.getMessage());
            }
            return screenDTO;

    }

    @Override
    public GenericResponse findAllScreens() {
        response = new GenericResponse();
        try{

            List<Screen> screens = screenRepo.findAll();
            List<ScreenDTO> screensDTO = new ArrayList<>();
            response = util.generateSuccessRespose();
            screens.stream().forEach(screen -> {
                screensDTO.add(convertToDto(screen));
            });
            response.setData(screensDTO);
        }catch (Exception e){
            log.error("Exception while fetching module records{}",e.getMessage());
            response = util.generateErrorRespose("moduleKey", e.getMessage());
        }
        return response;
    }

    @Override
    public GenericResponse findScreen(Long screenId) throws RecordNotFoundException {
        response = new GenericResponse();
        try{

            Optional<Screen> screens = screenRepo.findById(screenId);
            ScreenDTO screensDTO = new ScreenDTO();
            if(screens.isPresent()){
                screensDTO = convertToDto(screens.get());
            }else{
                response = util.generateErrorRespose("Screen", "Record not found");
                throw new RecordNotFoundException("Screen not found");

            }
            response = util.generateSuccessRespose();
            response.setData(screensDTO);
        }catch (Exception e){
            log.error("Exception while fetching screen records{}",e.getMessage());
            response = util.generateErrorRespose("screen", e.getMessage());
        }
        return response;
    }

    @Override
    public GenericResponse findScreensByModule(Long moduleId) throws RecordNotFoundException {
        response = new GenericResponse();
        try{

            List<Screen> screens = screenRepo.findByModuleId(moduleId);
            List<ScreenDTO> screensDTO = new ArrayList<>();
            response = util.generateSuccessRespose();
            screens.stream().forEach(screen -> {
                screensDTO.add(convertToDto(screen));
            });
            response.setData(screensDTO);
        }catch (Exception e){
            log.error("Exception while fetching screen records{}",e.getMessage());
            response = util.generateErrorRespose("screen", e.getMessage());
        }
        return response;
    }


    private Screen convertToEntity(ScreenDTO screenDTO) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(screenDTO, Screen.class);
    }

    private ScreenDTO convertToDto(Screen Screen) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(Screen, ScreenDTO.class);
    }
}
