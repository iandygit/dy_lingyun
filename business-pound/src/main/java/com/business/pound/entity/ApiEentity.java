package com.business.pound.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="t_api")
@Entity
@ApiModel(description = "api实体类")
public class ApiEentity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ApiModelProperty("客户端uuid")
    private String uuid;

    @ApiModelProperty("客户端钥匙")
    private String key;
}
