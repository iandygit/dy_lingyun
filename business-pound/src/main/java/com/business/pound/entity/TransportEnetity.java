package com.business.pound.entity;

import com.business.pound.util.PoundEnum;
import com.business.pound.util.TransportEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

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
    @Convert(converter = PoundEnum.Converter.class)
    @ApiModelProperty("流向/入库，出库")
    //@Enumerated(EnumType.ORDINAL)
    /**
     * EnumType:  ORDINAL 枚举序数  默认选项（int）。eg:入库 数据库存储的是 0
     *            STRING：枚举名称       (String)。eg:入库 数据库存储的是 "入库"
     */
    private PoundEnum flowTo;

    @CreatedDate
    /**
     * 创建时间 yyyy-MM-dd HH:mm:ss
     */
    @Column(name = "create_time", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME,pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("审批意见")
    private String apporvalMes;

    @ApiModelProperty("磅房号")
    private String poundAccount;
}
