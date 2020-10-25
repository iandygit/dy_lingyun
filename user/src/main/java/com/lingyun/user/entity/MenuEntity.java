package com.lingyun.user.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="t_menu")
@Entity
@Proxy(lazy = false)
public class MenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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





}
