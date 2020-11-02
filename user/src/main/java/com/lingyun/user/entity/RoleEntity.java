package com.lingyun.user.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="t_role")
@Entity
@ApiModel(description = "权限实体类")
public class RoleEntity   {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("权限id主键")
    private Long id;

    @Column(nullable = false)
    @ApiModelProperty("权限名称")
    private String name;


    @ApiModelProperty("权限代码")
    private String code;

    @ApiModelProperty("备注")
    private String content;

    @ApiModelProperty("状态")
    @Convert(converter = MenuEnum.Converter.class)
    @Column(name = "status")
    private MenuEnum status=MenuEnum.ACTIVE;

}
