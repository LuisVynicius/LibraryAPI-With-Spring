package com.mevy.libraryapi.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProfileEnum {
    ROLE_ADMIN(1, "ROLE_ADMIN"),
    ROLE_USER(2, "ROLE_USER");

    private Integer code;
    private String description;

    public static ProfileEnum valueOf(Integer code){
        for (ProfileEnum up : ProfileEnum.values()){
            if (code.equals(up.getCode())) return up;
        }
        throw new IllegalArgumentException("This number is not valid. ");
    }
}
