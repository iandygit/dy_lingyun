package com.business.pound.entity;

import com.business.pound.util.PoundEnum;
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

    @Convert(converter = PoundEnum.Converter.class)
    @ApiModelProperty("状态")
    private PoundEnum status= PoundEnum.IN;


    @CreatedDate
    @Column(updatable = false, nullable = false)
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("审批意见")
    private String apporvalMes;

    @ApiModelProperty("磅房号")
    private String poundAccount;
}
