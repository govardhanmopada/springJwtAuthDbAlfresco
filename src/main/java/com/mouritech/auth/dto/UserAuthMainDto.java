package com.mouritech.auth.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserAuthMainDto {

    String userName;
    String userPassword;
    List<String> role;

}
