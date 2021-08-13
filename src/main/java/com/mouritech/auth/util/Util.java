package com.mouritech.auth.util;

import com.aspose.cad.Image;
import com.aspose.cad.imageoptions.PdfOptions;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

@Component
public class Util {


    @Autowired
    Constants constants;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public GenericResponse generateErrorRespose(String key, String e){
        GenericResponse response = new GenericResponse();

        HashMap<String , String > error = new HashMap<>();
        error.put(key,e);

        response.setError(true);
        response.setStatus(constants.exception);
        response.setErrorMessage(error);

        return  response;
    }

    public GenericResponse generateSuccessRespose(){
        GenericResponse response = new GenericResponse();

        response.setError(false);
        response.setStatus(constants.successStatus);

        return  response;
    }

    public String convertDwgToPdf(MultipartFile file, String fileName){

        try{

            Image objImage = Image.load(file.getInputStream());

            PdfOptions pdfOptions = new PdfOptions();

            objImage.save("convertedFiles/"+fileName+".pdf", pdfOptions);

        }catch (Exception e){
            System.err.println("Exception while convert to DWG to PDF: "+e.getMessage());
            return "Failed";
        }

        return "Success";
    }


    public String findConvertedFileLocation()throws IOException, URISyntaxException {
        Path targetPath = Paths.get(getClass().getResource("/").toURI()).getParent();
        String path = targetPath.toString();

        System.out.println("path==>"+path);

        path= path.replace("target","");
        return path;
    }

    public MultipartFile convertedPdfFile(String path, String fileName) throws IOException {

        File file = new File(path+"convertedFiles\\"+fileName);
        String absolutePath = file.getAbsolutePath();

        System.out.println("path===>"+file.getAbsolutePath());

        FileInputStream input = new FileInputStream(file);

        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "application/pdf", IOUtils.toByteArray(input));
        return multipartFile;
    }

}
