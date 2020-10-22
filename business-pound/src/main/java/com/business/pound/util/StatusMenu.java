package com.business.pound.util;

import com.business.pound.util.converter.AbstractEnumConverter;
import com.business.pound.util.converter.PersistEnum2DB;

public enum StatusMenu implements PersistEnum2DB<String> {


    IN("A","激活"),
    STOP("S","停用");


    private String desc;


    private String code;

    private StatusMenu(String code, String desc){
        this.code=code;
        this.desc=desc;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<StatusMenu, String> {

        public Converter() {
            super(StatusMenu.class);
        }
    }

}
