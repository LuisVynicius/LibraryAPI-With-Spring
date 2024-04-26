package com.mevy.libraryapi.entities.enums;

import lombok.Getter;

@Getter
public enum ProfileEnum {
    ROLE_ADMIN(1),
    ROLE_USER(2);

    private Integer code;

    private ProfileEnum(Integer code){
        this.code = code;
    }

    public static ProfileEnum valueOf(Integer code){
        for (ProfileEnum up : ProfileEnum.values()){
            if (code.equals(up.getCode())) return up;
        }
        throw new IllegalArgumentException("This number is not valid. ");
    }
}
