package com.business.pound.util;


import com.business.pound.util.converter.AbstractEnumConverter;
import com.business.pound.util.converter.PersistEnum2DB;

public enum PoundEnum implements PersistEnum2DB<String> {


    IN("I","入库"),
    OUT("O","出库"),
    SALES("S","销售过磅"),
    REFLUSH("P","采购过磅"),
    INNER("N","内部周转"),
    OTHRE("T","其他过磅");


    private String desc;


    private String code;

    private PoundEnum(String code, String desc){
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

    public static class Converter extends AbstractEnumConverter<PoundEnum, String> {

        public Converter() {
            super(PoundEnum.class);
        }
    }

}
