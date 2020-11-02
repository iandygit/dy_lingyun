package com.lingyun.user.vo;

import com.lingyun.user.entity.MenuEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ApiModelProperty("用户名")
    @Column(name = "user_name",nullable = false, unique = true)
    private String userName;

    @ApiModelProperty("部门")
    private String departName;

    @ApiModelProperty("用户类型")
    private String roleName;

    @ApiModelProperty("联系电话")
    private String iphone;


    @ApiModelProperty("职位")
    private String zhiwei;


    @Column
    @ApiModelProperty("性别")
    @Convert(converter = MenuEnum.Converter.class)
    private MenuEnum sex=MenuEnum.MAN;



    private String   createDate;



    @ApiModelProperty("用户状态")
    @Convert(converter = MenuEnum.Converter.class)
    @Column(name = "status")
    private MenuEnum status=MenuEnum.ACTIVE;


    public UserVo(Long id,String userName,String iphone,String zhiwei,String departName,String roleName){

        this.id=id;
        this.userName=userName;
        this.iphone=iphone;
        this.zhiwei=zhiwei;
        this.departName=departName;
        this.roleName=roleName;


    }
}
