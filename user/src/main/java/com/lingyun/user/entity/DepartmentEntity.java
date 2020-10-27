package com.lingyun.user.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="t_department")
@Entity
@ApiModel(description = "部门实体类")
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    @ApiModelProperty("部门名称")
    private String depName;

    @Column
    @ApiModelProperty("部门代码")
    private String depCode;

    @Column
    @ApiModelProperty("所属矿区")
    private String orgKq;

    @Column
    @ApiModelProperty("负责名字")
    private String fzrName;

    @ApiModelProperty("创建时间")
    @Column
    private String createTime;

}
