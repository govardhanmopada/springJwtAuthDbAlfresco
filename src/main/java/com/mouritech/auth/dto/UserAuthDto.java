package com.mouritech.auth.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public interface UserAuthDto {

    String getUserName();
    String getUserPassword();
    String getRoleName();

}
