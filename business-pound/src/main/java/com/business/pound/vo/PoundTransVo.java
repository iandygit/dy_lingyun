package com.business.pound.vo;

import com.business.pound.util.PoundEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Convert;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class PoundTransVo {


    @ApiModelProperty("运单id")
    private Long transportId;

    @ApiModelProperty("运单编号")
    private String transportNum;

    @ApiModelProperty("磅单id")
    private Long poundId;

    @ApiModelProperty("磅房号")
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

    @Convert(converter = PoundEnum.Converter.class)
    @ApiModelProperty("流向/入库，出库")
    private PoundEnum flowTo=PoundEnum.IN;

    @ApiModelProperty("磅房号")
    private String poundAccount;

    public PoundTransVo(Long transportId, Long poundId, String transportNum, String poundNum,
                        String carNum, String goodsName, String reciveUnit, String deliverUnit,
                        Double weight, Double tareWeight, Double netWeight, Double poundWeight,
                        PoundEnum flowTo, String poundAccount){
        this.transportId=transportId;
        this.poundId=poundId;
        this.transportNum=transportNum;
        this.poundNum=poundNum;
        this.carNum=carNum;
        this.goodsName=goodsName;
        this.reciveUnit=reciveUnit;
        this.deliverUnit=deliverUnit;
        this.weight=weight;
        this.tareWeight=tareWeight;
        this.netWeight=netWeight;
        this.poundWeight=poundWeight;
        this.flowTo=flowTo;
        this.poundAccount=poundAccount;


    }

}
