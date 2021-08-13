package com.mouritech.auth.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {

     public  String name="name";
     public  String nodeType= "nodeType";
     public  String folder="cm:folder";
     public  String success="success";
     public  String fileData = "filedata";
     public  String entry="entry";
     public  String id="id";
     public  String parentId="parentId";
     public  String exception = "exception";
    public   String successStatus = "success";



     public String queryKey="query";

     @Value("${dms.server.url}")
     public  String serverUrl;

    @Value("${dms.server.main.folder.shared.nodeId}")
    public  String sharedNodeID;

    @Value("${dms.main.url}")
    public  String mainUrl;

    @Value("${dms.main.search.url}")
    public  String searchUrl;

    @Value("${dms.main.url.children}")
    public  String children;

    @Value("${dms.main.url.content}")
    public  String content;

    @Value("${dms.auth.header}")
    public  String authHeader;

    @Value("${dms.auth.header.token}")
    public  String authHeaderToken;

    @Value("${dms.auth.header.basic}")
    public  String authHeaderBasic;



}
