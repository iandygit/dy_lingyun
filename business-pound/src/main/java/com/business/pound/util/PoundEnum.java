package com.business.pound.util;


import com.business.pound.util.converter.AbstractEnumConverter;
import com.business.pound.util.converter.PersistEnum2DB;

public enum PoundEnum implements PersistEnum2DB<String> {


    IN("I","入库"),
    OUT("O","出库"),
    OPT_E("E","未知"),
    APPORVAL_W("W","未审批"),
    APPORVAL_A("A","通过"),
    APPORVAL_B("B","未通过"),
    SALES("S","销售过磅"),
    REFLUSH("P","采购过磅"),
    INNER("N","内部周转"),
    OTHRE("T","其他过磅"),
    Y("Y","正常"),
    N("D","删除");


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
