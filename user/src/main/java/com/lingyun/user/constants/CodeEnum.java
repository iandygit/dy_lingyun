package com.lingyun.user.constants;

public enum  CodeEnum {

    ERROR_CODE_5001(50001,"无效的用户推广"),ERROR_CODE_50002(50002,"获取推广码失败"),
    ERROR_CODE_50003(50003,"无效推广码"),ERROR_CODE_50004(50004,"用户已被推广"),ERROR_CODE_50005(50005,"推广注册失败"),ERROR_CODE_5000(5000,"参数不足"),
    MAGIC_BASEURL(1000,"https://www.51gkp.com/referrer");//register
    private  String name;
    private  Integer codeNum;

    CodeEnum(Integer codeNum,String name) {
        this.codeNum=codeNum;
        this.name=name;
    }
    public  String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCodeNum() {
        return codeNum;
    }

    public void setCodeNum(Integer codeNum) {
        this.codeNum = codeNum;
    }
}
