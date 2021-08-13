package com.mouritech.auth.serviceImpl;


import com.mouritech.auth.service.FileService;
import com.mouritech.auth.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    Constants constants;


    @Override
    public String folderUrl(String nodeId) {
        String uploadUrl="";

        try {
            String serverUrl = constants.serverUrl;
            String mainUrl = constants.mainUrl;
            String parentId = nodeId;
            String children = constants.children;

            uploadUrl = serverUrl + mainUrl + parentId + children ;

            log.info(" framed URL ==>"+ uploadUrl);

        }catch (Exception e){
            log.error("Exception while framing url for{} ", e.getMessage());
        }
        return uploadUrl;
    }

    public JSONObject generateFolderCreateJson(String folderName){
        JSONObject folderCreate = new JSONObject();
        folderCreate.put(constants.name, folderName);
        folderCreate.put(constants.nodeType, constants.folder);
        return folderCreate;
    }

    public HttpHeaders dmsAuthHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.add(constants.authHeader, constants.authHeaderBasic+" "+constants.authHeaderToken);
        return headers;
    }
}
