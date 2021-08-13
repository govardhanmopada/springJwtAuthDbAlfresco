package com.mouritech.auth.serviceImpl;

import com.mouritech.auth.dto.ModuleDTO;
import com.mouritech.auth.dto.ScreenAttachmentDTO;
import com.mouritech.auth.exception.RecordNotFoundException;
import com.mouritech.auth.repo.ScreenAttachmentRepo;
import com.mouritech.auth.service.FileService;
import com.mouritech.auth.service.ScreenAttachmentService;
import com.mouritech.auth.util.Constants;
import com.mouritech.auth.util.FileSystemResource;
import com.mouritech.auth.util.GenericResponse;
import com.mouritech.auth.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Slf4j
@Service
public class ScreenAttachmentServiceImpl implements ScreenAttachmentService {

    @Autowired
    FileService fileService;

    @Autowired
    Constants constants;

    @Autowired
    ScreenAttachmentRepo screenAttachmentRepo;

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
    public GenericResponse saveScreenAttachment(ScreenAttachmentDTO screenAttachmentDTO) {


        response = new GenericResponse();
        HashMap<String , String > error = new HashMap<>();
        try{

           String folderNodeId = screenAttachmentDTO.getScreen().getScreenDmsNodeId();
            String  fileUploadUrl = fileService.folderUrl(folderNodeId);
            log.info( "fileUploadUrl ====>>> {} ",fileUploadUrl );

            JSONObject dmsData = uploadFileIntoDMS(screenAttachmentDTO.getFile(),fileUploadUrl);

            log.info(" dmsData ====>>>> {}",dmsData);

        }catch (Exception e){
            log.error("Exception while saving Module record {}",e.getMessage());
            error.put("Module",e.getMessage());
            response = util.generateErrorRespose("screenKey", e.getMessage());

        }


        return response;
    }


    public JSONObject uploadFileIntoDMS(MultipartFile file, String uploadUrl) throws IOException {

        try {


            String logFileName = new SimpleDateFormat("ddMMyyyy_hhmmss").format(new Date());
            System.out.println("logFileName==>"+logFileName);

            String fileActualName = file.getOriginalFilename();
            String fileType= fileActualName.split("\\.")[fileActualName.split("\\.").length-1];
            String fileModifiedFileName= fileActualName.split("\\.")[0]+"_"+logFileName+"."+fileType;
            System.out.println("fileModifiedFileName===>"+fileModifiedFileName);


            if(fileType.equalsIgnoreCase("dwg") && (util.convertDwgToPdf(file,fileActualName).equalsIgnoreCase(constants.success))){
                file =   util.convertedPdfFile(util.findConvertedFileLocation(),fileActualName+".pdf");
                fileModifiedFileName = fileActualName.split("\\.")[0]+"_"+logFileName+".pdf";
            }

            Resource resouceFile = new FileSystemResource(file.getBytes(),  file.getOriginalFilename());

            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add(constants.fileData, resouceFile);
            map.add(constants.name, fileModifiedFileName);
            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, fileService.dmsAuthHeader());

            String responseBody = restTemplate.postForEntity(uploadUrl, request, String.class).getBody();
            JSONObject json = new JSONObject(responseBody);
            System.out.println("########## ==>" +json);

            //String fileNodeId = json.getJSONObject("entry").getString("id");
            String fileNodeIdFromDms = json.getJSONObject(constants.entry).getString(constants.id).toString();
            String parentNodeId = json.getJSONObject(constants.entry).getString(constants.parentId).toString();

            return json;
        }catch (Exception e){
            System.err.println(" Exception while saved file into dms :"+ e.getMessage());
            return null;
        }
    }

    @Override
    public GenericResponse findAllScreensAttachments() {
        return null;
    }

    @Override
    public GenericResponse findScreenAttachment(Long screenId) throws RecordNotFoundException {
        return null;
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
