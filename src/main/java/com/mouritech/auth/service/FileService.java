package com.mouritech.auth.service;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;

public interface FileService {

    String folderUrl( String nodeId);
    JSONObject generateFolderCreateJson(String folderName);
    HttpHeaders dmsAuthHeader();
}
