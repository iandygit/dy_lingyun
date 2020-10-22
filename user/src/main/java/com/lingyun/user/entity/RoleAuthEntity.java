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
@Table(name ="t_role_auth")
@Entity
@ApiModel(description = "角色菜单关联类")
public class RoleAuthEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("权限id主键")
    private Long id;

    @ApiModelProperty("角色 id")
    private Long roleId;

    @ApiModelProperty("菜单id")
    private Long menuId;

}
