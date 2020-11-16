package com.business.pound.entity;

import com.business.pound.util.PoundEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="t_pound")
@Entity
@ApiModel(description = "磅单实体类")
@EntityListeners(AuditingEntityListener.class)
public class PoundEntity implements Serializable {


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

    @ApiModelProperty("运单号")
    private String transportNum;

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

    @ApiModelProperty("扣率")
    public Double deductionRate ;
    @ApiModelProperty("扣杂")
    public Double deductionWeight;

    @ApiModelProperty(" 实重")
    public Double actualWeight;
    @ApiModelProperty(" 毛重时间")
    public String weightTime ;
    @ApiModelProperty(" 皮重时间")
    public String tareWeightTime;
    @ApiModelProperty(" 备注")
    public String comments;



    @Convert(converter = PoundEnum.Converter.class)
    @ApiModelProperty("流向/入库，出库")
    //@Enumerated(EnumType.ORDINAL)
    /**
     * EnumType:  ORDINAL 枚举序数  默认选项（int）。eg:入库 数据库存储的是 0
     *            STRING：枚举名称       (String)。eg:入库 数据库存储的是 "入库"
     */
    private PoundEnum flowTo;

    @ApiModelProperty("磅房号")
    private String poundAccount;

    @ApiModelProperty("创建时间")
    /**
     * 创建时间 yyyy-MM-dd HH:mm:ss
     */
    @Column(name = "create_time", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME,pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    private String createTime;

    @Convert(converter = PoundEnum.Converter.class)
    @ApiModelProperty("审批通过/未通过/未审批")
    private PoundEnum poundStatus=PoundEnum.APPORVAL_W;;


}
