package com.lingyun.user.vo;

import com.lingyun.user.entity.MenuEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Convert;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class MenuVo {

    private Long id;
    @ApiModelProperty("菜单名称")
    private String menuName;
    @ApiModelProperty("菜单代码")
    private String code;
    @ApiModelProperty("父菜单代码")
    private String parentCode;
    @ApiModelProperty("顺序")
    private int displyOrder;

    @ApiModelProperty("菜单链接")
    private String menuUrl;
    private int menuLevel;

    @ApiModelProperty("状态")
    @Convert(converter = MenuEnum.Converter.class)
    @Column(name = "status")
    private MenuEnum status=MenuEnum.ACTIVE;


    public MenuVo(Long id,String menuName,String code,int displyOrder,String menuUrl,MenuEnum  status ){

           this.id=id;
           this.menuName=menuName;
           this.code=code;
           this.displyOrder=displyOrder;
           this.menuUrl=menuUrl;
           this.status=status;

    }
}
