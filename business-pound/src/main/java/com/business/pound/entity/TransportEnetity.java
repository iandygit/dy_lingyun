package com.business.pound.entity;

import com.business.pound.util.TransportEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

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

    @ApiModelProperty("汽车号")
    private String carNum;

    @ApiModelProperty("货物名称")
    private String goodsName;

    @ApiModelProperty("收货单位")
    private String reciveUnit;

    @ApiModelProperty("发货单位")
    private String deliverUnit;

    @ApiModelProperty("毛重")
    private Double weight;

    @ApiModelProperty("皮重")
    private Double tareWeight;

    @ApiModelProperty("净重")
    private Double netWeight;


    @Convert(converter = TransportEnum.Converter.class)
    @ApiModelProperty("状态")
    private TransportEnum status= TransportEnum.A;


    @CreatedDate
    @Column(updatable = false, nullable = false)
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("审批意见")
    private String apporvalMes;

    @ApiModelProperty("磅房号")
    private String poundAccount;
}
