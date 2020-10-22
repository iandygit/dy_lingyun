package com.lingyun.user.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="t_user")
@Entity
@ApiModel(description = "用户实体类")
public class UserEntity  implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ApiModelProperty("用户名")
    @Column(name = "user_name",nullable = false, unique = true)
    private String userName;

    @ApiModelProperty("密码")
    @Column(name = "pass_word")
    private String passWord;


    @Column
    @ApiModelProperty("联系电话")
    private String iphone;

    @Column
    @ApiModelProperty("职位")
    private String zhiwei;


    @Column
    @ApiModelProperty("性别")
    @Convert(converter = MenuEnum.Converter.class)
    private MenuEnum sex=MenuEnum.MAN;


    @Column
    @ApiModelProperty("账号")
    private String  acount;

    @ApiModelProperty("权限id")
    private Long roleId;

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME,pattern = "yyyy-MM-dd HH:mm:ss")
    private String   createDate;



    @ApiModelProperty("用户状态")
    @Convert(converter = MenuEnum.Converter.class)
    @Column(name = "status")
    private MenuEnum status=MenuEnum.ACTIVE;
    @Column
    @ApiModelProperty("部门id")
    private Long departId;
}
