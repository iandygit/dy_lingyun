package com.business.pound.util;


import com.business.pound.util.converter.AbstractEnumConverter;
import com.business.pound.util.converter.PersistEnum2DB;

public enum TransportEnum implements PersistEnum2DB<String> {


    E("E","异常"),
    A("A","初始化"),
    B("B","退回"),
    C("C","完成");


    private String desc;


    private String code;

    private TransportEnum(String code, String desc){
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

    public static class Converter extends AbstractEnumConverter<TransportEnum, String> {

        public Converter() {
            super(TransportEnum.class);
        }
    }

}
