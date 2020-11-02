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
@Table(name ="t_transport")
@Entity
@ApiModel(description = "运单实体类")
public class TransportEnetity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ApiModelProperty("磅单id")
    private Long poundId;


    @ApiModelProperty("运单号")
    private String transportNum;

    @ApiModelProperty("磅单号")
    private String poundNum;

    @Convert(converter = MenuEnum.Converter.class)
    @ApiModelProperty("状态")
    private MenuEnum status=MenuEnum.PASS;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("审批一见你")
    private String apporvalMes;
}
