package com.mouritech.auth.util;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Data
public class GenericResponse {

    private String status;
    private boolean error;
    private HashMap<String,String> errorMessage;
    private Object data;
}
