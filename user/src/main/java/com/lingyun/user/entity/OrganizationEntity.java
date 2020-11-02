package com.lingyun.user.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="t_organization")
@Entity
@ApiModel(description = "矿区实体类")
public class OrganizationEntity implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    @ApiModelProperty("矿区名称")
    private String orgName;
    @Column
    @ApiModelProperty("矿区地址")
    private String orgAdd;

    @Column
    @ApiModelProperty("注册时间")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME,pattern = "yyyy-MM-dd HH:mm:ss")
    private String orgTime;

    @Column
    @ApiModelProperty("负责人")
    private String fzr;
}
