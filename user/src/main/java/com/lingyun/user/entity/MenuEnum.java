package com.lingyun.user.entity;


import com.lingyun.user.util.converter.AbstractEnumConverter;
import com.lingyun.user.util.converter.PersistEnum2DB;

public enum MenuEnum implements PersistEnum2DB<String> {

     ACTIVE("A","启用"),NOACTIVE("D","停用"),
    MAN("M","男"),WOMAN("W","女人")
    ,IN("I","入库"),OUT("O","出库"),
    PASS("P","通过"),REFLUSH("R","拒绝");

    private String desc;


    private String code;

    private MenuEnum(String code, String desc){
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

    @Override
    public String getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<MenuEnum, String> {

        public Converter() {
            super(MenuEnum.class);
        }
    }

}
