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
@Table(name ="t_pound")
@Entity
@ApiModel(description = "磅单实体类")
public class PoundEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ApiModelProperty("磅单号")
    private String poundNum;

    @ApiModelProperty("汽车号")
    private String carNum;

    @ApiModelProperty("货物编号")
    private String goodsNum;

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

    @ApiModelProperty("磅重")
    private  Double poundWeight;


    @Convert(converter = MenuEnum.Converter.class)
    @ApiModelProperty("流向/入库，出库")
    private MenuEnum flowTo=MenuEnum.IN;;

    @ApiModelProperty("磅房号")
    private String poundAccount;

    @ApiModelProperty("创建时间")
    private String createTime;




}
